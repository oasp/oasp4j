package io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype;

/**
 * Represents the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition#getState() state} of an
 * {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}.
 *
 */
public enum OrderPositionState {

  /**
   * The initial state of an {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}.
   */
  ORDERED,

  /**
   * The state of an {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition} that has been
   * prepared by the kitchen and can be served by the waiter.
   */
  PREPARED,

  /**
   * The state of an {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition} that has been
   * delivered been delivered to the table by the waiter.
   */
  DELIVERED,

  /**
   * The state of an {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition} that has been payed
   * by the customer.
   */
  PAYED,

  /**
   * The state of an {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition} that has been
   * cancelled.
   */
  CANCELLED;

  /**
   * @return {@code true} if the
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity} is ordered
   */
  public boolean isOrdered() {

    return (this == ORDERED);
  }

  /**
   * @return {@code true} if the
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity} is closed (has
   *         reached its final state).
   */
  public boolean isClosed() {

    return ((this == PAYED) || (this == CANCELLED));
  }

  /**
   * @return {@code true} if the
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity} is delivered.
   */
  public boolean isDelivered() {

    return (this == DELIVERED);
  }

  /**
   * @return {@code true} if the
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity} is prepared
   */
  public boolean isPrepared() {

    return (this == PREPARED);
  }

  /**
   * @return {@code true} if the
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity} is payed
   */
  public boolean isPayed() {

    return (this == PAYED);
  }

}
