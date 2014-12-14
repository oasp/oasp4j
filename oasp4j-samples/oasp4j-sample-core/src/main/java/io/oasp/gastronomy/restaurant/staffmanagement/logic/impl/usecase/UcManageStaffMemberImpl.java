package io.oasp.gastronomy.restaurant.staffmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.usecase.UcManageStaffMember;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.base.usecase.AbstractStaffMemberUc;

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
  public StaffMemberEto saveStaffMember(StaffMemberEto staffMember) {

    Objects.requireNonNull(staffMember, "staffMemaber");

    Long id = staffMember.getId();
    StaffMemberEntity targetStaffMember = null;

    if (id != null) {
      targetStaffMember = getStaffMemberDao().find(id);
    }
    if (targetStaffMember == null) {
      // StaffMember is new: -> create
      LOG.debug("Saving StaffMember with id '{}' to the database.", id);
    } else {
      // StaffMember already exists: -> Update
      LOG.debug("Updating StaffMember with id '{}' in the database.", id);
      if (!Objects.equals(targetStaffMember.getName(), staffMember.getName())) {
        LOG.debug("Chaning login of StaffMember with id '{}' from '{}' to '{}' in the database.", id,
            targetStaffMember.getName(), staffMember.getName());
      }
    }
    StaffMemberEntity persistedStaffMember =
        getStaffMemberDao().save(getBeanMapper().map(staffMember, StaffMemberEntity.class));
    return getBeanMapper().map(persistedStaffMember, StaffMemberEto.class);
  }
}
