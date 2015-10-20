package io.oasp.gastronomy.restaurant.staffmanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This class contains methods for REST calls. Some URI structures may seem depricated, but in fact are not. See the
 * correspondent comments on top.
 *
 * @author agreul
 */
@Path("/staffmanagement/v1/staff")
@Named("StaffmanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StaffmanagementRestServiceImpl {

  private Staffmanagement staffManagement;

  /**
   * @param staffManagement the staffManagement to be set
   */
  @Inject
  public void setStaffManagement(Staffmanagement staffManagement) {

    this.staffManagement = staffManagement;
  }

  /**
   * @return a list of all {@link StaffMemberEto}
   *
   */
  @GET
  @Path("/")
  @Deprecated
  public List<StaffMemberEto> getAllStaffMember() {

    return this.staffManagement.findAllStaffMembers();
  }

  /**
   * @param login the login of a staff member
   * @return {@link StaffMemberEto}
   */
  @GET
  @Path("/{login}")
  public StaffMemberEto getStaffMember(@PathParam("login") String login) {

    return this.staffManagement.findStaffMemberByLogin(login);
  }

  // although login is not explicitly needed here, the path structure is intentionally chosen
  // it is up to the GUI-Team to either insert a (maybe redundant) call on getStaffMember or to leave it
  // like that and do the update right in the view of a previously "loaded" StaffMember
  /**
   * @param staffMemberBo the staffMember to be updated as JSON
   */
  @PUT
  @Path("/{login}")
  @Deprecated
  public void updateStaffMember(StaffMemberEto staffMemberBo) {

    this.staffManagement.saveStaffMember(staffMemberBo);
  }

  /**
   * Calls {@link Staffmanagement#saveStaffMember}.
   *
   * @param staffMemberEto the staffMember to be created or updated
   * @return the saved {@link StaffMemberEto}
   */
  @POST
  @Path("/")
  public StaffMemberEto saveStaffMember(StaffMemberEto staffMemberEto) {

    return this.staffManagement.saveStaffMember(staffMemberEto);
  }

  /**
   * @param login the login
   */
  @DELETE
  @Path("/{login}")
  public void deleteStaffMember(@PathParam("login") String login) {

    this.staffManagement.deleteStaffMemberByLogin(login);
  }

  /**
   * Delegates to {@link UcFindStaffMember#findStaffMemberEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding staffmembers.
   * @return the {@link PaginatedListTo list} of matching {@link StaffMemberEto}s.
   */
  @Path("/search")
  @POST
  public PaginatedListTo<StaffMemberEto> findStaffMembersByPost(StaffMemberSearchCriteriaTo searchCriteriaTo) {

    return this.staffManagement.findStaffMemberEtos(searchCriteriaTo);
  }
}
