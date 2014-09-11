package io.oasp.module.security.common.authorization.api;

import java.security.Principal;
import java.util.List;

/**
 * Inheriting classes are responsible to map user tokens to their respective roles.
 *
 * @param <P> is the generic type of the object representing the {@link Principal} (user that logged in).
 * @author mbrunnli
 */
public interface RolesProvider<P extends Principal> {

  /**
   * Checks whether a given user has one of the given roles.
   *
   * @param userToken token for identifying a user
   * @param roles list of roles to be checked
   * @return <code>true</code> if the user has one of the given roles<br>
   *         <code>false</code>, otherwise
   */
  boolean hasOneOf(P userToken, List<String> roles);

  /**
   * checks whether a given user has the given role.
   *
   * @param userToken for identifying a user
   * @param role to be checked
   * @return <code>true</code> if the user has the given role<br>
   *         <code>false</code>, otherwise
   */
  boolean has(P userToken, String role);

}
