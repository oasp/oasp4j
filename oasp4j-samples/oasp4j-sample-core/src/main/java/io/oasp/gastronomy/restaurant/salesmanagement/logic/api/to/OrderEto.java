/**
 *
 */
package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;

/**
 * {@link AbstractEto ETO} for an {@link Order}.
 *
 * @author rjoeris
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

  /**
   * {@inheritDoc}
   */
  @Override
  public long getTableId() {

    return this.tableId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTableId(long tableId) {

    this.tableId = tableId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderState getState() {

    return this.state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setState(OrderState state) {

    this.state = state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
    result = prime * result + (int) this.tableId;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    OrderEto other = (OrderEto) obj;
    if (getId() == other.getId()) {
      return false;
    }
    if (this.state != other.state) {
      return false;
    }
    if (this.tableId != other.tableId) {
      return false;
    }
    return true;
  }

}
