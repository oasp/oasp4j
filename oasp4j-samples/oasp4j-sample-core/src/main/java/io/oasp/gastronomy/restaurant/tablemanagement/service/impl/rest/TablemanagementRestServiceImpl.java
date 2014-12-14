package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcFindTable;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcManageTable;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * The service class for REST calls in order to execute the methods in {@link Tablemanagement}.
 *
 * @author agreul
 */
@Path("/tablemanagement")
@Named("TablemanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@Validated
public class TablemanagementRestServiceImpl {

  private Tablemanagement tableManagement;

  /**
   * This method sets the field <tt>tableManagement</tt>.
   *
   * @param tableManagement the new value of the field tableManagement
   */
  @Inject
  public void setTableManagement(Tablemanagement tableManagement) {

    this.tableManagement = tableManagement;
  }

  /**
   * Delegates to {@link UcFindTable#findTable}.
   *
   * @param id the ID of the {@link TableEto}
   * @return the {@link TableEto}
   */
  @GET
  @Path("/table/{id}/")
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public TableEto getTable(@PathParam("id") String id) {

    Long idAsLong;
    if (id == null) {
      throw new BadRequestException("missing id");
    }
    try {
      idAsLong = Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new BadRequestException("id is not a number");
    } catch (NotFoundException e) {
      throw new BadRequestException("table not found");
    }
    return this.tableManagement.findTable(idAsLong);
  }

  /**
   * Delegates to {@link UcFindTable#findAllTables}.
   *
   * @return list of all existing restaurant {@link TableEto}s
   */
  @GET
  @Path("/table/")
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public List<TableEto> getAllTables() {

    List<TableEto> allTables = this.tableManagement.findAllTables();
    return allTables;
  }

  /**
   * Delegates to {@link UcManageTable#saveTable}.
   *
   * @param table the {@link TableEto} to be created
   * @return the recently created {@link TableEto}
   */
  @POST
  @Path("/table/")
  @RolesAllowed(PermissionConstants.SAVE_TABLE)
  @Deprecated
  public TableEto createTable(@Valid TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  /**
   * Delegates to {@link UcManageTable#saveTable}.
   *
   * @param table the {@link TableEto} to be created
   * @return the recently created {@link TableEto}
   */
  @POST
  @Path("/table/")
  @RolesAllowed(PermissionConstants.SAVE_TABLE)
  public TableEto saveTable(@Valid TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  /**
   * Delegates to {@link UcManageTable#deleteTable}.
   *
   * @param id ID of the {@link TableEto} to be deleted
   */
  @DELETE
  @Path("/table/{id}/")
  @RolesAllowed(PermissionConstants.DELETE_TABLE)
  public void deleteTable(@PathParam("id") Long id) {

    this.tableManagement.deleteTable(id);
  }

  /**
   * Delegates to {@link UcFindTable#findFreeTables}.
   *
   * @return list of all existing free {@link TableEto}s
   */
  @GET
  @Path("/freetables/")
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public List<TableEto> getFreeTables() {

    return this.tableManagement.findFreeTables();
  }

  /**
   * Delegates to {@link UcManageTable#markTableAs}.
   *
   * @param id ID of the {@link TableEto}
   * @param newState the new {@link TableState}
   */
  @Path("/table/{id}/marktableas/{newState}")
  @POST
  @RolesAllowed(PermissionConstants.SAVE_TABLE)
  public void markTableAs(@PathParam("id") Long id, @PathParam("newState") TableState newState) {

    TableEto table = this.tableManagement.findTable(id);
    if (table == null) {
      throw new ObjectNotFoundUserException(Table.class, id);
    } else {
      this.tableManagement.markTableAs(table, newState);
    }
  }

  /**
   * Delegates to {@link UcManageTable#isTableReleasable}.
   *
   * @param id ID of the {@link TableEto}
   * @return <code>true</code> if the table could be released<br>
   *         <code>false</code>, otherwise
   */
  @GET
  @Path("/table/{id}/istablereleasable/")
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public boolean isTableReleasable(@PathParam("id") Long id) {

    TableEto table = this.tableManagement.findTable(id);
    if (table == null) {
      throw new ObjectNotFoundUserException(Table.class, id);
    } else {
      return this.tableManagement.isTableReleasable(table);
    }
  }
}
