package io.oasp.module.jpa.dataaccess.api;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

/**
 * This is the interface for a <em>Data Access Object</em> (DAO). It acts as a manager responsible for the persistence
 * operations on a specific {@link PersistenceEntity entity} {@literal <E>}.<br/>
 * This is base interface contains the CRUD operations:
 * <ul>
 * <li>Create: call {@link #save(PersistenceEntity)} on a new entity.</li>
 * <li>Retrieve: use <code>find*</code> methods such as {@link #findOne(Object)}. More specific queries will be added in
 * dedicated DAO interfaces.</li>
 * <li>Update: done automatically by JPA vendor (hibernate) on commit or call {@link #save(PersistenceEntity)} to
 * {@link javax.persistence.EntityManager#merge(Object) merge} an entity.</li>
 * <li>Delete: call {@link #delete(PersistenceEntity)} or {@link #delete(Object)}.</li>
 * </ul>
 * For each (non-abstract) implementation of {@link PersistenceEntity entity} <code>MyEntity</code> you should create an
 * interface interface <code>MyEntityDao</code> that inherits from this {@link GenericDao} interface. Also you create an
 * implementation of that interface <code>MyEntityDaoImpl</code> that you derive from
 * {@link io.oasp.module.jpa.dataaccess.base.AbstractGenericDao}.
 *
 * @param <ID> is the generic type if the {@link PersistenceEntity#getId() primary key}.
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 *
 * @author hohwille
 */
public interface GenericDao<ID, E extends PersistenceEntity<ID>> {

  /**
   * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
   *
   * @param entity
   * @return the saved entity
   */
  E save(E entity);

  /**
   * Saves all given entities.
   *
   * @param entities
   * @throws IllegalArgumentException in case the given entity is (@literal null}.
   */
  void save(Iterable<? extends E> entities);

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal null} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   * @throws ObjectNotFoundUserException if the requested entity does not exists (use {@link #findOne(Object)} to
   *         prevent).
   */
  E find(ID id) throws ObjectNotFoundUserException;

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal null} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  E findOne(ID id);

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return true if an entity with the given id exists, {@literal false} otherwise
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  boolean exists(ID id);

  /**
   * Returns all instances of the type with the given IDs.
   *
   * @param ids are the IDs of all entities to retrieve e.g. as {@link java.util.List}.
   * @return an {@link Iterable} with all {@link PersistenceEntity entites} for the given <code>ids</code>.
   */
  List<E> findAll(Iterable<ID> ids);

  // /**
  // * Returns the number of entities available.
  // *
  // * @return the number of entities
  // */
  // long count();

  /**
   * Deletes the entity with the given id.
   *
   * @param id must not be {@literal null}.
   * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
   */
  void delete(ID id);

  /**
   * Deletes a given entity.
   *
   * @param entity
   * @throws IllegalArgumentException in case the given entity is (@literal null}.
   */
  void delete(E entity);

  /**
   * Deletes the given entities.
   *
   * @param entities
   * @throws IllegalArgumentException in case the given {@link Iterable} is (@literal null}.
   */
  void delete(Iterable<? extends E> entities);

  /**
   * Enforces to increment the {@link E#getModificationCounter() modificationCounter} e.g. to enforce that a parent
   * object gets locked when its children are modified.
   *
   * @param entity that is getting checked.
   */
  public abstract void forceIncrementModificationCounter(E entity);

}
