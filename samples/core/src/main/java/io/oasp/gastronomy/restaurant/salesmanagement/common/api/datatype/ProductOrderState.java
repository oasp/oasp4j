package io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype;

/**
 * Represents the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition#getDrinkState() state}
 * of an {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}.
 *
 */
public enum ProductOrderState {

  /**
   * The initial state of a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink }.
   */
  ORDERED,

  /**
   * The state of a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} that has been prepared by the
   * bar keeper and can be served by the waiter.
   */
  PREPARED,

  /**
   * The state of a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} that has been delivered to
   * the table by the waiter.
   */
  DELIVERED;

  /**
   * @return <code>true</code> if the {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity}
   *         is ordered
   */
  public boolean isOrdered() {

    return (this == ORDERED);
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
   *         is delivered.
   */
  public boolean isDelivered() {

    return (this == DELIVERED);
  }

}