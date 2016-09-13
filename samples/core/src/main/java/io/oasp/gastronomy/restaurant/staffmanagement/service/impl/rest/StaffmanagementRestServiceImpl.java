package io.oasp.gastronomy.restaurant.staffmanagement.service.impl.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.staffmanagement.service.api.rest.StaffmanagementRestService;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 */
@Named("StaffmanagementRestService")
public class StaffmanagementRestServiceImpl implements StaffmanagementRestService {

  private Staffmanagement staffmanagement;

  /**
   * @param staffManagement the staffManagement to be set
   */
  @Inject
  public void setStaffmanagement(Staffmanagement staffManagement) {

    this.staffmanagement = staffManagement;
  }

  @Override
  public List<StaffMemberEto> getAllStaffMember() {

    return this.staffmanagement.findAllStaffMembers();
  }

  @Override
  public StaffMemberEto getStaffMember(String login) {

    return this.staffmanagement.findStaffMemberByLogin(login);
  }

  // although login is not explicitly needed here, the path structure is intentionally chosen
  // it is up to the GUI-Team to either insert a (maybe redundant) call on getStaffMember or to leave it
  // like that and do the update right in the view of a previously "loaded" StaffMember

  @Override
  @Deprecated
  public void updateStaffMember(StaffMemberEto staffMemberBo) {

    this.staffmanagement.saveStaffMember(staffMemberBo);
  }

  @Override
  public StaffMemberEto saveStaffMember(StaffMemberEto staffMemberEto) {

    return this.staffmanagement.saveStaffMember(staffMemberEto);
  }

  @Override
  public void deleteStaffMember(String login) {

    this.staffmanagement.deleteStaffMemberByLogin(login);
  }

  @Override
  public PaginatedListTo<StaffMemberEto> findStaffMembersByPost(StaffMemberSearchCriteriaTo searchCriteriaTo) {

    return this.staffmanagement.findStaffMemberEtos(searchCriteriaTo);
  }
}
