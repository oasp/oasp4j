package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.MealEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.MealDao;

import javax.inject.Named;

/**
 * Implementation of {@link MealDao}.
 *
 */
@Named
public class MealDaoImpl extends ApplicationMasterDataDaoImpl<MealEntity> implements MealDao {

  /**
   * The constructor.
   */
  public MealDaoImpl() {

    super();
  }

  @Override
  protected Class<MealEntity> getEntityClass() {

    return MealEntity.class;
  }

}
