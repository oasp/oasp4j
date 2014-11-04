#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.logic.impl.usecase;

import ${package}.staffmanagement.logic.api.to.StaffMemberEto;
import ${package}.staffmanagement.logic.api.usecase.UcManageStaffMember;
import ${package}.staffmanagement.logic.base.usecase.AbstractStaffMemberUc;
import ${package}.staffmanagement.persistence.api.StaffMemberEntity;

import java.util.Objects;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcManageStaffMember}.
 *
 * @author jozitz
 */
@Named
public class UcManageStaffMemberImpl extends AbstractStaffMemberUc implements UcManageStaffMember {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageStaffMemberImpl.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteStaffMemberByLogin(String login) {

    getStaffMemberDao().delete(getStaffMemberDao().findByLogin(login));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteStaffMember(Long staffMemberId) {

    getStaffMemberDao().delete(staffMemberId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateStaffMember(StaffMemberEto staffMember) {

    Objects.requireNonNull(staffMember, "staffMemaber");

    StaffMemberEntity targetStaffMember = getStaffMemberDao().findByLogin(staffMember.getName());

    if (targetStaffMember == null) {
      /*
       * StaffMember is new: -> Save
       */
      getStaffMemberDao().save(getBeanMapper().map(staffMember, StaffMemberEntity.class));
      LOG.debug("StaffMember with login '" + staffMember.getName() + "' saved to the database.");
      return;
    }

    /*
     * StaffMember already exists: -> Update
     */
    targetStaffMember.setFirstName(staffMember.getFirstName());
    targetStaffMember.setLastName(staffMember.getLastName());
    targetStaffMember.setRole(staffMember.getRole());

    getStaffMemberDao().save(targetStaffMember);
    LOG.debug("StaffMember with login '" + targetStaffMember.getName() + "' updated in the database.");
    return;
  }
}
