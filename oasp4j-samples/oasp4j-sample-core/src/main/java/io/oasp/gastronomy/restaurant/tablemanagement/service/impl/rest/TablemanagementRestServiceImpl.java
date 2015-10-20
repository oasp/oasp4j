package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
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

/**
 * The service class for REST calls in order to execute the methods in {@link Tablemanagement}.
 *
 * @author agreul
 */
@Path("/tablemanagement/v1")
@Named("TablemanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
   * Delegates to {@link Tablemanagement#findTable}.
   *
   * @param id the ID of the {@link TableEto}
   * @return the {@link TableEto}
   */
  @GET
  @Path("/table/{id}/")
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
   * Delegates to {@link Tablemanagement#findAllTables}.
   *
   * @return list of all existing restaurant {@link TableEto}s
   */
  @GET
  @Path("/table/")
  @Deprecated
  public List<TableEto> getAllTables() {

    List<TableEto> allTables = this.tableManagement.findAllTables();
    return allTables;
  }

  /**
   * Delegates to {@link Tablemanagement#saveTable}.
   *
   * @param table the {@link TableEto} to be created
   * @return the recently created {@link TableEto}
   */
  @POST
  @Path("/table/")
  @Deprecated
  public TableEto createTable(TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  /**
   * Delegates to {@link Tablemanagement#saveTable}.
   *
   * @param table the {@link TableEto} to be created
   * @return the recently created {@link TableEto}
   */
  @POST
  @Path("/table/")
  public TableEto saveTable(TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  /**
   * Delegates to {@link Tablemanagement#deleteTable}.
   *
   * @param id ID of the {@link TableEto} to be deleted
   */
  @DELETE
  @Path("/table/{id}/")
  public void deleteTable(@PathParam("id") Long id) {

    this.tableManagement.deleteTable(id);
  }

  /**
   * Delegates to {@link Tablemanagement#findFreeTables}.
   *
   * @return list of all existing free {@link TableEto}s
   */
  @GET
  @Path("/freetables/")
  @Deprecated
  public List<TableEto> getFreeTables() {

    return this.tableManagement.findFreeTables();
  }

  /**
   * Delegates to {@link Tablemanagement#isTableReleasable}.
   *
   * @param id ID of the {@link TableEto}
   * @return {@code true} if the table could be released<br>
   *         {@code false}, otherwise
   */
  @GET
  @Path("/table/{id}/istablereleasable/")
  public boolean isTableReleasable(@PathParam("id") Long id) {

    TableEto table = this.tableManagement.findTable(id);
    if (table == null) {
      throw new ObjectNotFoundUserException(Table.class, id);
    } else {
      return this.tableManagement.isTableReleasable(table);
    }
  }

  /**
   * Delegates to {@link UcFindTable#findTableEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding tables.
   * @return the {@link PaginatedListTo list} of matching {@link TableEto}s.
   */
  @Path("/table/search")
  @POST
  public PaginatedListTo<TableEto> findTablesByPost(TableSearchCriteriaTo searchCriteriaTo) {

    return this.tableManagement.findTableEtos(searchCriteriaTo);
  }
}
