package io.oasp.gastronomy.restaurant.general.common.impl.security;

import java.security.Principal;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import io.oasp.gastronomy.restaurant.general.common.api.UserProfile;
import io.oasp.gastronomy.restaurant.general.common.api.Usermanagement;
import io.oasp.module.security.common.base.accesscontrol.AbstractAccessControlBasedAuthenticationProvider;

/**
 * This..
 */
@Named("BaseUserDetailsService")
public class BaseUserDetailsService<U extends UserDetails, P extends Principal>
    extends AbstractAccessControlBasedAuthenticationProvider implements UserDetailsService {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(BaseUserDetailsService.class);

  private Usermanagement usermanagement;

  /**
   * @param usermanagement the {@link Usermanagement} to set
   */
  @Inject
  public void setUsermanagement(Usermanagement usermanagement) {

    this.usermanagement = usermanagement;
  }

  @Inject
  private InMemoryUserDetailsManager inMemoryUserDetailsManager;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Set<GrantedAuthority> authorities = getAuthorities(username);

    UserDetails user = this.inMemoryUserDetailsManager.loadUserByUsername(username);
    // TODO Auto-generated method stub
    return new User(user.getUsername(), user.getPassword(), authorities);
  }

  @Override
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
}
