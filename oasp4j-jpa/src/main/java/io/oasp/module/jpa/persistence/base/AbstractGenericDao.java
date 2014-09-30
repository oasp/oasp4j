package io.oasp.module.jpa.persistence.base;

import io.oasp.module.jpa.persistence.api.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.sf.mmm.util.entity.api.PersistenceEntity;

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
   * @param entityManager the entityManager to set
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {

    this.entityManager = entityManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E save(E entity) {

    if (isNew(entity)) {
      this.entityManager.persist(entity);
      return entity;
    } else {
      return this.entityManager.merge(entity);
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
  public E findOne(ID id) {

    E entity = this.entityManager.find(getEntityClass(), id);
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
  protected Iterable<E> findAll() {

    CriteriaQuery<E> query = this.entityManager.getCriteriaBuilder().createQuery(getEntityClass());
    Root<E> root = query.from(getEntityClass());
    query.select(root);
    TypedQuery<E> typedQuery = this.entityManager.createQuery(query);
    return typedQuery.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<E> findAll(Iterable<ID> ids) {

    CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<E> query = builder.createQuery(getEntityClass());
    Root<E> root = query.from(getEntityClass());
    query.select(root);
    query.where(root.get("id").in(ids));
    TypedQuery<E> typedQuery = this.entityManager.createQuery(query);
    return typedQuery.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(ID id) {

    E entity = this.entityManager.getReference(getEntityClass(), id);
    this.entityManager.remove(entity);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(E entity) {

    if (this.entityManager.contains(entity)) {
      this.entityManager.remove(entity);
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

}
