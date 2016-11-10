package io.oasp.gastronomy.restaurant.general.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;

import java.security.Principal;

/**
 * This is the interface for the profile of a user interacting with this application. Currently this can only be a
 * {@link io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity} however in the future a
 * customer may login and make a reservation, etc.<br/>
 * TODO: Also an external system may access the application via some service. Then there would be no user profile or it
 * would be empty...
 *
 */
public interface UserProfile extends Principal {
  /**
   * @return the technical ID of the user for calling REST services.
   */
  Long getId();

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
