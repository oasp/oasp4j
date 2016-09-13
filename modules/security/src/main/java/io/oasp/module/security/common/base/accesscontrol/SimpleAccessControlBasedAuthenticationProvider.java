package io.oasp.module.security.common.base.accesscontrol;

import java.security.Principal;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * This is a simple implementation of {@link AbstractAccessControlBasedAuthenticationProvider}.
 *
 */
public class SimpleAccessControlBasedAuthenticationProvider extends
    AbstractAccessControlBasedAuthenticationProvider<User, Principal> {

  /**
   * The constructor.
   */
  public SimpleAccessControlBasedAuthenticationProvider() {

    super();
  }

  @Override
  protected User createUser(String username, String password, Principal principal, Set<GrantedAuthority> authorities) {

    User user = new User(username, password, authorities);
    return user;
  }

  @Override
  protected Principal retrievePrincipal(String username, UsernamePasswordAuthenticationToken authentication) {

    return authentication;
  }

}
