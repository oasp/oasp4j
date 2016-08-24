package io.oasp.gastronomy.restaurant.staffmanagement.logic.api;

import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

/**
 * Interface for StaffManagement component.
 *
 */
public interface Staffmanagement {

  /**
   * @param id the {@link StaffMemberEto#getId() ID} of the requested staff member.
   * @return The {@link StaffMemberEto} with the given <code>id</code> or {@code null} if no such object exists.
   */
  StaffMemberEto findStaffMember(Long id);

  /**
   * @param login The {@link StaffMemberEto#getName() login} of the requested staff member.
   * @return The {@link StaffMemberEto} with the given <code>login</code> or {@code null} if no such object exists.
   */
  StaffMemberEto findStaffMemberByLogin(String login);

  /**
   * @return {@link List} of all existing {@link StaffMemberEto staff members}.
   */
  List<StaffMemberEto> findAllStaffMembers();

  /**
   * Returns a list of staff memberss matching the search criteria.
   *
   * @param criteria the {@link StaffMemberSearchCriteriaTo}.
   * @return the {@link List} of matching {@link StaffMemberEto}s.
   */
  PaginatedListTo<StaffMemberEto> findStaffMemberEtos(StaffMemberSearchCriteriaTo criteria);

  /**
   * @param staffMemberId the {@link StaffMemberEto#getId() ID} of a {@link StaffMemberEto} to delete.
   */
  void deleteStaffMember(Long staffMemberId);

  /**
   * @param login {@link StaffMemberEto#getName() login} of a {@link StaffMemberEto} to delete.
   */
  void deleteStaffMemberByLogin(String login);

  /**
   * Creates or updates a {@link StaffMemberEto}.
   *
   * @param staffMember The {@link StaffMemberEto} to create or update.
   * @return the saved {@link StaffMemberEto}
   */
  StaffMemberEto saveStaffMember(StaffMemberEto staffMember);

}
