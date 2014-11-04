#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.logic.impl;

import ${package}.general.common.base.AbstractBeanMapperSupport;
import ${package}.staffmanagement.logic.api.Staffmanagement;
import ${package}.staffmanagement.logic.api.to.StaffMemberEto;
import ${package}.staffmanagement.logic.api.usecase.UcFindStaffMember;
import ${package}.staffmanagement.logic.api.usecase.UcManageStaffMember;

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
  public void setUcFindStaffMember(UcFindStaffMember ucFindStaffMember) {

    this.ucFindStaffMember = ucFindStaffMember;
  }

  /**
   * Sets the field 'ucManageStaffMember'.
   *
   * @param ucManageStaffMember New value for ucManageStaffMember
   */
  @Inject
  public void setUcManageStaffMember(UcManageStaffMember ucManageStaffMember) {

    this.ucManageStaffMember = ucManageStaffMember;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StaffMemberEto findStaffMemberByLogin(String login) {

    return this.ucFindStaffMember.findStaffMemberByLogin(login);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StaffMemberEto findStaffMember(Long id) {

    return this.ucFindStaffMember.findStaffMember(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<StaffMemberEto> findAllStaffMembers() {

    return this.ucFindStaffMember.findAllStaffMembers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateStaffMember(StaffMemberEto staffMember) {

    this.ucManageStaffMember.updateStaffMember(staffMember);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteStaffMemberByLogin(String login) {

    this.ucManageStaffMember.deleteStaffMemberByLogin(login);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteStaffMember(Long staffMemberId) {

    this.ucManageStaffMember.deleteStaffMember(staffMemberId);
  }
}
