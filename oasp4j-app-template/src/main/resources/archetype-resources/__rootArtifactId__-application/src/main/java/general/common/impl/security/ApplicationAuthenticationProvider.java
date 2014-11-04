#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.impl.security;

import ${package}.general.common.api.UserProfile;
import ${package}.general.common.api.Usermanagement;
import ${package}.general.common.api.security.UserData;
import io.oasp.module.security.common.base.accesscontrol.AbstractAccessControlBasedAuthenticationProvider;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This class is responsible for the security aspects of authentication as well as providing user profile data and the
 * access-controls for authoriziation.
 *
 * @author mbrunnli
 * @author hohwille
 * @author agreul
 */
@Named("ApplicationAuthenticationProvider")
public class ApplicationAuthenticationProvider extends
    AbstractAccessControlBasedAuthenticationProvider<UserData, UserProfile> {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationAuthenticationProvider.class);

  private Usermanagement userManagement;

  /**
   * The constructor.
   */
  public ApplicationAuthenticationProvider() {

    super();
  }

  /**
   * @param userManagement the {@link Usermanagement} to set
   */
  @Inject
  public void setStaffManagement(Usermanagement userManagement) {

    this.userManagement = userManagement;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UserProfile retrievePrincipal(String username, UsernamePasswordAuthenticationToken authentication) {

    try {
      return this.userManagement.findStaffMemberByLogin(username);
    } catch (RuntimeException e) {
      UsernameNotFoundException exception = new UsernameNotFoundException("Authentication failed.", e);
      LOG.warn("Failed to get user {}.", username, exception);
      throw exception;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UserData createUser(String username, String password, UserProfile principal,
      Set<GrantedAuthority> authorities) {

    UserData user = new UserData(username, password, authorities);
    user.setUserProfile(principal);
    return user;
  }

}
