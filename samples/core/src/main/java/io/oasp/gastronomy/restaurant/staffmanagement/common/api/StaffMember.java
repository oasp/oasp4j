package io.oasp.gastronomy.restaurant.staffmanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.gastronomy.restaurant.general.common.api.UserProfile;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;

/**
 * This is the interface for a {@link StaffMember} that is an employee of the restaurant.
 *
 */
public interface StaffMember extends ApplicationEntity, UserProfile {

  /**
   * @param name is the new {@link #getName() login}.
   */
  void setName(String name);

  /**
   * @param firstName is the new {@link #getFirstName() first name}.
   */
  void setFirstName(String firstName);

  /**
   * @param lastName is the new {@link #getLastName() last name}.
   */
  void setLastName(String lastName);

  /**
   * @param role the new {@link #getRole() role}.
   */
  void setRole(Role role);

}
