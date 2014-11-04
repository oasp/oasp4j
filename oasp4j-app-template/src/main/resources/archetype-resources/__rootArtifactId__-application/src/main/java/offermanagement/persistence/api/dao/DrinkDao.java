#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.offermanagement.persistence.api.DrinkEntity;
import io.oasp.module.jpa.persistence.api.MasterDataDao;

/**
 * {@link ApplicationDao Data Access Object} for {@link DrinkEntity}.
 *
 * @author hohwille
 */
public interface DrinkDao extends ApplicationDao<DrinkEntity>, MasterDataDao<DrinkEntity> {

}
