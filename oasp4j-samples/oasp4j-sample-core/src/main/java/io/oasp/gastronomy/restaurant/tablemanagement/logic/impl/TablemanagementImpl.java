package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.general.logic.base.AbstractComponentFacade;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.dao.TableDao;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.mapper.TableMapper;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link Tablemanagement}.
 *
 * @author etomety
 */
@Named
public class TablemanagementImpl extends AbstractComponentFacade implements Tablemanagement {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(TablemanagementImpl.class);

  /** @see #getTableDao() */
  private TableDao tableDao;

  private Salesmanagement salesManagement;

  private Staffmanagement staffmanagement;

  private TableMapper tableMapper;

  /**
   * The constructor.
   */
  public TablemanagementImpl() {

    super();
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public TableEto findTable(Long id) {

    LOG.debug("Get table with id '" + id + "' from database.");
    return getTableMapper().toTableEto(getTableDao().findOne(id));
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public List<TableEto> findAllTables() {

    LOG.debug("Get all restaurant tables from database.");
    List<TableEntity> tables = getTableDao().findAll();
    return getTableMapper().toTableEtos(tables);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public List<TableEto> findFreeTables() {

    LOG.debug("Get all free restaurant tables from database.");

    List<TableEntity> tables = getTableDao().getFreeTables();
    return getTableMapper().toTableEtos(tables);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public PaginatedListTo<TableEto> findTableEtos(TableSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<TableEntity> tables = getTableDao().findTables(criteria);

    return new PaginatedListTo<>(getTableMapper().toTableEtos(tables.getResult()), tables.getPagination());
  }

  @Override
  @RolesAllowed(PermissionConstants.DELETE_TABLE)
  public void deleteTable(Long tableId) {

    TableEntity table = getTableDao().find(tableId);

    if (!table.getState().isFree()) {
      throw new IllegalEntityStateException(table, table.getState());
    }

    getTableDao().delete(table);
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_TABLE)
  public TableEto saveTable(@Valid TableEto table) {

    Objects.requireNonNull(table, "table");

    Long tableId = table.getId();

    TableEntity tableEntity = getTableMapper().toTableEntity(table);
    // initialize
    if (tableEntity.getState() == null) {
      tableEntity.setState(TableState.FREE);
    }
    Long waiterId = tableEntity.getWaiterId();
    if (waiterId != null) {
      StaffMemberEto staffMember = this.staffmanagement.findStaffMember(waiterId);
      if (Role.WAITER != staffMember.getRole()) {
        throw new IllegalArgumentException("Staffmember with id " + waiterId + " has role " + staffMember.getRole()
            + " and can not be associated as waiter for table with ID " + tableId + "!");
      }
    }

    getTableDao().save(tableEntity);
    LOG.debug("Table with id '{}' has been created.", tableEntity.getId());
    return getTableMapper().toTableEto(tableEntity);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public boolean isTableReleasable(TableEto table) {

    if (table.getState() != TableState.OCCUPIED) {
      return true;
    }
    OrderEto order = this.salesManagement.findOpenOrderForTable(table.getId());
    // no open order so the table is actually free...
    return order == null;
  }

  /**
   * @return the {@link TableDao} instance.
   */
  public TableDao getTableDao() {

    return this.tableDao;
  }

  /**
   * @param tableDao the {@link TableDao} to {@link Inject}.
   */
  @Inject
  public void setTableDao(TableDao tableDao) {

    this.tableDao = tableDao;
  }

  /**
   * Sets the field 'salesManagement'.
   *
   * @param salesManagement new value for salesManagement
   */
  @Inject
  public void setSalesManagement(Salesmanagement salesManagement) {

    this.salesManagement = salesManagement;
  }

  /**
   * @param staffmanagement the staffmanagement to set
   */
  @Inject
  public void setStaffmanagement(Staffmanagement staffmanagement) {

    this.staffmanagement = staffmanagement;
  }
  
  public TableMapper getTableMapper() {

      return tableMapper;
  }

  @Inject
  public void setTableMapper(TableMapper tableMapper) {

      this.tableMapper = tableMapper;
  }

}
