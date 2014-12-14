package io.oasp.gastronomy.restaurant.staffmanagement.logic.base.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.dao.StaffMemberDao;

import javax.inject.Inject;

/**
 *
 * @author jozitz
 */
public abstract class AbstractStaffMemberUc extends AbstractUc {

  /** @see #getStaffMemberDao() */
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
