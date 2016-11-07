package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink;

/**
 * The {@link io.oasp.module.basic.common.api.to.AbstractEto ETO} for a {@link Drink}.
 *
 */
public class DrinkEto extends ProductEto implements Drink {

  private static final long serialVersionUID = 1L;

  private boolean alcoholic;

  /**
   * The constructor.
   */
  public DrinkEto() {

    super();
  }

  /**
   * Returns the field 'alcoholic'.
   *
   * @return Value of alcoholic
   */
  @Override
  public boolean isAlcoholic() {

    return this.alcoholic;
  }

  /**
   * Sets the field 'alcoholic'.
   *
   * @param alcoholic New value for alcoholic
   */
  @Override
  public void setAlcoholic(boolean alcoholic) {

    this.alcoholic = alcoholic;
  }

}
