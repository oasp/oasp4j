package io.oasp.module.security.common.base.accesscontrol;

import io.oasp.module.security.common.api.accesscontrol.PrincipalAccessControlProvider;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * This is an implementation of {@link PrincipalAccessControlProvider} based on {@link Group}. Due to the confusing API of
 * {@link Group} that mixes a {@link Principal} with permissions and permission groups it is not commonly used even
 * though it is available in the Java standard edition.
 *
 * @author hohwille
 */
public class PrincipalGroupProviderGroupImpl implements PrincipalAccessControlProvider<Group> {

  /**
   * The constructor.
   */
  public PrincipalGroupProviderGroupImpl() {

    super();
  }

  @Override
  public Collection<String> getAccessControlIds(Group principal) {

    Set<String> groupSet = new HashSet<>();
    collectGroups(principal, groupSet);
    return groupSet;
  }

  /**
   * Called from {@link #getAccessControlIds(Group)} to recursively collect the groups.
   *
   * @param group is the {@link Group} to traverse.
   * @param groupSet is the {@link Set} where to add the principal names.
   */
  protected void collectGroups(Group group, Set<String> groupSet) {

    Enumeration<? extends Principal> members = group.members();
    while (members.hasMoreElements()) {
      Principal member = members.nextElement();
      String name = member.getName();
      boolean added = groupSet.add(name);
      if (added && (member instanceof Group)) {
        collectGroups((Group) member, groupSet);
      }
    }
  }
}
