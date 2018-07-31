package io.oasp.example.component.dataaccess.api;

import static com.querydsl.core.alias.Alias.$;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.example.component.common.api.Foo;
import io.oasp.example.component.common.api.to.FooSearchCriteriaTo;
import io.oasp.module.jpa.dataaccess.api.QueryUtil;
import io.oasp.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link FooEntity}.
 */
public interface FooRepository extends DefaultRepository<FooEntity> {

  /**
   * @param message the {@link Foo#getMessage() message} to match.
   * @param pageable the {@link Pageable} to configure the paging.
   * @return the {@link Page} of {@link FooEntity} objects that matched the search.
   */
  @Query("SELECT foo FROM FooEntity foo" //
      + " WHERE foo.message = :message")
  Page<FooEntity> findByMessage(@Param("message") String message, Pageable pageable);

  /**
   * @param criteria the {@link FooSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link FooEntity} objects that matched the search.
   */
  default Page<FooEntity> findByCriteria(FooSearchCriteriaTo criteria) {

    FooEntity alias = newDslAlias();
    JPAQuery<FooEntity> query = newDslQuery(alias);
    String message = criteria.getMessage();
    if ((message != null) && !message.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getMessage()), message, criteria.getMessageOption());
    }
    return QueryUtil.get().findPaginated(criteria.getPageable(), query, false);
  }

}
