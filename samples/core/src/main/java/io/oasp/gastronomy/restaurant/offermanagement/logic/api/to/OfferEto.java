package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.validation.NotNegativeMoney;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;

/**
 * The {@link io.oasp.module.basic.common.api.to.AbstractEto ETO} for an {@link Offer}.
 *
 */
public class OfferEto extends MenuItemEto implements Offer {

  private static final long serialVersionUID = 1L;

  private Long number;

  @NotNegativeMoney
  private Money currentPrice;

  private Long mealId;

  private Long drinkId;

  private Long sideDishId;

  private OfferState state;

  /**
   * The constructor.
   */
  public OfferEto() {

    super();
  }

  @Override
  public Long getNumber() {

    return this.number;
  }

  @Override
  public void setNumber(Long number) {

    this.number = number;
  }

  @Override
  public Money getPrice() {

    return this.currentPrice;
  }

  @Override
  public void setPrice(Money currentPrice) {

    this.currentPrice = currentPrice;
  }

  @Override
  public Long getMealId() {

    return this.mealId;
  }

  @Override
  public void setMealId(Long mealId) {

    this.mealId = mealId;
  }

  @Override
  public Long getDrinkId() {

    return this.drinkId;
  }

  @Override
  public void setDrinkId(Long drinkId) {

    this.drinkId = drinkId;
  }

  @Override
  public Long getSideDishId() {

    return this.sideDishId;
  }

  @Override
  public void setSideDishId(Long sideDishId) {

    this.sideDishId = sideDishId;
  }

  @Override
  public OfferState getState() {

    return this.state;
  }

  @Override
  public void setState(OfferState state) {

    this.state = state;
  }
}
