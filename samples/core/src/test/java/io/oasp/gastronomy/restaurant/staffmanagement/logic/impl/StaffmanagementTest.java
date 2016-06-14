package io.oasp.gastronomy.restaurant.staffmanagement.logic.impl;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.common.builders.StaffMemberEtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.TestUtil;
import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.module.basic.configuration.OaspProfile;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * TODO sroeger This type ...
 *
 * @author sroeger
 * @since dev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@WebIntegrationTest("server.port:0")
@ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST })
public class StaffmanagementTest extends SubsystemTest {

  @Inject
  Staffmanagement staffmanagement;

  @Before
  public void setUp() {

    TestUtil.login("chief", PermissionConstants.SAVE_STAFF_MEMBER, PermissionConstants.DELETE_STAFF_MEMBER,
        PermissionConstants.FIND_STAFF_MEMBER);
  }

  @After
  public void tearDown() {

    TestUtil.logout();
  }

  @Test
  public void testNewSaveStaffMember() {

    // given
    StaffMemberEto staffMemberEto = new StaffMemberEtoBuilder().name("myLoginname").firstName("Marc")
        .lastName("Hollowen").role(Role.BARKEEPER).createNew();

    // when
    StaffMemberEto savedStaffMemberEto = this.staffmanagement.saveStaffMember(staffMemberEto);

    // then
    assertThat(savedStaffMemberEto).isNotNull();
    assertThat(savedStaffMemberEto.getName()).isEqualTo("myLoginname");
    assertThat(savedStaffMemberEto.getFirstName()).isEqualTo("Marc");

  }

  @Test
  public void testDefaultSaveStaffMemberAndDeleteStaffMember() {

    // given
    StaffMemberEto staffMemberEto = new StaffMemberEtoBuilder().name("myOtherLoginname").createNew();

    // when
    StaffMemberEto savedStaffMemberEto = this.staffmanagement.saveStaffMember(staffMemberEto);

    // then
    assertThat(savedStaffMemberEto).isNotNull();
    assertThat(savedStaffMemberEto.getName()).isEqualTo("myOtherLoginname");
    assertThat(savedStaffMemberEto.getFirstName()).isEqualTo("defaultFirstName");

    // when
    this.staffmanagement.deleteStaffMemberByLogin("myOtherLoginname");
    try {
      StaffMemberEto deletedStaffMemberEto = this.staffmanagement.findStaffMemberByLogin("myOtherLoginname");
    } catch (Exception e) {

      // then

      assertThat(e).isInstanceOf(EmptyResultDataAccessException.class);
    }

  }

}
