package io.oasp.module.security.common.base.accesscontrol;

import io.oasp.module.security.common.api.accesscontrol.AbstractAccessControl;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

/**
 * Implementation of {@link GrantedAuthority} for a {@link AbstractAccessControl}.
 *
 */
public class AccessControlGrantedAuthority implements GrantedAuthority {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private final AbstractAccessControl accessControl;

  /**
   * The constructor.
   *
   * @param accessControl the {@link #getAccessControl() access control}.
   */
  public AccessControlGrantedAuthority(AbstractAccessControl accessControl) {

    super();
    Objects.requireNonNull(accessControl, AbstractAccessControl.class.getSimpleName());
    this.accessControl = accessControl;
  }

  /**
   * @return the contained {@link AbstractAccessControl}.
   */
  public AbstractAccessControl getAccessControl() {

    return this.accessControl;
  }

  @Override
  public String getAuthority() {

    return this.accessControl.getId();
  }

  @Override
  public String toString() {

    return getAuthority();
  }

}
