package io.oasp.module.jpa.dataaccess.api;

import java.util.Collection;
import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.module.jpa.common.api.to.LikePatternSyntax;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.StringSearchConfigTo;
import io.oasp.module.jpa.common.api.to.StringSearchOperator;

/**
 * Helper class for generic handling of {@link net.sf.mmm.util.entity.api.PersistenceEntity persistence entities} (based
 * on {@link javax.persistence.EntityManager}).
 *
 * @since 3.0.0
 */
public class QueryDslUtil extends QueryDslHelper {

  private static final QueryDslUtil INSTANCE = new QueryDslUtil();

  /**
   * @param <E> generic type of the entity.
   * @param criteria contains information about the requested page.
   * @param query is a query which is preconfigured with the desired conditions for the search including search order.
   * @return a paginated list.
   * @see #findPaginated(SearchCriteriaTo, JPAQuery, boolean)
   */
  public <E> PaginatedListTo<E> findPaginated(SearchCriteriaTo criteria, JPAQuery<E> query) {

    return findPaginatedGeneric(criteria, query, true);
  }

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
  public <E> PaginatedListTo<E> findPaginated(SearchCriteriaTo criteria, JPAQuery<E> query, boolean applySortOrder) {

    return findPaginatedGeneric(criteria, query, true);
  }

  @Override
  public void whereLike(JPAQuery<?> query, StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring) {

    super.whereLike(query, expression, pattern, syntax, ignoreCase, matchSubstring);
  }

  @Override
  public BooleanExpression newLikeClause(StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring, boolean negate) {

    return super.newLikeClause(expression, pattern, syntax, ignoreCase, matchSubstring, negate);
  }

  @Override
  public BooleanExpression newStringClause(StringExpression expression, String value, StringSearchConfigTo config) {

    return super.newStringClause(expression, value, config);
  }

  @Override
  public BooleanExpression newStringClause(StringExpression expression, String value, StringSearchOperator operator,
      LikePatternSyntax syntax, boolean ignoreCase, boolean matchSubstring) {

    return super.newStringClause(expression, value, operator, syntax, ignoreCase, matchSubstring);
  }

  @Override
  public void whereString(JPAQuery<?> query, StringExpression expression, String value, StringSearchConfigTo config) {

    super.whereString(query, expression, value, config);
  }

  @Override
  public void whereNotLike(JPAQuery<?> query, StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring) {

    super.whereNotLike(query, expression, pattern, syntax, ignoreCase, matchSubstring);
  }

  @Override
  public BooleanExpression newLikeClause(StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring) {

    return super.newLikeClause(expression, pattern, syntax, ignoreCase, matchSubstring);
  }

  @Override
  public BooleanExpression newNotLikeClause(StringExpression expression, String pattern, LikePatternSyntax syntax,
      boolean ignoreCase, boolean matchSubstring) {

    return super.newNotLikeClause(expression, pattern, syntax, ignoreCase, matchSubstring);
  }

  @Override
  public <T> BooleanExpression newInClause(SimpleExpression<T> expression, Collection<T> inValues) {

    return super.newInClause(expression, inValues);
  }

  @Override
  public <T> void whereIn(JPAQuery<?> query, SimpleExpression<T> expression, Collection<T> inValues) {

    super.whereIn(query, expression, inValues);
  }

  /**
   * @return the {@link QueryDslUtil} instance.
   */
  public static QueryDslUtil get() {

    return INSTANCE;
  }

}
