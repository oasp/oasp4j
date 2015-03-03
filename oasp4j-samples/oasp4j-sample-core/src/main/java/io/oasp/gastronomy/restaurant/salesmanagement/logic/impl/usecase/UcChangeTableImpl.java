package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcChangeTable;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * UseCase: The guests can change a table to get a better table.
 *
 * @author rjoeris
 */
@Named
public class UcChangeTableImpl extends AbstractUc implements UcChangeTable {

  private Tablemanagement tableManagement;

  private Salesmanagement salesManagement;

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.SAVE_TABLE)
  public void changeTable(long orderId, long newTableId) {

    OrderEto order = this.salesManagement.findOrder(orderId);
    // save old table data
    long oldTableId = order.getTableId();
    TableState oldTableState = this.tableManagement.findTable(oldTableId).getState();

    // update order
    order.setTableId(newTableId);

    // marks new table with copied status
    TableEto newTable = this.tableManagement.findTable(newTableId);
    newTable.setState(oldTableState);

    this.tableManagement.saveTable(newTable);

    // saves Order for the new table
    this.salesManagement.saveOrder(order);

    // change table status:
    // marks old table as free
    TableEto oldTable = this.tableManagement.findTable(oldTableId);
    oldTable.setState(TableState.FREE);
    this.tableManagement.saveTable(oldTable);

  }

  /**
   * @param tableManagement the {@link Tablemanagement} to {@link Inject}.
   */
  @Inject
  public void setTableManagement(Tablemanagement tableManagement) {

    this.tableManagement = tableManagement;
  }

  /**
   * @param salesManagement the {@link Salesmanagement} to {@link Inject}.
   */
  @Inject
  public void setSalesManagement(Salesmanagement salesManagement) {

    this.salesManagement = salesManagement;
  }

}
