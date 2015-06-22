package io.oasp.gastronomy.restaurant.staffmanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.base.AbstractBeanMapperSupport;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.usecase.UcFindStaffMember;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.usecase.UcManageStaffMember;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

/**
 * Implementation of {@link Staffmanagement}.
 *
 * @author etomety
 */
@Named
@Component
public class StaffmanagementImpl extends AbstractBeanMapperSupport implements Staffmanagement {

  private UcFindStaffMember ucFindStaffMember;

  private UcManageStaffMember ucManageStaffMember;

  /**
   * Sets the field 'ucFindStaffMember'.
   *
   * @param ucFindStaffMember New value for ucFindStaffMember
   */
  @Inject
  @UseCase
  public void setUcFindStaffMember(UcFindStaffMember ucFindStaffMember) {

    this.ucFindStaffMember = ucFindStaffMember;
  }

  /**
   * Sets the field 'ucManageStaffMember'.
   *
   * @param ucManageStaffMember New value for ucManageStaffMember
   */
  @Inject
  @UseCase
  public void setUcManageStaffMember(UcManageStaffMember ucManageStaffMember) {

    this.ucManageStaffMember = ucManageStaffMember;
  }

  @Override
  public StaffMemberEto findStaffMemberByLogin(String login) {

    return this.ucFindStaffMember.findStaffMemberByLogin(login);
  }

  @Override
  public StaffMemberEto findStaffMember(Long id) {

    return this.ucFindStaffMember.findStaffMember(id);
  }

  @Override
  public List<StaffMemberEto> findAllStaffMembers() {

    return this.ucFindStaffMember.findAllStaffMembers();
  }

  @Override
  public StaffMemberEto saveStaffMember(StaffMemberEto staffMember) {

    return this.ucManageStaffMember.saveStaffMember(staffMember);
  }

  @Override
  public void deleteStaffMemberByLogin(String login) {

    this.ucManageStaffMember.deleteStaffMemberByLogin(login);
  }

  @Override
  public void deleteStaffMember(Long staffMemberId) {

    this.ucManageStaffMember.deleteStaffMember(staffMemberId);
  }
}
