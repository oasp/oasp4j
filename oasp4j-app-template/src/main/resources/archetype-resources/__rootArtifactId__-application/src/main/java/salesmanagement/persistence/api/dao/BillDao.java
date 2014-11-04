#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.salesmanagement.persistence.api.BillEntity;

/**
 * {@link ApplicationDao Data Access Object} for {@link BillEntity} entity.
 *
 * @author jozitz
 */
public interface BillDao extends ApplicationDao<BillEntity> {

}
