package org.oasp.module.security.common.authorization.api;

import java.util.List;

/**
 * Inheriting classes are responsible to map user tokens to their respective roles.
 *
 * @author mbrunnli
 */
public interface RolesProvider {

  /**
   * Checks whether a given user has one of the given roles.
   *
   * @param userToken token for identifying a user
   * @param roles list of roles to be checked
   * @return <code>true</code> if the user has one of the given roles<br>
   *         <code>false</code>, otherwise
   */
  public boolean hasOneOf(Object userToken, List<String> roles);

  /**
   * checks whether a given user has the given role.
   *
   * @param userToken for identifying a user
   * @param role to be checked
   * @return <code>true</code> if the user has the given role<br>
   *         <code>false</code>, otherwise
   */
  public boolean has(Object userToken, String role);

}
