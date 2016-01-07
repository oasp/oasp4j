package io.oasp.gastronomy.restaurant.staffmanagement.services;

import io.oasp.gastronomy.restaurant.general.common.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.test.config.RestUrls;
import io.oasp.gastronomy.restaurant.test.config.TestData.Additional;
import io.oasp.gastronomy.restaurant.test.config.TestData.DB;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test staff management rest service
 *
 * @author arklos
 */
public class StaffManagementRestServiceTest extends AbstractRestServiceTest {

  /**
   * Tests get staff member
   */
  @Test
  public void getStaffmemberTest() {

    ResponseData<StaffMemberEto> member =
        this.waiter.get(
            RestUrls.StaffManagement.getDeleteStaffMemberUrl(DB.STAFF_MEMEBR_1.getLastName().toLowerCase()),
            StaffMemberEto.class);
    assertThat(member.getResponse().getStatus()).isEqualTo(200);
    assertThat(member.getResponseObject().getId()).isEqualTo(DB.STAFF_MEMEBR_1.getId());
  }

  /**
   * Test get all staff members
   */
  @Test
  public void getAllStaffmembersTest() {

    List<ResponseData<StaffMemberEto>> members =
        this.waiter.getAll(RestUrls.StaffManagement.getGetAllStaffMembersUrl(), StaffMemberEto.class);
    assertThat(members.size()).isEqualTo(4);
  }

  /**
   * Tests update staffMember
   */
  @Test
  @Ignore
  public void updateStaffMemberTest() {

    this.chief.put(RestUrls.StaffManagement.getUpdateStaffMember(DB.STAFF_MEMEBR_1.getLastName().toLowerCase()),
        Additional.CHANGED_STAFF_MEMEBR_1);
    ResponseData<StaffMemberEto> member =
        this.chief.get(RestUrls.StaffManagement.getGetStaffMember(DB.STAFF_MEMEBR_1.getLastName().toLowerCase()),
            StaffMemberEto.class);
    assertThat(member.getResponse().getStatus()).isEqualTo(200);
    assertThat(member.getResponseObject().getFirstName()).isEqualTo(Additional.CHANGED_STAFF_MEMEBR_1.getFirstName());
  }

  /**
   * Test delete member
   */
  @Test
  @Ignore
  public void delteStaffMemberTest() {

    this.chief.delete(RestUrls.StaffManagement.getDeleteStaffMemberUrl(DB.STAFF_MEMEBR_1.getLastName().toLowerCase()));
    List<ResponseData<StaffMemberEto>> members =
        this.waiter.getAll(RestUrls.StaffManagement.getGetAllStaffMembersUrl(), StaffMemberEto.class);
    assertThat(members.size()).isEqualTo((DB.ALL_STAFF_MEMBER.size() - 1));
  }
}
