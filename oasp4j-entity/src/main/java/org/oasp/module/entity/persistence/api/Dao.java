package org.oasp.module.entity.persistence.api;

/**
 * This is the interface for a Data Access Object (DAO).
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 *
 * @author hohwille
 */
public interface Dao<E extends PersistenceEntity> {

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
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  E findOne(Long id);

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return true if an entity with the given id exists, {@literal false} otherwise
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  boolean exists(Long id);

  // /**
  // * Returns all instances of the type.
  // *
  // * @return all entities
  // */
  // Iterable<E> findAll();

  /**
   * Returns all instances of the type with the given IDs.
   *
   * @param ids
   * @return
   */
  Iterable<E> findAll(Iterable<Long> ids);

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
  void delete(Long id);

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

  // /**
  // * Deletes all entities managed by the repository.
  // */
  // void deleteAll();

}
