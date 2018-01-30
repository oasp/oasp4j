package io.oasp.module.security.common.impl.accesscontrol;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.junit.Test;

import io.oasp.module.security.common.api.accesscontrol.AccessControl;
import io.oasp.module.security.common.api.accesscontrol.AccessControlGroup;
import io.oasp.module.security.common.base.accesscontrol.AccessControlConfig;
import io.oasp.module.test.common.base.ModuleTest;

/**
 * Test of {@link AccessControlConfig}.
 */
public class AccessControlConfigTest extends ModuleTest {

  @Test
  // @RolesAllowed only used to ensure that the constant can be referenced here
  @RolesAllowed(AccessControlConfigSimple.PERMISSION_FIND_TABLE)
  public void testSimple() {

    // given + when
    AccessControlConfigSimple config = new AccessControlConfigSimple();
    AccessControlGroup chief = (AccessControlGroup) config.getAccessControl(AccessControlConfigSimple.GROUP_CHIEF);

    // then
    assertThat(chief).isNotNull();
    assertThat(chief.getId()).isEqualTo(AccessControlConfigSimple.GROUP_CHIEF);
    assertThat(flatten(chief.getPermissions())).containsExactly(AccessControlConfigSimple.PERMISSION_SAVE_OFFER,
        AccessControlConfigSimple.PERMISSION_SAVE_PRODUCT, AccessControlConfigSimple.PERMISSION_SAVE_STAFF_MEMBER,
        AccessControlConfigSimple.PERMISSION_DELETE_OFFER, AccessControlConfigSimple.PERMISSION_DELETE_PRODUCT,
        AccessControlConfigSimple.PERMISSION_DELETE_STAFF_MEMBER,
        AccessControlConfigSimple.PERMISSION_DELETE_ORDER_POSITION, AccessControlConfigSimple.PERMISSION_DELETE_TABLE);
    AccessControlGroup waiter = getSingleInherit(chief);
    assertThat(waiter).isNotNull();
    assertThat(waiter.getId()).isEqualTo(AccessControlConfigSimple.GROUP_WAITER);
    assertThat(flatten(waiter.getPermissions())).containsExactly(AccessControlConfigSimple.PERMISSION_SAVE_TABLE);
    AccessControlGroup barkeeper = getSingleInherit(waiter);
    assertThat(barkeeper).isNotNull();
    assertThat(barkeeper.getId()).isEqualTo(AccessControlConfigSimple.GROUP_BARKEEPER);
    assertThat(flatten(barkeeper.getPermissions())).containsExactly(AccessControlConfigSimple.PERMISSION_FIND_BILL,
        AccessControlConfigSimple.PERMISSION_SAVE_BILL, AccessControlConfigSimple.PERMISSION_DELETE_BILL,
        AccessControlConfigSimple.PERMISSION_DELETE_ORDER);
    AccessControlGroup cook = getSingleInherit(barkeeper);
    assertThat(cook).isNotNull();
    assertThat(cook.getId()).isEqualTo(AccessControlConfigSimple.GROUP_COOK);
    assertThat(flatten(cook.getPermissions())).containsExactly(AccessControlConfigSimple.PERMISSION_FIND_ORDER,
        AccessControlConfigSimple.PERMISSION_SAVE_ORDER, AccessControlConfigSimple.PERMISSION_FIND_ORDER_POSITION,
        AccessControlConfigSimple.PERMISSION_SAVE_ORDER_POSITION);
    AccessControlGroup readMasterData = getSingleInherit(cook);
    assertThat(readMasterData).isNotNull();
    assertThat(readMasterData.getId()).isEqualTo(AccessControlConfigSimple.GROUP_READ_MASTER_DATA);
    assertThat(flatten(readMasterData.getPermissions())).containsExactly(
        AccessControlConfigSimple.PERMISSION_FIND_OFFER, AccessControlConfigSimple.PERMISSION_FIND_PRODUCT,
        AccessControlConfigSimple.PERMISSION_FIND_STAFF_MEMBER, AccessControlConfigSimple.PERMISSION_FIND_TABLE);
    assertThat(readMasterData.getInherits()).isEmpty();

    // and when
    Set<String> permissions = new HashSet<>();
    config.collectAccessControlIds(AccessControlConfigSimple.GROUP_READ_MASTER_DATA, permissions);

    // then
    assertThat(permissions).containsExactlyInAnyOrder(AccessControlConfigSimple.GROUP_READ_MASTER_DATA,
        AccessControlConfigSimple.PERMISSION_FIND_OFFER, AccessControlConfigSimple.PERMISSION_FIND_PRODUCT,
        AccessControlConfigSimple.PERMISSION_FIND_STAFF_MEMBER, AccessControlConfigSimple.PERMISSION_FIND_TABLE);

    // and when
    permissions = new HashSet<>();
    config.collectAccessControlIds(AccessControlConfigSimple.GROUP_CHIEF, permissions);

    // then
    assertThat(permissions).containsExactlyInAnyOrder(AccessControlConfigSimple.GROUP_READ_MASTER_DATA,
        AccessControlConfigSimple.PERMISSION_FIND_OFFER, AccessControlConfigSimple.PERMISSION_FIND_PRODUCT,
        AccessControlConfigSimple.PERMISSION_FIND_STAFF_MEMBER, AccessControlConfigSimple.PERMISSION_FIND_TABLE,
        //
        AccessControlConfigSimple.GROUP_COOK, AccessControlConfigSimple.PERMISSION_FIND_ORDER,
        AccessControlConfigSimple.PERMISSION_SAVE_ORDER, AccessControlConfigSimple.PERMISSION_FIND_ORDER_POSITION,
        AccessControlConfigSimple.PERMISSION_SAVE_ORDER_POSITION,
        //
        AccessControlConfigSimple.GROUP_BARKEEPER, AccessControlConfigSimple.PERMISSION_FIND_BILL,
        AccessControlConfigSimple.PERMISSION_SAVE_BILL, AccessControlConfigSimple.PERMISSION_DELETE_BILL,
        AccessControlConfigSimple.PERMISSION_DELETE_ORDER,
        //
        AccessControlConfigSimple.GROUP_WAITER, AccessControlConfigSimple.PERMISSION_SAVE_TABLE,
        //
        AccessControlConfigSimple.GROUP_CHIEF, AccessControlConfigSimple.PERMISSION_SAVE_OFFER,
        AccessControlConfigSimple.PERMISSION_SAVE_PRODUCT, AccessControlConfigSimple.PERMISSION_SAVE_STAFF_MEMBER,
        AccessControlConfigSimple.PERMISSION_DELETE_OFFER, AccessControlConfigSimple.PERMISSION_DELETE_PRODUCT,
        AccessControlConfigSimple.PERMISSION_DELETE_STAFF_MEMBER,
        AccessControlConfigSimple.PERMISSION_DELETE_ORDER_POSITION, AccessControlConfigSimple.PERMISSION_DELETE_TABLE);
  }

  private static AccessControlGroup getSingleInherit(AccessControlGroup group) {

    List<AccessControlGroup> inherits = group.getInherits();
    assertThat(inherits).hasSize(1);
    AccessControlGroup inheritedGroup = inherits.get(0);
    assertThat(inheritedGroup).isNotNull();
    return inheritedGroup;
  }

  private static String[] flatten(Collection<? extends AccessControl> accessControlList) {

    String[] ids = new String[accessControlList.size()];
    int i = 0;
    for (AccessControl accessControl : accessControlList) {
      ids[i++] = accessControl.getId();
    }
    return ids;
  }

}
