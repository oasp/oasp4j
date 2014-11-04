#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.logic.api.usecase;

import ${package}.general.common.api.Usermanagement;
import ${package}.staffmanagement.logic.api.to.StaffMemberEto;

import java.util.List;

/**
 * Interface for the use-case FindStaffMember.
 *
 * @author mvielsac
 */
public interface UcFindStaffMember extends Usermanagement {

  /**
   * @param id the {@link StaffMemberEto${symbol_pound}getId() ID} of the requested staff member.
   * @return The {@link StaffMemberEto} with the given <code>id</code> or <code>null</code> if no such object exists.
   */
  StaffMemberEto findStaffMember(Long id);

  /**
   * @param login The {@link StaffMemberEto${symbol_pound}getName() login} of the requested staff member.
   * @return The {@link StaffMemberEto} with the given <code>login</code> or <code>null</code> if no such object exists.
   */
  StaffMemberEto findStaffMemberByLogin(String login);

  /**
   * @return {@link List} of all existing {@link StaffMemberEto staff members}.
   */
  List<StaffMemberEto> findAllStaffMembers();
}
