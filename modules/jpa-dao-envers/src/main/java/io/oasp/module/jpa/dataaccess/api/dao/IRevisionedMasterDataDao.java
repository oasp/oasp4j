package io.oasp.module.jpa.dataaccess.api.dao;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;

/**
 * This is the interface for a {@link IDao} responsible for a {@link PersistenceEntity} that represents master-data. In
 * that case you typically have a limited number of entities in your persistent store and need operations like
 * {@link #findAll()}.<br>
 * <b>ATTENTION:</b><br>
 * Such operations are not part of {@link IGenericDao} or {@link IDao} as invoking them (accidently) could cause that an
 * extraordinary large number of entities are loaded into main memory and could cause serious performance and stability
 * disasters. So only extend this interface in case you are aware of what you are doing.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 *
 */
public interface IRevisionedMasterDataDao<E extends MutablePersistenceEntity<Long>> extends IGenericRevisionedDao<Long, E> {

  /**
   * @return an {@link Iterable} with ALL managed entities from the persistent store. Not exposed to API by default as
   *         this might not make sense for all kind of entities.
   */
  List<E> findAll();

}
