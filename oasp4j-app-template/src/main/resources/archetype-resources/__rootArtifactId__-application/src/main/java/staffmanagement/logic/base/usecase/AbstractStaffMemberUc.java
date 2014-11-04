#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.logic.base.usecase;

import ${package}.general.logic.base.AbstractUc;
import ${package}.staffmanagement.persistence.api.dao.StaffMemberDao;

import javax.inject.Inject;

/**
 *
 * @author jozitz
 */
public abstract class AbstractStaffMemberUc extends AbstractUc {

  /** @see ${symbol_pound}getStaffMemberDao() */
  private StaffMemberDao staffMemberDao;

  /**
   * @return the {@link StaffMemberDao} instance.
   */
  public StaffMemberDao getStaffMemberDao() {

    return this.staffMemberDao;
  }

  /**
   * Sets the field 'staffMemberDao'.
   *
   * @param staffMemberDao New value for staffMemberDao
   */
  @Inject
  public void setStaffMemberDao(StaffMemberDao staffMemberDao) {

    this.staffMemberDao = staffMemberDao;
  }

}
