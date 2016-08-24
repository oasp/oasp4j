package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * This is the abstract base implementation of {@link MasterDataDao}.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}. Should be derived from
 *        {@link io.oasp.module.jpa.dataaccess.api.AbstractPersistenceEntity}.
 *
 */
public abstract class AbstractMasterDataDao<E extends PersistenceEntity<Long>> extends AbstractDao<E> implements
    MasterDataDao<E> {

  /**
   * The constructor.
   */
  public AbstractMasterDataDao() {

    super();
  }

  @Override
  public List<E> findAll() {

    return super.findAll();
  }

}
