#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.common.api;

import ${package}.general.common.api.datatype.Money;
import ${package}.offermanagement.common.api.datatype.OfferState;

/**
 * This is the interface for an {@link Offer} that combines {@link Product}s with a price for the menu of the
 * restaurant. The combined {@link Product}s are a {@link Meal}, a {@link SideDish}, and a {@link Drink}. All of them
 * are optional but at least one of the three has to be present in a valid {@link Offer}.
 *
 * @author hohwille
 */
public interface Offer extends MenuItem {

  /**
   * @return the unique number of this {@link Offer} used in the menu. While the {@link ${symbol_pound}getId() ID} of an {@link Offer}
   *         is auto-generated and will never change, the number may be changed if the menu gets updated. This is the
   *         identifier used by customers to displace orders.
   */
  Long getNumber();

  /**
   * @param number is the new {@link ${symbol_pound}getNumber() number}.
   */
  void setNumber(Long number);

  /**
   * Returns the field 'currentPrice'.
   *
   * @return Value of currentPrice
   */
  Money getCurrentPrice();

  /**
   * @param currentPrice is the new {@link ${symbol_pound}getCurrentPrice() price}.
   */
  void setCurrentPrice(Money currentPrice);

  /**
   * @return is the {@link Meal${symbol_pound}getId() ID} of the {@link Meal} or <code>null</code> if no {@link Meal} is contained in
   *         this {@link Offer}.
   */
  Long getMealId();

  /**
   * @param mealId is the new {@link ${symbol_pound}getMealId() mealId}.
   */
  void setMealId(Long mealId);

  /**
   * @return is the {@link Drink${symbol_pound}getId() ID} of the {@link Drink} or <code>null</code> if no {@link Drink} is contained
   *         in this {@link Offer}.
   */
  Long getDrinkId();

  /**
   * @param drinkId is the new {@link ${symbol_pound}getDrinkId() drinkId}.
   */
  void setDrinkId(Long drinkId);

  /**
   * @return is the {@link SideDish${symbol_pound}getId() ID} of the {@link SideDish} or <code>null</code> if no {@link SideDish} is
   *         contained in this {@link Offer}.
   */
  Long getSideDishId();

  /**
   * @param sideDishId is the new {@link ${symbol_pound}getSideDishId() sideDishId}.
   */
  void setSideDishId(Long sideDishId);

  /**
   * @return the {@link OfferState state} of this {@link Offer}.
   */
  OfferState getState();

  /**
   * @param state is the new {@link ${symbol_pound}getState() state}.
   */
  void setState(OfferState state);
}
