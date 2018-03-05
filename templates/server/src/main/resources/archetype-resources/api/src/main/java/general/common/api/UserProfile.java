package ${package}.general.common.api;

import java.security.Principal;

/**
 * This is the interface for the profile of a user interacting with this application.
 */
public interface UserProfile {

  /**
   * @return the login of the user.
   */
  String getLogin();

}
