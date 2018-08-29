package io.oasp.module.jpa.dataaccess.base.dao;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;

import io.oasp.module.jpa.dataaccess.api.dao.IMasterDataDao;

/**
 * This is the abstract base implementation of {@link IMasterDataDao}.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 */
public abstract class AbstractMasterDataDaoImpl<E extends PersistenceEntity<Long>> extends AbstractDaoImpl<E>
    implements IMasterDataDao<E> {

  /**
   * The constructor.
   */
  public AbstractMasterDataDaoImpl() {

    super();
  }

  @Override
  public List<E> findAll() {

    return super.findAll();
  }

}
