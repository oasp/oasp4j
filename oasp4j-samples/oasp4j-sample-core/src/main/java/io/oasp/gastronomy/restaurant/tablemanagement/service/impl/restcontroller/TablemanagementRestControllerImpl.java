package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.restcontroller;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;

import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TSOWADA
 *
 */
@RestController
@Configuration
@RequestMapping("/tablemanagement/v1")
public class TablemanagementRestControllerImpl {

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
  @RequestMapping("/table/{id}/")
  public TableEto getTable(@PathParam("id") String id) {

    long idAsLong;
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
  @RequestMapping("/gettable/")
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
  @RequestMapping("/table/")
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
  @RequestMapping("/savetable/")
  public TableEto saveTable(TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  /**
   * Delegates to {@link Tablemanagement#deleteTable}.
   *
   * @param id ID of the {@link TableEto} to be deleted
   */
  @DELETE
  @RequestMapping("/table/{id}")
  public void deleteTable(@PathParam("id") long id) {

    this.tableManagement.deleteTable(id);
  }

  /**
   * Delegates to {@link Tablemanagement#findFreeTables}.
   *
   * @return list of all existing free {@link TableEto}s
   */
  @GET
  @RequestMapping("/freetables/")
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
  @RequestMapping("/table/{id}/istablereleasable/")
  public boolean isTableReleasable(@PathParam("id") long id) {

    TableEto table = this.tableManagement.findTable(id);
    if (table == null) {
      throw new ObjectNotFoundUserException(Table.class, id);
    } else {
      return this.tableManagement.isTableReleasable(table);
    }
  }

  /**
   * Delegates to {@link Tablemanagement#findTableEtos}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding tables.
   * @return the {@link PaginatedListTo list} of matching {@link TableEto}s.
   */
  @RequestMapping("/table/search")
  @POST
  public PaginatedListTo<TableEto> findTablesByPost(TableSearchCriteriaTo searchCriteriaTo) {

    return this.tableManagement.findTableEtos(searchCriteriaTo);
  }

}
