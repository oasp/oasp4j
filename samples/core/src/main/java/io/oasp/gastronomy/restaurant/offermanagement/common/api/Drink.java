package io.oasp.gastronomy.restaurant.offermanagement.common.api;

/**
 * This is the interface for a {@link Product} that represents a {@link Drink}.
 *
 */
public interface Drink extends Product {

  /**
   * @return {@code true} if this drink is containing alcohol, {@code false} otherwise.
   */
  boolean isAlcoholic();

  /**
   * @param alcoholic is the new value of {@link #isAlcoholic() alcoholic}.
   */
  void setAlcoholic(boolean alcoholic);

}
