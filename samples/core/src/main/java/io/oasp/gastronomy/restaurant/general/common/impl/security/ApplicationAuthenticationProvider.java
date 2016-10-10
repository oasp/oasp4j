package io.oasp.gastronomy.restaurant.general.common.impl.security;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.oasp.gastronomy.restaurant.general.common.api.UserProfile;
import io.oasp.gastronomy.restaurant.general.common.api.Usermanagement;
import io.oasp.gastronomy.restaurant.general.common.api.security.UserData;
import io.oasp.module.security.common.base.accesscontrol.AbstractAccessControlBasedAuthenticationProvider;

/**
 * This class is responsible for the security aspects of authentication as well as providing user profile
 * data and the access-controls for authoriziation.
 * @deprecated As of bug-fix release 2.1.2 the authentication mechanism changes. It is now based upon custom
 *             implementations of {@link UserDetailsService} in combination with {@link WebSecurityConfigurerAdapter}.
 *             For further information have a look at the sample application. <br/>
 *             <br/>
 *
 */
@Deprecated
@Named("ApplicationAuthenticationProvider")
public class ApplicationAuthenticationProvider
    extends AbstractAccessControlBasedAuthenticationProvider<UserData, UserProfile> {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationAuthenticationProvider.class);

  private Usermanagement usermanagement;

  /**
   * The constructor.
   */
  public ApplicationAuthenticationProvider() {

    super();
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    authentication.setDetails(userDetails);
  }

  /**
   * @param usermanagement the {@link Usermanagement} to set
   */
  @Inject
  public void setUsermanagement(Usermanagement usermanagement) {

    this.usermanagement = usermanagement;
  }

  @Override
  protected UserProfile retrievePrincipal(String username, UsernamePasswordAuthenticationToken authentication) {

    try {
      return this.usermanagement.findUserProfileByLogin(username);
    } catch (RuntimeException e) {
      e.printStackTrace();
      UsernameNotFoundException exception = new UsernameNotFoundException("Authentication failed.", e);
      LOG.warn("Failed to get user {}.", username, exception);
      throw exception;
    }
  }

  @Override
  protected UserData createUser(String username, String password, UserProfile principal,
      Set<GrantedAuthority> authorities) {

    UserData user = new UserData(username, password, authorities);
    user.setUserProfile(principal);
    return user;
  }

  /*
   * Leave empty on purpose. Not used in this version.
   */
  @Override
  protected UserProfile retrievePrincipal(String username) {

    return null;
  }

}
