package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SideDishEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SideDishDao;

import javax.inject.Named;

/**
 * Implementation of {@link SideDishDao}.
 *
 */
@Named
public class SideDishDaoImpl extends ApplicationMasterDataDaoImpl<SideDishEntity> implements SideDishDao {

  /**
   * The constructor.
   */
  public SideDishDaoImpl() {

    super();
  }

  @Override
  protected Class<SideDishEntity> getEntityClass() {

    return SideDishEntity.class;
  }

}
