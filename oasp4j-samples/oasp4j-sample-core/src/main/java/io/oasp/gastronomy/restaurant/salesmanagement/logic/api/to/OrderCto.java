package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractCto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link AbstractCto CTO} for an {@link #getOrder() order} with its {@link #getPositions() positions}.
 *
 * @author hohwille
 */
public class OrderCto extends AbstractCto {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private OrderEto order;

  private List<OrderPositionEto> positions;

  /**
   * The constructor.
   */
  public OrderCto() {

    super();
  }

  /**
   * @return orderEto
   */
  public OrderEto getOrder() {

    return this.order;
  }

  /**
   * @param orderEto the orderEto to set
   */
  public void setOrder(OrderEto orderEto) {

    this.order = orderEto;
  }

  /**
   * @return positions
   */
  public List<OrderPositionEto> getPositions() {

    if (this.positions == null) {
      this.positions = new ArrayList<>();
    }
    return this.positions;
  }

  /**
   * @param positions the positions to set
   */
  public void setPositions(List<OrderPositionEto> positions) {

    this.positions = positions;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.order == null) ? 0 : this.order.hashCode());
    result = prime * result + ((this.positions == null) ? 0 : this.positions.hashCode());
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
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    OrderCto other = (OrderCto) obj;
    if (Objects.equals(this.order, other.order)) {
      return false;
    }
    if (Objects.equals(this.positions, other.positions)) {
      return false;
    }
    return true;
  }

}
