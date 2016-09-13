package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationRevisionedDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.MealEntity;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

/**
 * {@link ApplicationRevisionedDao Data Access Object} for {@link MealEntity}.
 *
 */
public interface MealDao extends ApplicationRevisionedDao<MealEntity>, MasterDataDao<MealEntity> {

}
