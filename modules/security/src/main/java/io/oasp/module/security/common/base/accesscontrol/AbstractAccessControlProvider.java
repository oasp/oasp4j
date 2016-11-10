package io.oasp.module.security.common.base.accesscontrol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.collection.base.NodeCycle;
import net.sf.mmm.util.collection.base.NodeCycleException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.oasp.module.security.common.api.accesscontrol.AccessControl;
import io.oasp.module.security.common.api.accesscontrol.AccessControlGroup;
import io.oasp.module.security.common.api.accesscontrol.AccessControlPermission;
import io.oasp.module.security.common.api.accesscontrol.AccessControlProvider;
import io.oasp.module.security.common.api.accesscontrol.AccessControlSchema;

/**
 * This is the abstract base implementation of {@link AccessControlProvider}.<br/>
 * ATTENTION:<br/>
 * You need to call {@link #initialize(AccessControlSchema)} from the derived implementation.
 *
 */
public abstract class AbstractAccessControlProvider implements AccessControlProvider {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(AbstractAccessControlProvider.class);

  /** @see #getAccessControl(String) */
  private final Map<String, AccessControl> id2nodeMap;

  /**
   * The constructor.
   */
  public AbstractAccessControlProvider() {

    super();
    this.id2nodeMap = new HashMap<>();
  }

  /**
   * Performs the required initialization of this class.
   *
   * @param config is the {@link AccessControlSchema}.
   */
  protected void initialize(AccessControlSchema config) {

    LOG.debug("Initializing.");
    List<AccessControlGroup> groups = config.getGroups();
    if (groups.size() == 0) {
      throw new IllegalStateException("AccessControlSchema is empty - please configure at least one group!");
    }
    Set<AccessControlGroup> toplevelGroups = new HashSet<>(groups);
    for (AccessControlGroup group : groups) {
      collectAccessControls(group, toplevelGroups);
      NodeCycle<AccessControlGroup> nodeCycle = new NodeCycle<>(group);
      checkForCyclicDependencies(group, nodeCycle);
    }
  }

  /**
   * Checks that the given {@link AccessControlGroup} has no cyclic {@link AccessControlGroup#getInherits() inheritance
   * graph}.
   *
   * @param group is the {@link AccessControlGroup} to check.
   * @param nodeCycle the {@link NodeCycle} used to detect cycles.
   */
  protected void checkForCyclicDependencies(AccessControlGroup group, NodeCycle<AccessControlGroup> nodeCycle) {

    for (AccessControlGroup inheritedGroup : group.getInherits()) {
      List<AccessControlGroup> inverseCycle = nodeCycle.getInverseCycle();
      if (inverseCycle.contains(inheritedGroup)) {
        throw new NodeCycleException(nodeCycle);
      }
      inverseCycle.add(inheritedGroup);
      checkForCyclicDependencies(inheritedGroup, nodeCycle);
      AccessControlGroup removed = inverseCycle.remove(inverseCycle.size() - 1);
      assert (removed == inheritedGroup);
    }
  }

  /**
   * Called from {@link #initialize(AccessControlSchema)} to collect all {@link AccessControl}s recursively.
   *
   * @param group the {@link AccessControlGroup} to traverse.
   * @param toplevelGroups is the {@link Set} of all {@link AccessControlGroup}s from
   *        {@link AccessControlSchema#getGroups()}.
   */
  protected void collectAccessControls(AccessControlGroup group, Set<AccessControlGroup> toplevelGroups) {

    if (!toplevelGroups.contains(group)) {
      throw new IllegalStateException("Invalid group not declared as top-level group in schema: " + group);
    }
    AccessControl old = this.id2nodeMap.put(group.getId(), group);
    if (old != null) {
      LOG.debug("Already visited access control group {}", group);
      if (old != group) {
        throw new IllegalStateException(
            "Invalid security configuration: duplicate groups with id " + group.getId() + "!");
      }
      // group has already been visited, stop recursion...
      return;
    } else {
      LOG.debug("Registered access control group {}", group);
    }
    for (AccessControlPermission permission : group.getPermissions()) {
      old = this.id2nodeMap.put(permission.getId(), permission);
      if (old != null) {
        // throw new IllegalStateException("Invalid security configuration: duplicate permission with id "
        // + permission.getId() + "!");
        LOG.warn("Security configuration contains duplicate permission with id {}.", permission.getId());
      } else {
        LOG.debug("Registered access control permission {}", permission);
      }
    }
    for (AccessControlGroup inheritedGroup : group.getInherits()) {
      collectAccessControls(inheritedGroup, toplevelGroups);
    }
  }

  @Override
  public AccessControl getAccessControl(String nodeId) {

    return this.id2nodeMap.get(nodeId);
  }

  @Override
  public boolean collectAccessControlIds(String groupId, Set<String> permissions) {

    AccessControl node = getAccessControl(groupId);
    if (node instanceof AccessControlGroup) {
      collectPermissionIds((AccessControlGroup) node, permissions);
    } else {
      // node does not exist or is a flat AccessControlPermission
      permissions.add(groupId);
    }
    return (node != null);
  }

  /**
   * Recursive implementation of {@link #collectAccessControlIds(String, Set)} for {@link AccessControlGroup}s.
   *
   * @param group is the {@link AccessControlGroup} to traverse.
   * @param permissions is the {@link Set} used to collect.
   */
  public void collectPermissionIds(AccessControlGroup group, Set<String> permissions) {

    boolean added = permissions.add(group.getId());
    if (!added) {
      // we have already visited this node, stop recursion...
      return;
    }
    for (AccessControlPermission permission : group.getPermissions()) {
      permissions.add(permission.getId());
    }
    for (AccessControlGroup inheritedGroup : group.getInherits()) {
      collectPermissionIds(inheritedGroup, permissions);
    }
  }

  @Override
  public boolean collectAccessControls(String groupId, Set<AccessControl> permissions) {

    AccessControl node = getAccessControl(groupId);
    if (node == null) {
      return false;
    }
    if (node instanceof AccessControlGroup) {
      collectPermissionNodes((AccessControlGroup) node, permissions);
    } else {
      // node is a flat AccessControlPermission
      permissions.add(node);
    }
    return true;
  }

  /**
   * Recursive implementation of {@link #collectAccessControls(String, Set)} for {@link AccessControlGroup}s.
   *
   * @param group is the {@link AccessControlGroup} to traverse.
   * @param permissions is the {@link Set} used to collect.
   */
  public void collectPermissionNodes(AccessControlGroup group, Set<AccessControl> permissions) {

    boolean added = permissions.add(group);
    if (!added) {
      // we have already visited this node, stop recursion...
      return;
    }
    for (AccessControlPermission permission : group.getPermissions()) {
      permissions.add(permission);
    }
    for (AccessControlGroup inheritedGroup : group.getInherits()) {
      collectPermissionNodes(inheritedGroup, permissions);
    }
  }

}
