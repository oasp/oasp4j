package io.oasp.gastronomy.restaurant.staffmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.UserProfile;
import io.oasp.gastronomy.restaurant.general.common.api.Usermanagement;
import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.usecase.UcFindStaffMember;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.base.usecase.AbstractStaffMemberUc;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Named;

/**
 * Implementation of {@link UcFindStaffMember}.
 *
 * @author jozitz
 */
@Named
@UseCase
public class UcFindStaffMemberImpl extends AbstractStaffMemberUc implements UcFindStaffMember, Usermanagement {

  /**
   * The constructor.
   */
  public UcFindStaffMemberImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   *
   * Do not extract this method as a service, because of PermitAll. (only for login)
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_STAFF_MEMBER)
  public StaffMemberEto findStaffMemberByLogin(String login) {

    return privateFindStaffMemberByLogin(login);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_STAFF_MEMBER)
  public StaffMemberEto findStaffMember(Long id) {

    return getBeanMapper().map(getStaffMemberDao().find(id), StaffMemberEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_STAFF_MEMBER)
  @Deprecated
  public List<StaffMemberEto> findAllStaffMembers() {

    List<StaffMemberEntity> members = getStaffMemberDao().findAll();
    List<StaffMemberEto> membersBo = new ArrayList<>();

    for (StaffMemberEntity member : members) {
      membersBo.add(getBeanMapper().map(member, StaffMemberEto.class));
    }

    return membersBo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  // used during authentication so not authorization annotation (not even @PermitAll) can be used here
  public UserProfile findUserProfileByLogin(String login) {

    return privateFindStaffMemberByLogin(login);
  }

  /**
   * Do not extract this method as a service, because of PermitAll. (only for login)
   */
  private StaffMemberEto privateFindStaffMemberByLogin(String login) {

    return getBeanMapper().map(getStaffMemberDao().findByLogin(login), StaffMemberEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_STAFF_MEMBER)
  public PaginatedListTo<StaffMemberEto> findStaffMemberEtos(StaffMemberSearchCriteriaTo criteria) {

    // Uncomment next line in order to limit the maximum page size for the staff member search
    // criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);

    PaginatedListTo<StaffMemberEntity> offers = getStaffMemberDao().findStaffMembers(criteria);
    return mapPaginatedEntityList(offers, StaffMemberEto.class);
  }

}
