package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.module.basic.common.api.to.AbstractCto;

/**
 * The {@link AbstractCto CTO} for an {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer}.
 *
 */
public class OfferCto extends AbstractCto {

  private static final long serialVersionUID = 1L;

  private OfferEto offer;

  private MealEto meal;

  private DrinkEto drink;

  private SideDishEto sideDish;

  /**
   * The constructor.
   */
  public OfferCto() {

  }

  /**
   * @return offer
   */
  public OfferEto getOffer() {

    return this.offer;
  }

  /**
   * @param offer the offer to set
   */
  public void setOffer(OfferEto offer) {

    this.offer = offer;
  }

  /**
   * @return meal
   */
  public MealEto getMeal() {

    return this.meal;
  }

  /**
   * @param meal the meal to set
   */
  public void setMeal(MealEto meal) {

    this.meal = meal;
  }

  /**
   * @return drink
   */
  public DrinkEto getDrink() {

    return this.drink;
  }

  /**
   * @param drink the drink to set
   */
  public void setDrink(DrinkEto drink) {

    this.drink = drink;
  }

  /**
   * @return sideDish
   */
  public SideDishEto getSideDish() {

    return this.sideDish;
  }

  /**
   * @param sideDish the sideDish to set
   */
  public void setSideDish(SideDishEto sideDish) {

    this.sideDish = sideDish;
  }

}
