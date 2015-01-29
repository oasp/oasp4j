package io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype;

/**
 * Represents the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition#getDrinkState() state}
 * of an {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}.
 *
 * @author oelsabba
 */
public enum DrinkState {

  /**
   * The initial state of an {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink }.
   */
  ORDERED,

  /**
   * The state of an {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} that has been prepared by
   * the bar keeper and can be served by the waiter.
   */
  PREPARED,

  /**
   * The state of an {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} that has been delivered to
   * the table by the waiter.
   */
  DELIVERED,

  /**
   * The state of an {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} that has been payed by the
   * customer.
   */
  PAYED,

  /**
   * The state of an {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} that has been cancelled.
   */
  CANCELLED;

  /**
   * @return <code>true</code> if the {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity}
   *         is ordered
   */
  public boolean isOrdered() {

    return (this == ORDERED);
  }

  /**
   * @return <code>true</code> if the {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity}
   *         is closed (has reached its final state).
   */
  public boolean isClosed() {

    return ((this == PAYED) || (this == CANCELLED));
  }

  /**
   * @return <code>true</code> if the {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity}
   *         is delivered.
   */
  public boolean isDelivered() {

    return (this == DELIVERED);
  }

  /**
   * @return <code>true</code> if the {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity}
   *         is prepared
   */
  public boolean isPrepared() {

    return (this == PREPARED);
  }

  /**
   * @return <code>true</code> if the {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity}
   *         is payed
   */
  public boolean isPayed() {

    return (this == PAYED);
  }

}