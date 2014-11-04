#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.offermanagement.persistence.api.MealEntity;
import io.oasp.module.jpa.persistence.api.MasterDataDao;

/**
 * {@link ApplicationDao Data Access Object} for {@link MealEntity}.
 *
 * @author hohwille
 */
public interface MealDao extends ApplicationDao<MealEntity>, MasterDataDao<MealEntity> {

}
