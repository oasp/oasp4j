#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.staffmanagement.persistence.api.StaffMemberEntity;
import io.oasp.module.jpa.persistence.api.MasterDataDao;

/**
 * {@link ApplicationDao Data Access Object} for {@link StaffMemberEntity} entity.
 *
 * @author jozitz
 */
public interface StaffMemberDao extends ApplicationDao<StaffMemberEntity>, MasterDataDao<StaffMemberEntity> {

  /**
   * Searchs the restaurant staff member with identifier 'login' within the database.
   *
   * @param login staff member identifier
   * @return Staffmember
   */
  StaffMemberEntity findByLogin(String login);

}
