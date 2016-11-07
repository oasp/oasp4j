package io.oasp.module.jpa.dataaccess.base;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;
import net.sf.mmm.util.search.base.AbstractSearchCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Expression;

import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationResultTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;
import io.oasp.module.jpa.dataaccess.api.GenericDao;

/**
 * This is the abstract base-implementation of the {@link GenericDao} interface.
 *
 * @param <ID> is the generic type if the {@link PersistenceEntity#getId() primary key}.
 * @param <E> is the generic type of the managed {@link PersistenceEntity}.
 *
 */
// @Repository
public abstract class AbstractGenericDao<ID, E extends PersistenceEntity<ID>> implements GenericDao<ID, E> {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(AbstractGenericDao.class);

  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public AbstractGenericDao() {

    super();
  }

  /**
   * @return the {@link Class} reflecting the managed entity.
   */
  protected abstract Class<E> getEntityClass();

  /**
   * @return the {@link EntityManager} instance.
   */
  protected EntityManager getEntityManager() {

    return this.entityManager;
  }

  /**
   * @param entityManager the {@link EntityManager} to inject.
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {

    this.entityManager = entityManager;
  }

  /**
   * @return the name of the managed entity.
   */
  protected String getEntityName() {

    return getEntityClass().getSimpleName();
  }

  @Override
  public E save(E entity) {

    if (isNew(entity)) {
      this.entityManager.persist(entity);
      LOG.debug("Saved new {} with id {}.", getEntityName(), entity.getId());
      return entity;
    } else {
      if (this.entityManager.find(entity.getClass(), entity.getId()) != null) {
        E update = this.entityManager.merge(entity);
        LOG.debug("Updated {} with id {}.", getEntityName(), entity.getId());
        return update;
      } else {
        throw new EntityNotFoundException("Entity not found");
      }
    }
  }

  /**
   * Determines if the given {@link PersistenceEntity} is {@link PersistenceEntity#STATE_NEW new}.
   *
   * @param entity is the {@link PersistenceEntity} to check.
   * @return {@code true} if {@link PersistenceEntity#STATE_NEW new}, {@code false} otherwise (e.g.
   *         {@link PersistenceEntity#STATE_DETACHED detached} or {@link PersistenceEntity#STATE_MANAGED managed}.
   */
  protected boolean isNew(E entity) {

    return entity.getId() == null;
  }

  @Override
  public void save(Iterable<? extends E> entities) {

    for (E entity : entities) {
      save(entity);
    }
  }

  @Override
  public void forceIncrementModificationCounter(E entity) {

    getEntityManager().lock(entity, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
  }

  @Override
  public E findOne(ID id) {

    E entity = this.entityManager.find(getEntityClass(), id);
    return entity;
  }

  @Override
  public E find(ID id) throws ObjectNotFoundUserException {

    E entity = findOne(id);
    if (entity == null) {
      throw new ObjectNotFoundUserException(getEntityClass().getSimpleName(), id);
    }
    return entity;
  }

  @Override
  public boolean exists(ID id) {

    // pointless...
    return findOne(id) != null;
  }

  /**
   * @return an {@link Iterable} to find ALL {@link #getEntityClass() managed entities} from the persistent store. Not
   *         exposed to API by default as this might not make sense for all kind of entities.
   */
  protected List<E> findAll() {

    CriteriaQuery<E> query = this.entityManager.getCriteriaBuilder().createQuery(getEntityClass());
    Root<E> root = query.from(getEntityClass());
    query.select(root);
    TypedQuery<E> typedQuery = this.entityManager.createQuery(query);
    List<E> resultList = typedQuery.getResultList();
    LOG.debug("Query for all {} objects returned {} hit(s).", getEntityName(), resultList.size());
    return resultList;
  }

  @Override
  public List<E> findAll(Iterable<ID> ids) {

    CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<E> query = builder.createQuery(getEntityClass());
    Root<E> root = query.from(getEntityClass());
    query.select(root);
    query.where(root.get("id").in(ids));
    TypedQuery<E> typedQuery = this.entityManager.createQuery(query);
    List<E> resultList = typedQuery.getResultList();
    LOG.debug("Query for selection of {} objects returned {} hit(s).", getEntityName(), resultList.size());
    return resultList;
  }

  @Override
  public void delete(ID id) {

    E entity = this.entityManager.getReference(getEntityClass(), id);
    this.entityManager.remove(entity);
    LOG.debug("Deleted {} with ID {}.", getEntityName(), id);
  }

  @Override
  public void delete(E entity) {

    // entity might be detached and could cause trouble in entityManager on remove
    if (this.entityManager.contains(entity)) {
      this.entityManager.remove(entity);
      LOG.debug("Deleted {} with ID {}.", getEntityName(), entity.getId());
    } else {
      delete(entity.getId());
    }

  }

  @Override
  public void delete(Iterable<? extends E> entities) {

    for (E entity : entities) {
      delete(entity);
    }
  }

  @SuppressWarnings("javadoc")
  protected PaginatedListTo<E> findPaginated(SearchCriteriaTo criteria, Query query, Expression<E> expr) {

    throw new UnsupportedOperationException("Pagination is not yet supported for generic JPA queries.");
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
   * @param criteria contains information about the requested page.
   * @param query is a query which is preconfigured with the desired conditions for the search.
   * @param expr is used for the final mapping from the SQL result to the entities.
   * @return a paginated list.
   */
  protected PaginatedListTo<E> findPaginated(SearchCriteriaTo criteria, JPAQuery query, Expression<E> expr) {

    applyCriteria(criteria, query);

    PaginationTo pagination = criteria.getPagination();

    PaginationResultTo paginationResult = createPaginationResult(pagination, query);

    applyPagination(pagination, query);
    List<E> paginatedList = query.list(expr);

    return new PaginatedListTo<>(paginatedList, paginationResult);
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
  protected PaginationResultTo createPaginationResult(PaginationTo pagination, JPAQuery query) {

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
  protected Long calculateTotalBeforePagination(PaginationTo pagination, JPAQuery query) {

    Long total = null;
    if (pagination.isTotal()) {
      total = query.clone().count();
    }

    return total;
  }

  /**
   * Applies the {@link PaginationTo pagination criteria} to the given {@link JPAQuery}.
   *
   * @param pagination is the {@link PaginationTo pagination criteria} to apply.
   * @param query is the {@link JPAQuery} to apply to.
   */
  protected void applyPagination(PaginationTo pagination, JPAQuery query) {

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
  protected void applyCriteria(AbstractSearchCriteria criteria, JPAQuery query) {

    Integer limit = criteria.getMaximumHitCount();
    if (limit != null) {
      query.limit(limit);
    }
    int offset = criteria.getHitOffset();
    if (offset > 0) {
      query.offset(offset);
    }
    Long timeout = criteria.getSearchTimeout();
    if (timeout != null) {
      query.setHint("javax.persistence.query.timeout", timeout.intValue());
    }
  }

  /**
   * Applies the meta-data of the given {@link AbstractSearchCriteria search criteria} to the given {@link Query}.
   *
   * @param criteria is the {@link AbstractSearchCriteria search criteria} to apply.
   * @param query is the {@link Query} to apply to.
   */
  protected void applyCriteria(AbstractSearchCriteria criteria, Query query) {

    Integer limit = criteria.getMaximumHitCount();
    if (limit != null) {
      query.setMaxResults(limit);
    }
    int offset = criteria.getHitOffset();
    if (offset > 0) {
      query.setFirstResult(offset);
    }
    Long timeout = criteria.getSearchTimeout();
    if (timeout != null) {
      query.setHint("javax.persistence.query.timeout", timeout.intValue());
    }
  }

  /**
   * Applies the meta-data of the given {@link SearchCriteriaTo search criteria} to the given {@link Query}.
   *
   * @param criteria is the {@link AbstractSearchCriteria search criteria} to apply.
   * @param query is the {@link JPAQuery} to apply to.
   */
  protected void applyCriteria(SearchCriteriaTo criteria, JPAQuery query) {

    Integer timeout = criteria.getSearchTimeout();
    if (timeout != null) {
      query.setHint("javax.persistence.query.timeout", timeout.intValue());
    }
  }

}
