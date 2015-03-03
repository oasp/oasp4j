package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationRevisionedDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SideDishEntity;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

/**
 * {@link ApplicationRevisionedDao Data Access Object} for {@link SideDishEntity}.
 *
 * @author hohwille
 */
public interface SideDishDao extends ApplicationRevisionedDao<SideDishEntity>, MasterDataDao<SideDishEntity> {

}
