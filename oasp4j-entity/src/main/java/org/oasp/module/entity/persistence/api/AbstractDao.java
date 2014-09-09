package org.oasp.module.entity.persistence.api;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * TODO hohwille This type ...
 *
 * @author hohwille
 */
// @Repository
public abstract class AbstractDao<E extends PersistenceEntity> implements Dao<E> {

  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public AbstractDao() {

    super();
  }

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
  public E findOne(Long id) {

    E entity = this.entityManager.find(getEntityClass(), id);
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists(Long id) {

    // pointless...
    return findOne(id) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<E> findAll(Iterable<Long> ids) {

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
  public void delete(Long id) {

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
