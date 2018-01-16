package io.oasp.example.component.dataaccess.api;

import static com.querydsl.core.alias.Alias.$;

import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.example.component.common.api.to.FooSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.dataaccess.api.QueryDslUtil;
import io.oasp.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link FooEntity}.
 */
public interface FooRepository extends DefaultRepository<FooEntity> {

  default PaginatedListTo<FooEntity> findByCriteria(FooSearchCriteriaTo criteria) {

    FooEntity alias = newDslAlias();
    JPAQuery<FooEntity> query = newDslQuery(alias);
    String message = criteria.getMessage();
    if ((message != null) && !message.isEmpty()) {
      QueryDslUtil.get().whereString(query, $(alias.getMessage()), message, criteria.getMessageOption());
    }
    return QueryDslUtil.get().findPaginated(criteria, query);
  }

}
