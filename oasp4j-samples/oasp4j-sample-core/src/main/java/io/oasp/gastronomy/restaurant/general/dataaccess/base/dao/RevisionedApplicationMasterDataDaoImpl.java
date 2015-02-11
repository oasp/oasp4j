package io.oasp.gastronomy.restaurant.general.dataaccess.base.dao;

import io.oasp.module.jpa.dataaccess.api.MasterDataDao;
import io.oasp.module.jpa.dataaccess.api.RevisionedPersistenceEntity;

import java.util.List;

/**
 * This is the abstract base implemetation of {@link MasterDataDao} based on {@link ApplicationDaoImpl}.
 *
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 *
 * @author hohwille
 */
public abstract class RevisionedApplicationMasterDataDaoImpl<ENTITY extends RevisionedPersistenceEntity<Long>> extends
    RevisionedApplicationDaoImpl<ENTITY> implements MasterDataDao<ENTITY> {

  /**
   * The constructor.
   */
  public RevisionedApplicationMasterDataDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ENTITY> findAll() {

    return super.findAll();
  }

}
