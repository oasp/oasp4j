package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.module.basic.common.api.to.AbstractCto;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractCto CTO} for an {@link #getOrder() order} with its {@link #getPositions() positions}.
 *
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
}
