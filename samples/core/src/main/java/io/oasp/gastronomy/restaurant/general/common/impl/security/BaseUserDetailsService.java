package io.oasp.gastronomy.restaurant.general.common.impl.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.oasp.gastronomy.restaurant.general.common.api.UserProfile;
import io.oasp.gastronomy.restaurant.general.common.api.Usermanagement;
import io.oasp.gastronomy.restaurant.general.common.api.security.UserData;
import io.oasp.gastronomy.restaurant.general.service.impl.config.BaseWebSecurityConfig;
import io.oasp.module.security.common.api.accesscontrol.AccessControl;
import io.oasp.module.security.common.api.accesscontrol.AccessControlProvider;
import io.oasp.module.security.common.api.accesscontrol.PrincipalAccessControlProvider;
import io.oasp.module.security.common.base.accesscontrol.AccessControlGrantedAuthority;

/**
 * This class represents a customized implementation of the {@link UserDetailsService} interface.<br/>
 * <br/>
 * It should be used in custom subclasses of {@link WebSecurityConfigurerAdapter} in the following way:
 * <ul>
 * <li>Inject a fully configured instance of {@link BaseUserDetailsService} into the subclass of
 * {@link WebSecurityConfigurerAdapter}</li>
 * <li>Override method {@code configure(HttpSecurity)} of {@link WebSecurityConfigurerAdapter}</li>
 * <li>Add the {@link BaseUserDetailsService} to the {@code HttpSecurity} object.</li>
 * </ul>
 * The following code snippet shows the above steps:<br/>
 *
 * <pre>
 * &#64;Configuration
 * &#64;EnableWebSecurity
 * public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
 *   // ...
 *   &#64;Inject
 *   private UserDetailsService userDetailsService;
 *   // ...
 *   &#64;Override
 *   public void configure(HttpSecurity http) throws Exception {
 *      http.userDetailsService(this.userDetailsService)... //add matchers and other stuff
 *   }
 * }
 * </pre>
 *
 * <br/>
 * For another example, have a look at {@link BaseWebSecurityConfig}.
 */
@Named
public class BaseUserDetailsService implements UserDetailsService {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(BaseUserDetailsService.class);

  private Usermanagement usermanagement;

  private AuthenticationManagerBuilder amBuilder;

  private AccessControlProvider accessControlProvider;

  private PrincipalAccessControlProvider<UserProfile> principalAccessControlProvider;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserProfile principal = retrievePrincipal(username);
    Set<GrantedAuthority> authorities = getAuthorities(principal);
    UserDetails user;
    try {
      // amBuilder uses the InMemoryUserDetailsManager, because it is configured in BaseWebSecurityConfig
      user = getAmBuilder().getDefaultUserDetailsService().loadUserByUsername(username);
      UserData userData = new UserData(user.getUsername(), user.getPassword(), authorities);
      userData.setUserProfile(principal);
      return userData;
    } catch (Exception e) {
      e.printStackTrace();
      UsernameNotFoundException exception = new UsernameNotFoundException("Authentication failed.", e);
      LOG.warn("Failed to get user {}.", username, exception);
      throw exception;
    }
  }

  /**
   * Returns the {@link GrantedAuthority}s of the user associated with the provided {@link UserProfile}.
   *
   * @param principal the {@link UserProfile} of the user
   * @return the associated {@link GrantedAuthority}s
   * @throws AuthenticationException if no principal is retrievable for the given {@code username}
   */
  protected Set<GrantedAuthority> getAuthorities(UserProfile principal) throws AuthenticationException {

    if (principal == null) {
      LOG.warn("Principal must not be null.");
      throw new IllegalArgumentException();
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

  /**
   * @param username The {@code username} for which the {@code UserProfile} will be queried.
   * @return An instance of type {@link UserProfile} obtained by querying the {@code username}.
   */
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
   * @param usermanagement new value of {@link #getUsermanagement}.
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
   * @param amBuilder new value of {@link #getAmBuilder}.
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
   * @param accessControlProvider new value of {@link #getAccessControlProvider}.
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
   * @param principalAccessControlProvider new value of {@link #getPrincipalAccessControlProvider}.
   */
  @Inject
  public void setPrincipalAccessControlProvider(
      PrincipalAccessControlProvider<UserProfile> principalAccessControlProvider) {

    this.principalAccessControlProvider = principalAccessControlProvider;
  }
}
