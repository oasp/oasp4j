package io.oasp.module.security.common.base.accesscontrol;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.oasp.module.security.common.api.accesscontrol.AccessControl;
import io.oasp.module.security.common.api.accesscontrol.AccessControlProvider;
import io.oasp.module.security.common.api.accesscontrol.PrincipalAccessControlProvider;

/**
 * This type is used to provide access authorities based on the access control xml schema.
 *
 * @param <U> is the generic type of the {@link UserDetails} implementation used to bridge with spring-security.
 * @param
 *        <P>
 *        is the generic type of the {@link Principal} for internal user representation to bridge with
 *        {@link PrincipalAccessControlProvider}.
 */
public abstract class AbstractAccessControlBasedAuthenticationProvider<U extends UserDetails, P extends Principal> {

  /** The {@link Logger} instance. */
  private static final Logger LOG = LoggerFactory.getLogger(AbstractAccessControlBasedAuthenticationProvider.class);

  private PrincipalAccessControlProvider<P> principalAccessControlProvider;

  private AccessControlProvider accessControlProvider;

  /**
   * The constructor.
   */
  public AbstractAccessControlBasedAuthenticationProvider() {

  }

  /**
   * @param principalAccessControlProvider the {@link PrincipalAccessControlProvider} to {@link Inject}.
   */
  @Inject
  public void setPrincipalAccessControlProvider(PrincipalAccessControlProvider<P> principalAccessControlProvider) {

    this.principalAccessControlProvider = principalAccessControlProvider;
  }

  /**
   * @param accessControlProvider the {@link AccessControlProvider} to {@link Inject}.
   */
  @Inject
  public void setAccessControlProvider(AccessControlProvider accessControlProvider) {

    this.accessControlProvider = accessControlProvider;
  }

  /**
   * @param username the username to get authorities for
   * @return the populated list of granted authorities
   * @throws AuthenticationException when the user does not exist
   */
  protected Set<GrantedAuthority> getAuthorities(String username) throws AuthenticationException {

    P principal = retrievePrincipal(username);
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
        // authorities.add(new SimpleGrantedAuthority(id));
      }
    }
    for (AccessControl accessControl : accessControlSet) {
      authorities.add(new AccessControlGrantedAuthority(accessControl));
    }
    return authorities;
  }

  /**
   * @param username the username to retrieve
   * @return a principal, mostly a {@link UserProfile}
   */
  protected abstract P retrievePrincipal(String username);
}
