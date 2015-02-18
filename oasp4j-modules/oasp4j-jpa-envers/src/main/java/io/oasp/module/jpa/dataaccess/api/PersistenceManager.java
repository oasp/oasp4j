/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.jpa.dataaccess.api;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the interface for a manager of the persistence layer. It is the entrance point to the persistence component.
 * In a common environment there is typically only one single instance of this interface active. <br>
 * The {@link PersistenceManager} acts as delegation to the {@link GenericDao} {@link GenericDao#getEntityClass()
 * responsible} for the according {@link PersistenceEntity} in the invoked methods. This guarantees that individual
 * custom logic is also invoked in case of generic access. <br>
 * <b>NOTE:</b><br>
 * You will have to perform operations of this persistence-layer in a transactional context. The suggested way is to use
 * {@link net.sf.mmm.transaction.api.TransactionExecutor}.
 *
 * @see javax.persistence.EntityManager
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification
public interface PersistenceManager {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.persistence.api.PersistenceManager";

  /**
   * This method determines if the {@link #getDao(Class) DAO} for the given <code>entityClass</code> is available. This
   * can be useful to prevent exceptions.
   *
   * @param entityClass is the type of the {@link PersistenceEntity} for which the according {@link GenericDao} is
   *        requested.
   * @return <code>true</code> if {@link #getDao(Class)} will return a valid DAO for the given <code>entityClass</code>,
   *         <code>false</code> otherwise (if an exception would be thrown).
   */
  boolean hasDao(Class<? extends PersistenceEntity<?>> entityClass);

  /**
   * This method gets the individual {@link GenericDao} {@link GenericDao#getEntityClass() responsible} for the given
   * <code>entityClass</code>.
   *
   * @param <ENTITY> is the generic entity-type.
   * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}.
   * @param entityClass is the type of the {@link PersistenceEntity} for which the according {@link GenericDao} is
   *        requested.
   * @return the {@link GenericDao} responsible for the given <code>entityClass</code>.
   * @throws ObjectNotFoundException if the requested {@link GenericDao DAO} could NOT be found.
   */
  <ID, E extends PersistenceEntity<ID>> GenericDao<ID, E> getDao(Class<E> entityClass) throws ObjectNotFoundException;

  /**
   * This method loads the {@link PersistenceEntity} with the given <code>entityClass</code> and <code>id</code> from
   * the persistent store.
   *
   * @see GenericDao#find(Object)
   *
   * @param <ENTITY> is the generic type of the <code>entityClass</code>.
   * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}.
   * @param entityClass is the class reflecting the type of the requested entity.
   * @param id is the {@link PersistenceEntity#getId() primary key} of the requested entity.
   * @return the requested entity.
   * @throws ObjectNotFoundException if the requested {@link PersistenceEntity entity} could NOT be found.
   */
  <ID, ENTITY extends PersistenceEntity<ID>> ENTITY find(Class<ENTITY> entityClass, ID id)
      throws ObjectNotFoundException;

  /**
   * This method loads the {@link PersistenceEntity} with the given <code>entityClass</code> and <code>id</code> from
   * the persistent store.
   *
   * @see GenericDao#findIfExists(Object)
   *
   * @param <ENTITY> is the generic type of the <code>entityClass</code>.
   * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}.
   * @param entityClass is the class reflecting the type of the requested entity.
   * @param id is the {@link PersistenceEntity#getId() primary key} of the requested entity.
   * @return the requested entity or <code>null</code> if it does NOT exist.
   */
  <ID, ENTITY extends PersistenceEntity<ID>> ENTITY findIfExists(Class<ENTITY> entityClass, ID id);

  /**
   * This method creates a lazy reference proxy of the {@link PersistenceEntity} with the given <code>entityClass</code>
   * and <code>id</code> from the persistent store.
   *
   * @see GenericDao#getReference(Object)
   *
   * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}.
   * @param <ENTITY> is the generic type of the <code>entityClass</code>.
   * @param entityClass is the class reflecting the type of the requested entity.
   * @param id is the {@link PersistenceEntity#getId() primary key} of the requested entity.
   * @return the requested entity.
   * @throws ObjectNotFoundException if the requested {@link PersistenceEntity entity} could NOT be found.
   */
  <ID, ENTITY extends PersistenceEntity<ID>> ENTITY getReference(Class<ENTITY> entityClass, ID id)
      throws ObjectNotFoundException;

  /**
   * This method creates a new and transient instance for the given <code>entityClass</code>. <br>
   *
   * @see GenericDao#create()
   *
   * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}.
   * @param <ENTITY> is the generic type of the <code>entityClass</code>.
   * @param entityClass is the {@link Class} reflecting the entity to create. This may also be the interface of the
   *        entity.
   * @return the new instance.
   */
  <ID, ENTITY extends PersistenceEntity<ID>> ENTITY create(Class<ENTITY> entityClass);

  /**
   * This method saves the given <code>entity</code>. <br>
   *
   * @see GenericDao#save(PersistenceEntity)
   *
   * @param entity is the entity to save.
   */
  void save(PersistenceEntity<?> entity);

  /**
   * This method deletes the given <code>entity</code>. <br>
   *
   * @see GenericDao#delete(PersistenceEntity)
   *
   * @param entity is the entity to delete.
   */
  void delete(PersistenceEntity<?> entity);

  /**
   * This method gets the {@link Class} reflecting the given <code>{@link PersistenceEntity entity}</code>. Unlike
   * <code>entity.{@link #getClass()}</code> this method will always return the real {@link GenericDao#getEntityClass()
   * class defining the entity}. <br>
   * The underlying JPA implementation may create a dynamic proxy or byte-code generated sub-class that extends the
   * entity-class. In such case <code>entity.{@link #getClass()}</code> will return the {@link Class} of the proxy
   * instead. In such case this method will return the real entity-class adapted by the proxy.
   *
   * @param entity is the {@link PersistenceEntity} for which the class is requested.
   * @return the {@link Class} reflecting the given <code>entity</code>.
   */
  Class<? extends PersistenceEntity<?>> getEntityClass(PersistenceEntity<?> entity);

}
