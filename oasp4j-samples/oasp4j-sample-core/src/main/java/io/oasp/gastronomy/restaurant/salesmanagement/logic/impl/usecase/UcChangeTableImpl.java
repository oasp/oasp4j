package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcChangeTable;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

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

  /**
   * {@inheritDoc}
   */
  @Override
  public void changeTable(OrderEto order, TableEto newTable) {

    // save old table data
    long oldTableId = order.getTableId();
    TableState oldTableState = this.tableManagement.findTable(oldTableId).getState();

    // update order
    order.setTableId(newTable.getId());

    // change table status:
    // marks old table as free
    this.tableManagement.markTableAs(this.tableManagement.findTable(oldTableId), TableState.FREE);
    // marks new table with copied status
    this.tableManagement.markTableAs(newTable, oldTableState);

  }

  /**
   * @param tableManagement the {@link Tablemanagement} to {@link Inject}.
   */
  @Inject
  public void setTableManagement(Tablemanagement tableManagement) {

    this.tableManagement = tableManagement;
  }

}
