#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api;

import ${package}.general.common.api.datatype.Role;

import java.security.Principal;

/**
 * This is the interface for the profile of a user interacting with this application. Currently this can only be a
 * {@link ${package}.staffmanagement.persistence.api.StaffMemberEntity} however in the future a
 * customer may login and make a reservation, etc.<br/>
 * TODO: Also an external system may access the application via some service. Then there would be no user profile or it
 * would be empty...
 *
 * @author agreul
 */
public interface UserProfile extends Principal {

  /**
   * @return the unique login of the user for authentication and identification.
   */
  String getName();

  /**
   * @return the first name of the users real name.
   */
  String getFirstName();

  /**
   * @return the last name of the users real name.
   */
  String getLastName();

  /**
   * @return {@link Role} of this {@link UserProfile}.
   */
  Role getRole();
}
