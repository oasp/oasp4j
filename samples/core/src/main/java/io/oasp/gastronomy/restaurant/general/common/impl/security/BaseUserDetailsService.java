package io.oasp.gastronomy.restaurant.general.common.impl.security;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.oasp.gastronomy.restaurant.general.common.api.UserProfile;
import io.oasp.gastronomy.restaurant.general.common.api.Usermanagement;
import io.oasp.module.security.common.api.accesscontrol.AccessControl;
import io.oasp.module.security.common.api.accesscontrol.AccessControlProvider;
import io.oasp.module.security.common.api.accesscontrol.PrincipalAccessControlProvider;
import io.oasp.module.security.common.base.accesscontrol.AccessControlGrantedAuthority;

/**
 *
 * @author jmolinar,sroeger
 */
@Named
public class BaseUserDetailsService<U extends UserDetails, P extends Principal> implements UserDetailsService {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(BaseUserDetailsService.class);

  private Usermanagement usermanagement;

  private AuthenticationManagerBuilder amBuilder;

  private AccessControlProvider accessControlProvider;

  private PrincipalAccessControlProvider<UserProfile> principalAccessControlProvider;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Set<GrantedAuthority> authorities = getAuthorities(username);
    UserDetails user;
    try {
      user = this.amBuilder.getDefaultUserDetailsService().loadUserByUsername(username);
      return new User(user.getUsername(), user.getPassword(), authorities);
    } catch (Exception e) {
      e.printStackTrace();
      UsernameNotFoundException exception = new UsernameNotFoundException("Authentication failed.", e);
      LOG.warn("Failed to get user {}.", username, exception);
      throw exception;
    }
  }

  /**
   * Returns the {@link GrantedAuthority}s of the provided user identified by the {@code username}.
   *
   * @param username the name of the user
   * @return the associated {@link GrantedAuthority}s
   * @throws AuthenticationException if not principal is retrievable for the given {@code username}
   */
  protected Set<GrantedAuthority> getAuthorities(String username) throws AuthenticationException {

    UserProfile principal = retrievePrincipal(username);
    if (principal == null) {
      LOG.warn("Failed to retrieve user for login {}.", username);
      throw new UsernameNotFoundException(username);
    }

    // determine granted authorities for spring-security...
    Set<GrantedAuthority> authorities = new HashSet<>();
    Collection<String> accessControlIds = this.principalAccessControlProvider.getAccessControlIds(principal);
    Set<AccessControl> accessControlSet = new HashSet<>();
    for (String id : accessControlIds) {
      boolean success = this.accessControlProvider.collectAccessControls(id, accessControlSet);
      if (!success) {
        LOG.warn("Undefined access control {}.", id);
      }
    }
    for (AccessControl accessControl : accessControlSet) {
      authorities.add(new AccessControlGrantedAuthority(accessControl));
    }
    return authorities;
  }

  protected UserProfile retrievePrincipal(String username) {

    try {
      return this.usermanagement.findUserProfileByLogin(username);
    } catch (RuntimeException e) {
      e.printStackTrace();
      UsernameNotFoundException exception = new UsernameNotFoundException("Authentication failed.", e);
      LOG.warn("Failed to get user {}.", username, exception);
      throw exception;
    }
  }

  /**
   * @return usermanagement
   */
  public Usermanagement getUsermanagement() {

    return this.usermanagement;
  }

  /**
   * @param usermanagement new value of {@link #getusermanagement}.
   */
  @Inject
  public void setUsermanagement(Usermanagement usermanagement) {

    this.usermanagement = usermanagement;
  }

  /**
   * @return amBuilder
   */
  public AuthenticationManagerBuilder getAmBuilder() {

    return this.amBuilder;
  }

  /**
   * @param amBuilder new value of {@link #getamBuilder}.
   */
  @Inject
  public void setAmBuilder(AuthenticationManagerBuilder amBuilder) {

    this.amBuilder = amBuilder;
  }

  /**
   * @return accessControlProvider
   */
  public AccessControlProvider getAccessControlProvider() {

    return this.accessControlProvider;
  }

  /**
   * @param accessControlProvider new value of {@link #getaccessControlProvider}.
   */
  @Inject
  public void setAccessControlProvider(AccessControlProvider accessControlProvider) {

    this.accessControlProvider = accessControlProvider;
  }

  /**
   * @return principalAccessControlProvider
   */
  public PrincipalAccessControlProvider<UserProfile> getPrincipalAccessControlProvider() {

    return this.principalAccessControlProvider;
  }

  /**
   * @param principalAccessControlProvider new value of {@link #getprincipalAccessControlProvider}.
   */
  @Inject
  public void setPrincipalAccessControlProvider(
      PrincipalAccessControlProvider<UserProfile> principalAccessControlProvider) {

    this.principalAccessControlProvider = principalAccessControlProvider;
  }
}