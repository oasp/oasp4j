package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.rest.TablemanagementRestService;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * @author agreul, gazzi, jmolinar
 */
@Named("TablemanagementRestService")
public class TablemanagementRestServiceImpl implements TablemanagementRestService {

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

  @Override
  public TableEto getTable(String id) {

    long idAsLong;
    if (id == null) {
      throw new BadRequestException("missing id");
    }

    TableEto tableEto = null;

    try {
      idAsLong = Long.parseLong(id);
      tableEto = this.tableManagement.findTable(idAsLong);

      if (tableEto == null)
        throw new NotFoundException("table not found");

    } catch (NumberFormatException e) {
      throw new BadRequestException("id is not a number");
    } catch (NotFoundException e) {
      throw new BadRequestException(e.getMessage());
    }
    return this.tableManagement.findTable(idAsLong);
  }

  @Override
  @Deprecated
  public List<TableEto> getAllTables() {

    List<TableEto> allTables = this.tableManagement.findAllTables();
    return allTables;
  }

  @Override
  @Deprecated
  public TableEto createTable(TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  @Override
  public TableEto saveTable(TableEto table) {

    return this.tableManagement.saveTable(table);
  }

  @Override
  public void deleteTable(long id) {

    this.tableManagement.deleteTable(id);
  }

  @Override
  @Deprecated
  public List<TableEto> getFreeTables() {

    return this.tableManagement.findFreeTables();
  }

  @Override
  public boolean isTableReleasable(long id) {

    TableEto table = this.tableManagement.findTable(id);
    if (table == null) {
      throw new ObjectNotFoundUserException(Table.class, id);
    } else {
      return this.tableManagement.isTableReleasable(table);
    }
  }

  @Override
  public PaginatedListTo<TableEto> findTablesByPost(TableSearchCriteriaTo searchCriteriaTo) {

    return this.tableManagement.findTableEtos(searchCriteriaTo);
  }
}
