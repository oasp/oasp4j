package org.oasp.module.security.common.authorization.api;

import java.util.List;

/**
 * Inheriting classes are responsible to map user tokens to their respective roles
 * 
 * @author mbrunnli
 * @version $Id:$
 */
public interface RolesProvider {

  /**
   * Checks whether a given user has one of the given roles
   * 
   * @param userToken token for identifying a user
   * @param roles list of roles to check
   * @return <code>true</code> if the user has one of the given roles<br>
   *         <code>false</code>, otherwise
   */
  public boolean hasOneOf(Object userToken, List<String> roles);

}
