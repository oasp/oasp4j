#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.common.api;

import ${package}.general.common.api.ApplicationEntity;
import ${package}.general.common.api.UserProfile;
import ${package}.general.common.api.datatype.Role;

/**
 * This is the interface for a {@link StaffMember} that is an employee of the restaurant.
 *
 * @author hohwille
 */
public interface StaffMember extends ApplicationEntity, UserProfile {

  /**
   * @param name is the new {@link ${symbol_pound}getName() login}.
   */
  void setName(String name);

  /**
   * @param firstName is the new {@link ${symbol_pound}getFirstName() first name}.
   */
  void setFirstName(String firstName);

  /**
   * @param lastName is the new {@link ${symbol_pound}getLastName() last name}.
   */
  void setLastName(String lastName);

  /**
   * @param role the new {@link ${symbol_pound}getRole() role}.
   */
  void setRole(Role role);

}
