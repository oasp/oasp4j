package io.oasp.gastronomy.restaurant.staffmanagement.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import io.oasp.AbstractDBRollbackTest;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.LoginCredentials;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.RestUrls;
import io.oasp.gastronomy.restaurant.test.general.TestData.Additional;
import io.oasp.gastronomy.restaurant.test.general.TestData.DB;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;
import io.oasp.gastronomy.restaurant.test.general.webclient.WebClientWrapper;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test staff management rest service
 *
 * @author arklos
 */
public class StaffManagementRestServiceTest extends AbstractDBRollbackTest {

  private WebClientWrapper waiter = new WebClientWrapper(LoginCredentials.waiterUsername,
      LoginCredentials.waiterPassword);

  private WebClientWrapper chief = new WebClientWrapper(LoginCredentials.chiefUsername, LoginCredentials.chiefPassword);

  /**
   * Tests get staff member
   */
  @Test
  public void getStaffmemberTest() {

    ResponseData<StaffMemberEto> member =
        this.waiter.get(
            RestUrls.StaffManagement.getDeleteStaffMemberUrl(DB.STAFF_MEMEBR_1.getLastName().toLowerCase()),
            StaffMemberEto.class);
    assertThat(member.getResponse().getStatus(), is(200));
    assertThat(member.getResponseObject().getId(), is(DB.STAFF_MEMEBR_1.getId()));
  }

  /**
   * Test get all staff members
   */
  @Test
  public void getAllStaffmembersTest() {

    List<ResponseData<StaffMemberEto>> members =
        this.waiter.getAll(RestUrls.StaffManagement.getGetAllStaffMembersUrl(), StaffMemberEto.class);
    assertThat(members.size(), is(DB.ALL_STAFF_MEMBER.size()));
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
    assertThat(member.getResponse().getStatus(), is(200));
    assertThat(member.getResponseObject().getFirstName(), is(Additional.CHANGED_STAFF_MEMEBR_1.getFirstName()));
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
    assertThat(members.size(), is(DB.ALL_STAFF_MEMBER.size() - 1));
  }
}
