package io.oasp.gastronomy.restaurant.general.dataaccess.base.dao;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;
import io.oasp.module.jpa.dataaccess.api.RevisionedMasterDataDao;

import java.util.List;

/**
 * This is the abstract base implemetation of {@link RevisionedMasterDataDao} based on {@link ApplicationDaoImpl}.
 *
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 *
 * @author hohwille
 */
public abstract class ApplicationMasterDataDaoImpl<ENTITY extends MutablePersistenceEntity<Long>> extends
    ApplicationDaoImpl<ENTITY> implements RevisionedMasterDataDao<ENTITY> {

  /**
   * The constructor.
   */
  public ApplicationMasterDataDaoImpl() {

    super();
  }

  @Override
  public List<ENTITY> findAll() {

    return super.findAll();
  }

}
