#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.logic.impl.usecase;

import ${package}.general.common.api.datatype.Role;
import ${package}.general.common.api.exception.IllegalEntityStateException;
import ${package}.salesmanagement.logic.api.Salesmanagement;
import ${package}.salesmanagement.logic.api.to.OrderEto;
import ${package}.staffmanagement.logic.api.Staffmanagement;
import ${package}.staffmanagement.logic.api.to.StaffMemberEto;
import ${package}.tablemanagement.common.api.datatype.TableState;
import ${package}.tablemanagement.logic.api.to.TableEto;
import ${package}.tablemanagement.logic.api.usecase.UcManageTable;
import ${package}.tablemanagement.logic.base.usecase.AbstractTableUc;
import ${package}.tablemanagement.persistence.api.TableEntity;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcManageTable}.
 *
 * @author jozitz
 */
@Named
public class UcManageTableImpl extends AbstractTableUc implements UcManageTable {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageTableImpl.class);

  private Salesmanagement salesManagement;

  private Staffmanagement staffmanagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteTable(Long tableId) {

    TableEntity table = getTableDao().find(tableId);

    if (!table.getState().isFree()) {
      throw new IllegalEntityStateException(table, table.getState());
    }

    getTableDao().delete(table);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TableEto createTable(TableEto table) {

    Long tableId = table.getId();
    if (tableId != null) {
      throw new IllegalArgumentException("Table ID must not be set for creation!");
    }

    TableEntity tableEntity = getBeanMapper().map(table, TableEntity.class);
    // initialize
    tableEntity.setState(TableState.FREE);
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
    return getBeanMapper().map(tableEntity, TableEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void markTableAs(TableEto table, TableState newState) {

    Objects.requireNonNull(table, "table");

    long tableId = table.getId();
    TableEntity targetTable = getTableDao().find(tableId);
    if (targetTable.getState() == newState) {
      return;
    }

    switch (newState) {
    case FREE:
      // we need the ensure that there is no open order associated with the table...
      OrderEto openOrder = this.salesManagement.findOpenOrderForTable(tableId);
      if (openOrder != null) {
        throw new IllegalEntityStateException(table, table.getState(), newState);
      }
      break;
    case RESERVED:
      if (table.getState() != TableState.FREE) {
        // table has to be free before going to reserved state...
        throw new IllegalEntityStateException(table, table.getState(), newState);
      }
      break;
    default:
      // nothing to do...
      break;
    }
    targetTable.setState(newState);
    getTableDao().save(targetTable);
    LOG.debug("The table with id '{}' is marked as {}.", tableId, newState);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTableReleasable(TableEto table) {

    if (table.getState() != TableState.OCCUPIED) {
      return true;
    }
    OrderEto order = this.salesManagement.findOpenOrderForTable(table.getId());
    // no open order so the table is actually free...
    return order == null;
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

}
