package io.oasp.gastronomy.restaurant.general.common.api;

/**
 * Interface to get a user from its login.
 *
 */
public interface Usermanagement {

  /**
   * @param login The login of the requested user.
   * @return The {@link UserProfile} with the given <code>login</code> or {@code null} if no such object exists.
   */
  UserProfile findUserProfileByLogin(String login);

}
