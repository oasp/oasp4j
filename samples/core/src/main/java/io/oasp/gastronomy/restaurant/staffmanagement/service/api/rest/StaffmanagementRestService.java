package io.oasp.gastronomy.restaurant.staffmanagement.service.api.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * TODO geazzi This type ...
 *
 * The service class for REST calls in order to execute the methods in {@link Staffmanagement}.
 *
 * @author geazzi
 * @since dev
 */
@Path("/staffmanagement/v1/staff")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface StaffmanagementRestService {

  /**
   * @param login the login of a staff member
   * @return {@link StaffMemberEto}
   */
  @GET
  @Path("/{login}")
  StaffMemberEto getStaffMember(@PathParam("login") String login);

  // although login is not explicitly needed here, the path structure is intentionally chosen
  // it is up to the GUI-Team to either insert a (maybe redundant) call on getStaffMember or to leave it
  // like that and do the update right in the view of a previously "loaded" StaffMember

  /**
   * @return a list of all {@link StaffMemberEto}
   *
   */
  @GET
  @Path("/")
  @Deprecated
  List<StaffMemberEto> getAllStaffMember();

  /**
   * @param staffMemberBo the staffMember to be updated as JSON
   */
  @PUT
  @Path("/{login}")
  @Deprecated
  void updateStaffMember(StaffMemberEto staffMemberBo);

  /**
   * Calls {@link Staffmanagement#saveStaffMember}.
   *
   * @param staffMemberEto the staffMember to be created or updated
   * @return the saved {@link StaffMemberEto}
   */
  @POST
  @Path("/")
  StaffMemberEto saveStaffMember(StaffMemberEto staffMemberEto);

  /**
   * @param login the login
   */
  @DELETE
  @Path("/{login}")
  void deleteStaffMember(@PathParam("login") String login);

  /**
   * Delegates to {@link Staffmanagement#findStaffMemberEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding staffmembers.
   * @return the {@link PaginatedListTo list} of matching {@link StaffMemberEto}s.
   */
  @Path("/search")
  @POST
  PaginatedListTo<StaffMemberEto> findStaffMembersByPost(StaffMemberSearchCriteriaTo searchCriteriaTo);

}
