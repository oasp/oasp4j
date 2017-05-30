package io.oasp.module.security.common.api.accesscontrol;

/**
 * This is the interface for a node of the {@link AccessControlSchema} that represents a tree of
 * {@link AccessControlGroup}s and {@link AccessControlPermission}s. If a {@link java.security.Principal} "has" a
 * {@link AccessControl} he also "has" all {@link AccessControl}s with according permissions in the spanned sub-tree.
 *
 */
public interface AccessControl {

  /**
   * @return the unique identifier of {@link AccessControl}. Has to be unique for all {@link AccessControl} in a
   *         {@link AccessControlSchema}.
   */
  public String getId();
}
