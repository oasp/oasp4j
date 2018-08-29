package io.oasp.module.jpa.dataaccess.base;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;

import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

/**
 * This is the abstract base implementation of {@link MasterDataDao}.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 */
@Deprecated
public abstract class AbstractMasterDataDao<E extends PersistenceEntity<Long>> extends AbstractDao<E>
    implements MasterDataDao<E> {

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
