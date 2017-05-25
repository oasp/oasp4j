package io.oasp.module.security.common.api.accesscontrol;

import java.util.Set;

/**
 * This is the interface for a provider of {@link AbstractAccessControl}s. It allows to
 * {@link #collectAccessControlIds(String, Set) collect} all {@link AbstractAccessControl}s for an ID of a {@link AbstractAccessControl}
 * (typically a {@link AccessControlGroup} or role). This is used to expand the groups provided by the access-manager
 * (authentication and identity-management) to the full set of {@link AccessControlPermission permissions} of the
 * {@link java.security.Principal user}.<br/>
 * The actual authorization can then check individual permissions of the user by simply checking for
 * {@link Set#contains(Object) contains} in the collected {@link Set}, what is very fast as security checks happen
 * frequently.
 *
 * @see PrincipalAccessControlProvider
 *
 */
public interface AccessControlProvider {

  /**
   * @param id is the {@link AbstractAccessControl#getId() ID} of the requested {@link AbstractAccessControl}.
   * @return the requested {@link AbstractAccessControl} or {@code null} if not found.
   */
  AbstractAccessControl getAccessControl(String id);

  /**
   * This method collects the {@link AbstractAccessControl#getId() IDs} of all {@link AccessControlPermission}s (or more
   * precisely of all {@link AbstractAccessControl}s) contained in the {@link AbstractAccessControl} {@link AbstractAccessControl#getId()
   * identified} by the given <code>groupId</code>.
   *
   * @see #collectAccessControls(String, Set)
   *
   * @param id is the {@link AbstractAccessControl#getId() ID} of the {@link AbstractAccessControl} (typically an
   *        {@link AccessControlGroup}) to collect.
   * @param permissions is the {@link Set} where to {@link Set#add(Object) add} the collected
   *        {@link AbstractAccessControl#getId() IDs}. This will include the given <code>groupId</code>.
   * @return {@code true} if the given <code>groupId</code> has been found, {@code false} otherwise.
   */
  boolean collectAccessControlIds(String id, Set<String> permissions);

  /**
   * This method collects the {@link AbstractAccessControl}s contained in the {@link AbstractAccessControl}
   * {@link AbstractAccessControl#getId() identified} by the given <code>groupId</code>.
   *
   * @param id is the {@link AbstractAccessControl#getId() ID} of the {@link AbstractAccessControl} (typically an
   *        {@link AccessControlGroup}) to collect.
   * @param permissions is the {@link Set} where to {@link Set#add(Object) add} the collected {@link AbstractAccessControl}s.
   *        This will include the {@link AbstractAccessControl} {@link AbstractAccessControl#getId() identified} by the given
   *        <code>groupId</code>.
   * @return {@code true} if the given <code>groupId</code> has been found, {@code false} otherwise.
   */
  boolean collectAccessControls(String id, Set<AbstractAccessControl> permissions);

}
