package io.oasp.gastronomy.restaurant.offermanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;

/**
 * This is the interface for an {@link Offer} that combines {@link Product}s with a price for the menu of the
 * restaurant. The combined {@link Product}s are a {@link Meal}, a {@link SideDish}, and a {@link Drink}. All of them
 * are optional but at least one of the three has to be present in a valid {@link Offer}.
 *
 */
public interface Offer extends MenuItem {

  /**
   * @return the unique number of this {@link Offer} used in the menu. While the {@link #getId() ID} of an {@link Offer}
   *         is auto-generated and will never change, the number may be changed if the menu gets updated. This is the
   *         identifier used by customers to displace orders.
   */
  Long getNumber();

  /**
   * @param number is the new {@link #getNumber() number}.
   */
  void setNumber(Long number);

  /**
   * @return the current price of the offer.
   */
  Money getPrice();

  /**
   * @param price is the new {@link #getPrice() price}.
   */
  void setPrice(Money price);

  /**
   * @return is the {@link Meal#getId() ID} of the {@link Meal} or {@code null} if no {@link Meal} is contained in
   *         this {@link Offer}.
   */
  Long getMealId();

  /**
   * @param mealId is the new {@link #getMealId() mealId}.
   */
  void setMealId(Long mealId);

  /**
   * @return is the {@link Drink#getId() ID} of the {@link Drink} or {@code null} if no {@link Drink} is contained
   *         in this {@link Offer}.
   */
  Long getDrinkId();

  /**
   * @param drinkId is the new {@link #getDrinkId() drinkId}.
   */
  void setDrinkId(Long drinkId);

  /**
   * @return is the {@link SideDish#getId() ID} of the {@link SideDish} or {@code null} if no {@link SideDish} is
   *         contained in this {@link Offer}.
   */
  Long getSideDishId();

  /**
   * @param sideDishId is the new {@link #getSideDishId() sideDishId}.
   */
  void setSideDishId(Long sideDishId);

  /**
   * @return the {@link OfferState state} of this {@link Offer}.
   */
  OfferState getState();

  /**
   * @param state is the new {@link #getState() state}.
   */
  void setState(OfferState state);
}
