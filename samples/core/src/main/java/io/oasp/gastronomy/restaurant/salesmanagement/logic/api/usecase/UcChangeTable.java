package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * Interface of {@link io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc use case} to
 * {@link #changeTable(long, long) change the table}.
 *
 * Interface of UcChangeTable to centralize documentation and signatures of methods.
 *
 */
public interface UcChangeTable {

  /**
   * UseCase to change from one {@link TableEto table} to another. The people sitting at a table are identified by their
   * {@link OrderEto order} that has to be provided as argument.
   *
   * @param orderId the {@link OrderEto order}
   * @param newTableId the new {@link TableEto table} to switch to.
   */
  void changeTable(long orderId, long newTableId);

}
