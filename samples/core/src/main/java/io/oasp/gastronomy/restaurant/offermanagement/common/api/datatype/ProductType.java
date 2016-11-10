package io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype;


/**
 * This enum contains the available types of a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product}.
 *
 */
public enum ProductType {

  /**
   * This value identifies a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink}.
   */
  DRINK,

  /**
   * This value identifies a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Meal}.
   */
  MEAL,

  /**
   * This value identifies a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.SideDish}.
   */
  SIDEDISH;

  /**
   * @return {@code true}, if this value equals {@link ProductType#DRINK} . {@code false} otherwise.
   */
  public boolean isDrink() {

    return equals(DRINK);
  }

  /**
   * @return {@code true}, if this value equals {@link ProductType#MEAL}. {@code false} otherwise.
   */
  public boolean isMeal() {

    return equals(MEAL);
  }

  /**
   * @return {@code true}, if this value equals {@link ProductType#SIDEDISH}. {@code false} otherwise.
   */
  public boolean isSideDish() {

    return equals(SIDEDISH);
  }

}
