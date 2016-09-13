package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;

/**
 * This is the {@link SearchCriteriaTo search criteria} {@link net.sf.mmm.util.transferobject.api.TransferObject TO}
 * used to find {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order}s.
 *
 */
public class OfferSearchCriteriaTo extends SearchCriteriaTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private Long number;

  private Money minPrice;

  private Money maxPrice;

  private Long mealId;

  private Long drinkId;

  private Long sideDishId;

  private OfferState state;

  /**
   * The constructor.
   */
  public OfferSearchCriteriaTo() {

    super();
  }

  /**
   * @return number
   */
  public Long getNumber() {

    return this.number;
  }

  /**
   * @param number the number to set
   */
  public void setNumber(Long number) {

    this.number = number;
  }

  /**
   * @return minPrice
   */
  public Money getMinPrice() {

    return this.minPrice;
  }

  /**
   * @param minPrice the minPrice to set
   */
  public void setMinPrice(Money minPrice) {

    this.minPrice = minPrice;
  }

  /**
   * @return maxPrice
   */
  public Money getMaxPrice() {

    return this.maxPrice;
  }

  /**
   * @param maxPrice the maxPrice to set
   */
  public void setMaxPrice(Money maxPrice) {

    this.maxPrice = maxPrice;
  }

  /**
   * @return mealId
   */
  public Long getMealId() {

    return this.mealId;
  }

  /**
   * @param mealId the mealId to set
   */
  public void setMealId(Long mealId) {

    this.mealId = mealId;
  }

  /**
   * @return drinkId
   */
  public Long getDrinkId() {

    return this.drinkId;
  }

  /**
   * @param drinkId the drinkId to set
   */
  public void setDrinkId(Long drinkId) {

    this.drinkId = drinkId;
  }

  /**
   * @return sideDishId
   */
  public Long getSideDishId() {

    return this.sideDishId;
  }

  /**
   * @param sideDishId the sideDishId to set
   */
  public void setSideDishId(Long sideDishId) {

    this.sideDishId = sideDishId;
  }

  /**
   * @return state
   */
  public OfferState getState() {

    return this.state;
  }

  /**
   * @param state the state to set
   */
  public void setState(OfferState state) {

    this.state = state;
  }

}
