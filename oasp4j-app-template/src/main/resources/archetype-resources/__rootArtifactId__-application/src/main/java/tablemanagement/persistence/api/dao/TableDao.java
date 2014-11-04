#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.tablemanagement.persistence.api.TableEntity;
import io.oasp.module.jpa.persistence.api.MasterDataDao;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link TableEntity} entity.
 *
 * @author hohwille
 */
public interface TableDao extends ApplicationDao<TableEntity>, MasterDataDao<TableEntity> {

  /**
   * Returns a list of free restaurant tables.
   *
   * @return {@link List} of free restaurant {@link TableEntity}s
   */
  List<TableEntity> getFreeTables();

}
