package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.RevisionedApplicationDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;
import io.oasp.module.jpa.dataaccess.api.RevisionedMasterDataDao;

/**
 * {@link RevisionedApplicationDao Data Access Object} for {@link DrinkEntity}.
 *
 * @author hohwille
 */
public interface DrinkDao extends RevisionedApplicationDao<DrinkEntity>, RevisionedMasterDataDao<DrinkEntity> {

}
