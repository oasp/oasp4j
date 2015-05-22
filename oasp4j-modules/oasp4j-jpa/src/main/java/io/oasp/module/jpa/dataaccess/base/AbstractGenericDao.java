package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.jpa.common.api.to.PaginatedEntityListTo;
import io.oasp.module.jpa.common.api.to.PaginationResultTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;
import io.oasp.module.jpa.dataaccess.api.GenericDao;

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

/**
 * This is the abstract base-implementation of the {@link GenericDao} interface.
 *
 * @param <ID> is the generic type if the {@link PersistenceEntity#getId() primary key}.
 * @param <E> is the generic type of the managed {@link PersistenceEntity}.
 *
 * @author hohwille
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

  /**
   * {@inheritDoc}
   */
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
   * @return <code>true</code> if {@link PersistenceEntity#STATE_NEW new}, <code>false</code> otherwise (e.g.
   *         {@link PersistenceEntity#STATE_DETACHED detached} or {@link PersistenceEntity#STATE_MANAGED managed}.
   */
  protected boolean isNew(E entity) {

    return entity.getId() == null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(Iterable<? extends E> entities) {

    for (E entity : entities) {
      save(entity);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void forceIncrementModificationCounter(E entity) {

    getEntityManager().lock(entity, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E findOne(ID id) {

    E entity = this.entityManager.find(getEntityClass(), id);
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E find(ID id) throws ObjectNotFoundUserException {

    E entity = findOne(id);
    if (entity == null) {
      throw new ObjectNotFoundUserException(getEntityClass().getSimpleName(), id);
    }
    return entity;
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(ID id) {

    E entity = this.entityManager.getReference(getEntityClass(), id);
    this.entityManager.remove(entity);
    LOG.debug("Deleted {} with ID {}.", getEntityName(), id);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Iterable<? extends E> entities) {

    for (E entity : entities) {
      delete(entity);
    }
  }

  /**
   * {@inheritDoc}
   */
  public PaginatedEntityListTo<E> findPaginated(PaginationTo pagination, JPAQuery query, Expression<E> expr) {

    Long total = null;
    if (pagination.isTotal()) {
      total = query.clone().count();
    } else {
      total = null;
    }
    PaginationResultTo paginationResult = new PaginationResultTo(pagination, total);

    applyPagination(pagination, query);
    List<E> paginatedList = query.list(expr);

    return new PaginatedEntityListTo<>(paginatedList, paginationResult);
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

}
