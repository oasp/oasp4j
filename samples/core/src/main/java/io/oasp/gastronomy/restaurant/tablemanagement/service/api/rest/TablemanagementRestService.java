package io.oasp.gastronomy.restaurant.tablemanagement.service.api.rest;

import java.util.List;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.oasp.gastronomy.restaurant.general.common.api.RestService;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * The service class for REST calls in order to execute the methods in {@link Tablemanagement}.
 *
 */
@Path("/tablemanagement/v1")
@Named("TablemanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TablemanagementRestService extends RestService {

  /**
   * Delegates to {@link Tablemanagement#findTable}.
   *
   * @param id the ID of the {@link TableEto}
   * @return the {@link TableEto}
   */
  @GET
  @Path("/table/{id}/")
  TableEto getTable(@PathParam("id") long id);

  /**
   * Delegates to {@link Tablemanagement#findAllTables}.
   *
   * @return list of all existing restaurant {@link TableEto}s
   */
  @GET
  @Path("/table/")
  @Deprecated
  List<TableEto> getAllTables();

  /**
   * Delegates to {@link Tablemanagement#saveTable}.
   *
   * @param table the {@link TableEto} to be created
   * @return the recently created {@link TableEto}
   */
  @POST
  @Path("/table/")
  @Deprecated
  TableEto createTable(TableEto table);

  /**
   * Delegates to {@link Tablemanagement#saveTable}.
   *
   * @param table the {@link TableEto} to be created
   * @return the recently created {@link TableEto}
   */
  @POST
  @Path("/table/")
  TableEto saveTable(TableEto table);

  /**
   * Delegates to {@link Tablemanagement#deleteTable}.
   *
   * @param id ID of the {@link TableEto} to be deleted
   */
  @DELETE
  @Path("/table/{id}/")
  void deleteTable(@PathParam("id") long id);

  /**
   * Delegates to {@link Tablemanagement#findFreeTables}.
   *
   * @return list of all existing free {@link TableEto}s
   */
  @GET
  @Path("/freetables/")
  @Deprecated
  List<TableEto> getFreeTables();

  /**
   * Delegates to {@link Tablemanagement#isTableReleasable}.
   *
   * @param id ID of the {@link TableEto}
   * @return {@code true} if the table could be released<br>
   *         {@code false}, otherwise
   */
  @GET
  @Path("/table/{id}/istablereleasable/")
  boolean isTableReleasable(@PathParam("id") long id);

  /**
   * Delegates to {@link Tablemanagement#findTableEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding tables.
   * @return the {@link PaginatedListTo list} of matching {@link TableEto}s.
   */
  @Path("/table/search")
  @POST
  PaginatedListTo<TableEto> findTablesByPost(TableSearchCriteriaTo searchCriteriaTo);
}
