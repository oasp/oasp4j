package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.exception.ChangeTableIllegalStateCombinationException;
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
 */
@Named
@UseCase
public class UcChangeTableImpl extends AbstractUc implements UcChangeTable {

  private Tablemanagement tableManagement;

  private Salesmanagement salesManagement;

  @Override
  @RolesAllowed(PermissionConstants.SAVE_TABLE)
  public void changeTable(long orderId, long newTableId) {

    OrderEto order = this.salesManagement.findOrder(orderId);
    // save old table data
    long oldTableId = order.getTableId();
    if (oldTableId == newTableId) {
      return; // nothing to do...
    }
    TableEto oldTable = this.tableManagement.findTable(oldTableId);
    TableState oldTableState = oldTable.getState();

    // throw exception if the newTableState is occupied
    TableEto newTable = this.tableManagement.findTable(newTableId);
    if (newTable.getState().isOccupied()) {
      throw new ChangeTableIllegalStateCombinationException(order, newTable);
    }

    // update order
    order.setTableId(newTableId);

    // marks new table with copied status
    newTable.setState(oldTableState);

    this.tableManagement.saveTable(newTable);

    // saves Order for the new table
    this.salesManagement.saveOrder(order);

    // change table status:
    // marks old table as free
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
