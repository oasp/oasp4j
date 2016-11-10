package io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype;

/**
 * Represents the state of an {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order}.
 *
 */
public enum OrderState {

  /**
   * The state if the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order} has at least one open
   * {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}.
   */
  OPEN,

  /**
   * The state if the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order} is closed. Then also all
   * {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition} have to be closed.
   */
  CLOSED;

  /**
   * @return {@code true} if the {@link io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity}
   *         is ordered
   */
  public boolean isOpen() {

    return (this == OPEN);
  }

  /**
   * @return {@code true} if the {@link io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity}
   *         is closed (has reached its final state).
   */
  public boolean isClosed() {

    return ((this == CLOSED));
  }

}
