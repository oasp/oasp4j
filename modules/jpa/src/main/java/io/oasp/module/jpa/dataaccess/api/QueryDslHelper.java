package io.oasp.module.jpa.dataaccess.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.search.base.AbstractSearchCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.querydsl.core.JoinExpression;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.module.basic.common.api.query.LikePatternSyntax;
import io.oasp.module.basic.common.api.query.StringSearchConfigTo;
import io.oasp.module.basic.common.api.query.StringSearchOperator;
import io.oasp.module.jpa.common.api.to.OrderByTo;
import io.oasp.module.jpa.common.api.to.OrderDirection;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationResultTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;

/**
 * Class with utility methods for dealing with QueryDSL. Either extend this class or use {@link QueryDslUtil#get()}.
 *
 * @since 3.0.0
 */
public class QueryDslHelper {

  private static final Logger LOG = LoggerFactory.getLogger(QueryDslHelper.class);

  /** JPA query property to configure the timeout in milliseconds. */
  protected static final String QUERY_PROPERTY_TIMEOUT = "javax.persistence.query.timeout";

  /**
   * Returns a paginated list of entities according to the supplied {@link SearchCriteriaTo criteria}.
   * <p>
   * Applies {@code limit} and {@code offset} values to the supplied {@code query} according to the supplied
   * {@link PaginationTo pagination} information inside {@code criteria}.
   * <p>
   * If a {@link PaginationTo#isTotal() total count} of available entities is requested, will also execute a second
   * query, without pagination parameters applied, to obtain said count.
   * <p>
   * Will install a query timeout if {@link SearchCriteriaTo#getSearchTimeout()} is not null.
   *
   * @param <E> generic type of the entity.
   * @param criteria contains information about the requested page.
   * @param query is a query which is preconfigured with the desired conditions for the search.
   * @param applySortOrder - {@code true} to automatically {@link #applySortOrder(List, JPAQuery) apply} the
   *        {@link SearchCriteriaTo#getSort() sort order} from the given {@link SearchCriteriaTo}, {@code false}
   *        otherwise (to apply manually for complex queries).
   * @return a paginated list.
   */
  protected <E> PaginatedListTo<E> findPaginatedGeneric(SearchCriteriaTo criteria, JPAQuery<E> query,
      boolean applySortOrder) {

    applyTimeout(query, criteria.getSearchTimeout());

    PaginationTo pagination = criteria.getPagination();
    PaginationResultTo paginationResult = createPaginationResult(pagination, query);
    applyPagination(pagination, query);

    if (applySortOrder) {
      applySortOrder(criteria.getSort(), query);
    }

    List<E> paginatedList = query.fetch();

    return new PaginatedListTo<>(paginatedList, paginationResult);
  }

  /**
   * @param sort the {@link SearchCriteriaTo#getSort() sort order}.
   * @param query the {@link JPAQuery} to modify.
   */
  @SuppressWarnings("rawtypes")
  protected void applySortOrder(List<OrderByTo> sort, JPAQuery<?> query) {

    if ((sort == null) || sort.isEmpty()) {
      return;
    }
    PathBuilder<?> alias = findAlias(query);
    for (OrderByTo orderBy : sort) {
      String name = orderBy.getName();
      ComparablePath<Comparable> path = alias.getComparable(name, Comparable.class);
      OrderSpecifier<Comparable> orderSpecifier;
      if (orderBy.getDirection() == OrderDirection.ASC) {
        orderSpecifier = path.asc();
      } else {
        orderSpecifier = path.desc();
      }
      query.orderBy(orderSpecifier);
    }
  }

  private <E> PathBuilder<E> findAlias(JPAQuery<E> query) {

    String alias = null;
    List<JoinExpression> joins = query.getMetadata().getJoins();
    if ((joins != null) && !joins.isEmpty()) {
      JoinExpression join = joins.get(0);
      Expression<?> target = join.getTarget();
      if (target instanceof EntityPath) {
        alias = target.toString(); // no safe API
      }
    }
    Class<E> type = query.getType();
    if (alias == null) {
      // should actually never happen, but fallback is provided as buest guess
      alias = StringUtils.uncapitalize(type.getSimpleName());
    }
    return new PathBuilder<>(type, alias);
  }

  /**
   * Creates a {@link PaginationResultTo pagination result} for the given {@code pagination} and {@code query}.
   * <p>
   * Needs to be called before pagination is applied to the {@code query}.
   *
   * @param pagination contains information about the requested page.
   * @param query is a query preconfigured with the desired conditions for the search.
   * @return information about the applied pagination.
   */
  protected PaginationResultTo createPaginationResult(PaginationTo pagination, JPAQuery<?> query) {

    Long total = calculateTotalBeforePagination(pagination, query);
    return new PaginationResultTo(pagination, total);
  }

  /**
   * Calculates the total number of entities the given {@link JPAQuery query} would return without pagination applied.
   * <p>
   * Needs to be called before pagination is applied to the {@code query}.
   *
   * @param pagination is the pagination information as requested by the client.
   * @param query is the {@link JPAQuery query} for which to calculate the total.
   * @return the total count, or {@literal null} if {@link PaginationTo#isTotal()} is {@literal false}.
   */
  protected Long calculateTotalBeforePagination(PaginationTo pagination, JPAQuery<?> query) {

    Long total = null;
    if (pagination.isTotal()) {
      total = query.clone().fetchCount();
    }
    return total;
  }

  /**
   * Applies the {@link PaginationTo pagination criteria} to the given {@link JPAQuery}.
   *
   * @param pagination is the {@link PaginationTo pagination criteria} to apply.
   * @param query is the {@link JPAQuery} to apply to.
   */
  protected void applyPagination(PaginationTo pagination, JPAQuery<?> query) {

    if (pagination == PaginationTo.NO_PAGINATION) {
      return;
    }

    Integer limit = pagination.getSize();
    if (limit != null) {
      query.limit(limit);

      int page = pagination.getPage();
      if (page > 0) {
        query.offset((page - 1) * limit);
      }
    }
  }

  /**
   * Applies the meta-data of the given {@link AbstractSearchCriteria search criteria} to the given {@link JPAQuery}.
   *
   * @param criteria is the {@link AbstractSearchCriteria search criteria} to apply.
   * @param query is the {@link JPAQuery} to apply to.
   */
  protected void applyCriteria(AbstractSearchCriteria criteria, JPAQuery<?> query) {

    Integer limit = criteria.getMaximumHitCount();
    if (limit != null) {
      query.limit(limit);
    }
    int offset = criteria.getHitOffset();
    if (offset > 0) {
      query.offset(offset);
    }
    Long timeout = criteria.getSearchTimeout();
    applyTimeout(query, timeout);
  }

  /**
   * @param query the {@link JPAQuery} to modify.
   * @param timeout the timeout in milliseconds.
   */
  protected void applyTimeout(JPAQuery<?> query, Number timeout) {

    if (timeout != null) {
      query.setHint(QUERY_PROPERTY_TIMEOUT, timeout.intValue());
    }
  }

  /**
   * @param query the {@link JPAQuery} to modify.
   * @param expression the {@link StringExpression} to {@link StringExpression#like(String) create the LIKE-clause}
   *        from.
   * @param pattern the pattern for the LIKE-clause to create.
   * @param syntax the {@link LikePatternSyntax} of the given {@code pattern}.
   * @param ignoreCase - {@code true} to ignore the case, {@code false} otherwise (to search case-sensitive).
   * @param matchSubstring - {@code true} to match also if the given {@code pattern} shall also match substrings on the
   *        given {@link StringExpression}.
   */
  protected void whereLike(JPAQuery<?> query, StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring) {

    BooleanExpression like = newLikeClause(expression, pattern, syntax, ignoreCase, matchSubstring, false);
    if (like != null) {
      query.where(like);
    }
  }

  /**
   * @param query the {@link JPAQuery} to modify.
   * @param expression the {@link StringExpression} to {@link StringExpression#notLike(String) create the NOT
   *        LIKE-clause} from.
   * @param pattern the pattern for the NOT LIKE-clause to create.
   * @param syntax the {@link LikePatternSyntax} of the given {@code pattern}.
   * @param ignoreCase - {@code true} to ignore the case, {@code false} otherwise (to search case-sensitive).
   * @param matchSubstring - {@code true} to match also if the given {@code pattern} shall also match substrings on the
   *        given {@link StringExpression}.
   */
  protected void whereNotLike(JPAQuery<?> query, StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring) {

    BooleanExpression like = newLikeClause(expression, pattern, syntax, ignoreCase, matchSubstring, true);
    if (like != null) {
      query.where(like);
    }
  }

  /**
   * @param expression the {@link StringExpression} to {@link StringExpression#like(String) create the LIKE-clause}
   *        from.
   * @param pattern the pattern for the LIKE-clause to create.
   * @param syntax the {@link LikePatternSyntax} of the given {@code pattern}.
   * @param ignoreCase - {@code true} to ignore the case, {@code false} otherwise (to search case-sensitive).
   * @param matchSubstring - {@code true} to match also if the given {@code pattern} shall also match substrings on the
   *        given {@link StringExpression}.
   * @return the LIKE-clause as {@link BooleanExpression}.
   */
  protected BooleanExpression newLikeClause(StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring) {

    return newLikeClause(expression, pattern, syntax, ignoreCase, matchSubstring, false);
  }

  /**
   * @param expression the {@link StringExpression} to {@link StringExpression#notLike(String) create the NOT
   *        LIKE-clause} from.
   * @param pattern the pattern for the NOT LIKE-clause to create.
   * @param syntax the {@link LikePatternSyntax} of the given {@code pattern}.
   * @param ignoreCase - {@code true} to ignore the case, {@code false} otherwise (to search case-sensitive).
   * @param matchSubstring - {@code true} to match also if the given {@code pattern} shall also match substrings on the
   *        given {@link StringExpression}.
   * @return the NOT LIKE-clause as {@link BooleanExpression}.
   */
  protected BooleanExpression newNotLikeClause(StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring) {

    return newLikeClause(expression, pattern, syntax, ignoreCase, matchSubstring, true);
  }

  /**
   * @param expression the {@link StringExpression} to {@link StringExpression#like(String) create the LIKE-clause}
   *        from.
   * @param pattern the pattern for the LIKE-clause to create.
   * @param syntax the {@link LikePatternSyntax} of the given {@code pattern}.
   * @param ignoreCase - {@code true} to ignore the case, {@code false} otherwise (to search case-sensitive).
   * @param matchSubstring - {@code true} to match also if the given {@code pattern} shall also match substrings on the
   *        given {@link StringExpression}.
   * @param negate - {@code true} for {@link StringExpression#notLike(String) NOT LIKE}, {@code false} for
   *        {@link StringExpression#like(String) LIKE}.
   * @return the LIKE-clause as {@link BooleanExpression}.
   */
  protected BooleanExpression newLikeClause(StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring, boolean negate) {

    if (syntax == null) {
      syntax = LikePatternSyntax.autoDetect(pattern);
      if (syntax == null) {
        syntax = LikePatternSyntax.SQL;
      }
    }
    String likePattern = LikePatternSyntax.SQL.convert(pattern, syntax, matchSubstring);
    StringExpression exp = expression;
    if (ignoreCase) {
      likePattern = likePattern.toUpperCase(Locale.US);
      exp = exp.upper();
    }
    BooleanExpression clause;
    if (syntax != LikePatternSyntax.SQL) {
      clause = exp.like(likePattern, LikePatternSyntax.ESCAPE);
    } else {
      clause = exp.like(likePattern);
    }
    if (negate) {
      clause = clause.not();
    }
    return clause;
  }

  /**
   * @param expression the {@link StringExpression} to search on.
   * @param value the string value or pattern to search for.
   * @param config the {@link StringSearchConfigTo} to configure the search. May be {@code null} for regular equals
   *        search as default fallback.
   * @return the new {@link BooleanExpression} for the specified string comparison clause.
   */
  protected BooleanExpression newStringClause(StringExpression expression, String value, StringSearchConfigTo config) {

    StringSearchOperator operator = StringSearchOperator.EQ;
    LikePatternSyntax syntax = null;
    boolean ignoreCase = false;
    boolean matchSubstring = false;
    if (config != null) {
      operator = config.getOperator();
      syntax = config.getLikeSyntax();
      ignoreCase = config.isIgnoreCase();
      matchSubstring = config.isMatchSubstring();
    }
    return newStringClause(expression, value, operator, syntax, ignoreCase, matchSubstring);
  }

  /**
   * @param expression the {@link StringExpression} to search on.
   * @param value the string value or pattern to search for.
   * @param syntax the {@link LikePatternSyntax} of the given {@code pattern}.
   * @param operator the {@link StringSearchOperator} used to compare the search string {@code value}.
   * @param ignoreCase - {@code true} to ignore the case, {@code false} otherwise (to search case-sensitive).
   * @param matchSubstring - {@code true} to match also if the given {@code pattern} shall also match substrings on the
   *        given {@link StringExpression}.
   * @return the new {@link BooleanExpression} for the specified string comparison clause.
   */
  protected BooleanExpression newStringClause(StringExpression expression, String value, StringSearchOperator operator,
      LikePatternSyntax syntax, boolean ignoreCase, boolean matchSubstring) {

    if (operator == null) {
      if (syntax == null) {
        syntax = LikePatternSyntax.autoDetect(value);
        if (syntax == null) {
          operator = StringSearchOperator.EQ;
        } else {
          operator = StringSearchOperator.LIKE;
        }
      } else {
        operator = StringSearchOperator.LIKE;
      }
    }
    if (matchSubstring && ((operator == StringSearchOperator.EQ) || (operator == StringSearchOperator.NE))) {
      if (syntax == null) {
        syntax = LikePatternSyntax.SQL;
      }
      if (operator == StringSearchOperator.EQ) {
        operator = StringSearchOperator.LIKE;
      } else {
        operator = StringSearchOperator.NOT_LIKE;
      }
    }
    String v = value;
    if (v == null) {
      switch (operator) {
        case LIKE:
        case EQ:
          return expression.isNull();
        case NE:
          return expression.isNotNull();
        default:
          throw new IllegalArgumentException("Operator " + operator + " does not accept null!");
      }
    } else if (v.isEmpty()) {
      switch (operator) {
        case LIKE:
        case EQ:
          return expression.isEmpty();
        case NOT_LIKE:
        case NE:
          return expression.isNotEmpty();
        default:
          // continue;
      }
    }
    StringExpression exp = expression;
    if (ignoreCase) {
      v = v.toUpperCase(Locale.US);
      exp = exp.upper();
    }
    switch (operator) {
      case LIKE:
        return newLikeClause(exp, v, syntax, false, matchSubstring, false);
      case NOT_LIKE:
        return newLikeClause(exp, v, syntax, false, matchSubstring, true);
      case EQ:
        return exp.eq(v);
      case NE:
        return exp.ne(v);
      case LT:
        return exp.lt(v);
      case LE:
        return exp.loe(v);
      case GT:
        return exp.gt(v);
      case GE:
        return exp.goe(v);
      default:
        throw new IllegalCaseException(StringSearchOperator.class, operator);
    }
  }

  /**
   * @param query the {@link JPAQuery} to modify.
   * @param expression the {@link StringExpression} to search on.
   * @param value the string value or pattern to search for.
   * @param config the {@link StringSearchConfigTo} to configure the search. May be {@code null} for regular equals
   *        search.
   */
  protected void whereString(JPAQuery<?> query, StringExpression expression, String value,
      StringSearchConfigTo config) {

    BooleanExpression clause = newStringClause(expression, value, config);
    if (clause != null) {
      query.where(clause);
    }
  }

  /**
   * @param <T> generic type of the {@link Collection} values for the {@link SimpleExpression#in(Collection)
   *        IN-clause}(s).
   * @param expression the {@link SimpleExpression} used to create the {@link SimpleExpression#in(Collection)
   *        IN-clause}(s).
   * @param inValues the {@link Collection} of values for the {@link SimpleExpression#in(Collection) IN-clause}(s).
   * @return the {@link BooleanExpression} for the {@link SimpleExpression#in(Collection) IN-clause}(s) oder
   *         {@code null} if {@code inValues} is {@code null} or {@link Collection#isEmpty() empty}.
   */
  protected <T> BooleanExpression newInClause(SimpleExpression<T> expression, Collection<T> inValues) {

    if ((inValues == null) || (inValues.isEmpty())) {
      return null;
    }
    int size = inValues.size();
    if (size == 1) {
      return expression.eq(inValues.iterator().next());
    }
    int maxSizeOfInClause = DatabaseWorkaround.getInstance().getMaxSizeOfInClause();
    if (size <= maxSizeOfInClause) {
      return expression.in(inValues);
    }
    LOG.warn("Creating workaround for IN-clause with {} items - this can cause performance problems.", size);
    List<T> values;
    if (inValues instanceof List) {
      values = (List<T>) inValues;
    } else {
      values = new ArrayList<>(inValues);
    }
    BooleanExpression predicate = null;
    int start = 0;
    while (start < size) {
      int end = start + maxSizeOfInClause;
      if (end > size) {
        end = size;
      }
      List<T> partition = values.subList(start, end);
      if (predicate == null) {
        predicate = expression.in(partition);
      } else {
        predicate = predicate.or(expression.in(partition));
      }
      start = end;
    }
    return predicate;
  }

  /**
   * @param <T> generic type of the {@link Collection} values for the {@link SimpleExpression#in(Collection)
   *        IN-clause}(s).
   * @param query the {@link JPAQuery} where to {@link JPAQuery#where(com.querydsl.core.types.Predicate) add} the
   *        {@link #newInClause(SimpleExpression, Collection) IN-clause(s)} from the other given parameters.
   * @param expression the {@link SimpleExpression} used to create the {@link SimpleExpression#in(Collection)
   *        IN-clause}(s).
   * @param inValues the {@link Collection} of values for the {@link SimpleExpression#in(Collection) IN-clause}(s).
   * @see #newInClause(SimpleExpression, Collection)
   */
  protected <T> void whereIn(JPAQuery<?> query, SimpleExpression<T> expression, Collection<T> inValues) {

    BooleanExpression inClause = newInClause(expression, inValues);
    if (inClause != null) {
      query.where(inClause);
    }
  }

}
