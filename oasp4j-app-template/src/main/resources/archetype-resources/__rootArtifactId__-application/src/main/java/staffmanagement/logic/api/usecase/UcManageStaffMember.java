#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.logic.api.usecase;

import ${package}.staffmanagement.logic.api.to.StaffMemberEto;

/**
 * Interface of UcManageStaffMember to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageStaffMember {

  /**
   * @param staffMemberId the {@link StaffMemberEto${symbol_pound}getId() ID} of a {@link StaffMemberEto} to delete.
   */
  void deleteStaffMember(Long staffMemberId);

  /**
   * @param login {@link StaffMemberEto${symbol_pound}getName() login} of a {@link StaffMemberEto} to delete.
   */
  void deleteStaffMemberByLogin(String login);

  /**
   * @param staffMember The {@link StaffMemberEto} to update.
   */
  void updateStaffMember(StaffMemberEto staffMember);
}
