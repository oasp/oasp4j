package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

import net.sf.mmm.util.transferobject.api.AbstractTransferObject;

/**
 * {@link AbstractTransferObject TO} to filter {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer}s.
 *
 * @author etomety
 */
public class OfferFilter extends AbstractTransferObject {

  private static final long serialVersionUID = 1L;

  private Long mealId;

  private Long sideDishId;

  private Long drinkId;

  private Money minPrice;

  private Money maxPrice;

  /**
   * The constructor.
   */
  public OfferFilter() {

    // initialize
    this.mealId = null;
    this.sideDishId = null;
    this.drinkId = null;
    this.minPrice = null;
    this.maxPrice = null;
  }

  /*
   * Constructor
   */
  /**
   * @param mealId Id of a meal as a Long
   * @param sideDishId Id of a Sidedish as a Long
   * @param drinkId Id of a Drink as a Long
   * @param minPrice minimal price for the filtered offers or null to ignore
   * @param maxPrice maximal price for the filtered offers or null to ignore
   */
  public OfferFilter(Long mealId, Long sideDishId, Long drinkId, Money minPrice, Money maxPrice) {

    this.mealId = mealId;
    this.sideDishId = sideDishId;
    this.drinkId = drinkId;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
  }

  /**
   * Returns the field 'mealId'.
   *
   * @return Value of mealId
   */
  public Long getMealId() {

    return this.mealId;
  }

  /**
   * Returns the field 'sideDishId'.
   *
   * @return Value of sideDishId
   */
  public Long getSideDishId() {

    return this.sideDishId;
  }

  /**
   * Returns the field 'drinkId'.
   *
   * @return Value of drinkId
   */
  public Long getDrinkId() {

    return this.drinkId;
  }

  /**
   * Returns the field 'minPrice'.
   *
   * @return Value of minPrice
   */
  public Money getMinPrice() {

    return this.minPrice;
  }

  /**
   * Returns the field 'maxPrice'.
   *
   * @return Value of maxPrice
   */
  public Money getMaxPrice() {

    return this.maxPrice;
  }

  /**
   * Sets the field 'mealId'.
   *
   * @param mealId New value for mealId
   */
  public void setMealId(Long mealId) {

    this.mealId = mealId;
  }

  /**
   * Sets the field 'sideDishId'.
   *
   * @param sideDishId New value for sideDishId
   */
  public void setSideDishId(Long sideDishId) {

    this.sideDishId = sideDishId;
  }

  /**
   * Sets the field 'drinkId'.
   *
   * @param drinkId New value for drinkId
   */
  public void setDrinkId(Long drinkId) {

    this.drinkId = drinkId;
  }

  /**
   * Sets the field 'minPrice'.
   *
   * @param minPrice New value for minPrice
   */
  public void setMinPrice(Money minPrice) {

    this.minPrice = minPrice;
  }

  /**
   * Sets the field 'maxPrice'.
   *
   * @param maxPrice New value for maxPrice
   */
  public void setMaxPrice(Money maxPrice) {

    this.maxPrice = maxPrice;
  }

}
