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
public class SimpleAccessControlBasedAuthenticationProvider
    extends AbstractAccessControlBasedAuthenticationProvider<User, Principal> {

  /**
   * The constructor.
   */
  public SimpleAccessControlBasedAuthenticationProvider() {

    super();
  }

  /**
   * Creates a fully populated {@link User} that represent the user with the given <code>username</code> without any
   * checks.
   *
   * @param username is the login of the user to create.
   * @param password the password of the user.
   * @param principal is the internal {@link Principal} that has been provided by
   *        {@link #retrievePrincipal(String, UsernamePasswordAuthenticationToken)}.
   * @param authorities are the {@link GrantedAuthority granted authorities} or in other words the permissions of the
   *        user.
   * @return the fully populated {@link User}.
   */
  protected User createUser(String username, String password, Principal principal, Set<GrantedAuthority> authorities) {

    User user = new User(username, password, authorities);
    return user;
  }

  /**
   * Simple method providing upwards compatibility
   *
   * @param username is the login of the user to create.
   * @param authentication the {@link UsernamePasswordAuthenticationToken}.
   * @return the given {@link UsernamePasswordAuthenticationToken}.
   */
  protected Principal retrievePrincipal(String username, UsernamePasswordAuthenticationToken authentication) {

    return authentication;
  }

  @Override
  protected Principal retrievePrincipal(String username) {

    // method not used
    return null;
  }

}
