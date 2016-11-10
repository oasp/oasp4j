/**
 *
 */
package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.module.basic.common.api.to.AbstractEto;

/**
 * {@link AbstractEto ETO} for an {@link Order}.
 *
 */
public class OrderEto extends AbstractEto implements Order {

  private static final long serialVersionUID = 1L;

  private long tableId;

  private OrderState state;

  /**
   * The constructor.
   */
  public OrderEto() {

    this.state = OrderState.OPEN;
  }

  @Override
  public long getTableId() {

    return this.tableId;
  }

  @Override
  public void setTableId(long tableId) {

    this.tableId = tableId;
  }

  @Override
  public OrderState getState() {

    return this.state;
  }

  @Override
  public void setState(OrderState state) {

    this.state = state;
  }

}
