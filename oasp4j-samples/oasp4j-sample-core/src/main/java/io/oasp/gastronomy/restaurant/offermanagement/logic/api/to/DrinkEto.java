package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto ETO} for a {@link Drink}.
 *
 * @author jozitz
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
  public boolean isAlcoholic() {

    return this.alcoholic;
  }

  /**
   * Sets the field 'alcoholic'.
   *
   * @param alcoholic New value for alcoholic
   */
  public void setAlcoholic(boolean alcoholic) {

    this.alcoholic = alcoholic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (this.alcoholic ? 1 : 0);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    DrinkEto other = (DrinkEto) obj;
    if (this.alcoholic != other.alcoholic) {
      return false;
    }
    return true;
  }

}
