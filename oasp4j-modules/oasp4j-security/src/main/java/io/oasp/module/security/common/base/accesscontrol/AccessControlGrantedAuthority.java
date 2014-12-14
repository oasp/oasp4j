package io.oasp.module.security.common.base.accesscontrol;

import io.oasp.module.security.common.api.accesscontrol.AccessControl;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

/**
 * Implementation of {@link GrantedAuthority} for a {@link AccessControl}.
 *
 * @author hohwille
 */
public class AccessControlGrantedAuthority implements GrantedAuthority {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private final AccessControl accessControl;

  /**
   * The constructor.
   *
   * @param accessControl the {@link #getAccessControl() access control}.
   */
  public AccessControlGrantedAuthority(AccessControl accessControl) {

    super();
    Objects.requireNonNull(accessControl, AccessControl.class.getSimpleName());
    this.accessControl = accessControl;
  }

  /**
   * @return the contained {@link AccessControl}.
   */
  public AccessControl getAccessControl() {

    return this.accessControl;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAuthority() {

    return this.accessControl.getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getAuthority();
  }

}
