#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.service.impl.rest;

import ${package}.general.logic.api.PermissionConstants;
import ${package}.staffmanagement.logic.api.Staffmanagement;
import ${package}.staffmanagement.logic.api.to.StaffMemberEto;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.transaction.annotation.Transactional;

/**
 * This class contains methods for REST calls. Some URI structures may seem depricated, but in fact are not. See the
 * correspondent comments on top.
 *
 * @author agreul
 */
@Path("/staffmanagement/staff")
@Named("StaffmanagementRestService")
@Transactional
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
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/")
  @RolesAllowed(PermissionConstants.FIND_STAFF_MEMBER)
  public List<StaffMemberEto> getAllStaffMember() {

    return this.staffManagement.findAllStaffMembers();
  }

  /**
   * @param login the login of a staff member
   * @return {@link StaffMemberEto}
   */
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/{login}")
  @RolesAllowed(PermissionConstants.FIND_STAFF_MEMBER)
  public StaffMemberEto getStaffMember(@PathParam("login") String login) {

    return this.staffManagement.findStaffMemberByLogin(login);
  }

  // although login is not explicitly needed here, the path structure is intentionally chosen
  // it is up to the GUI-Team to either insert a (maybe redundant) call on getStaffMember or to leave it
  // like that and do the update right in the view of a previously "loaded" StaffMember
  /**
   * @param staffMemberBo the staffMember to be updated as JSON
   */
  @Consumes(MediaType.APPLICATION_JSON)
  @PUT
  @Path("/{login}")
  @RolesAllowed(PermissionConstants.UPDATE_STAFF_MEMBER)
  public void updateStaffMember(StaffMemberEto staffMemberBo) {

    this.staffManagement.updateStaffMember(staffMemberBo);
  }

  /**
   * @param login the login
   */
  @DELETE
  @Path("/{login}")
  @RolesAllowed(PermissionConstants.DELETE_STAFF_MEMBER)
  public void deleteStaffMember(@PathParam("login") String login) {

    this.staffManagement.deleteStaffMemberByLogin(login);
  }
}
