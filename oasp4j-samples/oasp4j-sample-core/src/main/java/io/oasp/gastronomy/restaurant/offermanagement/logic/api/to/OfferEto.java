package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.validation.NotNegativeMoney;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;

import java.util.Objects;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto ETO} for an {@link Offer}.
 *
 * @author jozitz
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

  @Override
  public int hashCode() {

    final Integer prime = 31;
    Integer result = super.hashCode();
    result = prime * result + ((this.number == null) ? 0 : this.number.hashCode());
    result = prime * result + ((this.currentPrice == null) ? 0 : this.currentPrice.hashCode());
    result = prime * result + ((this.drinkId == null) ? 0 : this.drinkId.hashCode());
    result = prime * result + ((this.mealId == null) ? 0 : this.mealId.hashCode());
    result = prime * result + ((this.sideDishId == null) ? 0 : this.sideDishId.hashCode());
    result = prime * result + ((this.state == null) ? 0 : this.state.ordinal());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    OfferEto other = (OfferEto) obj;
    if (!Objects.equals(this.number, other.number)) {
      return false;
    }
    if (!Objects.equals(this.currentPrice, other.currentPrice)) {
      return false;
    }
    if (!Objects.equals(this.drinkId, other.drinkId)) {
      return false;
    }
    if (!Objects.equals(this.mealId, other.mealId)) {
      return false;
    }
    if (!Objects.equals(this.sideDishId, other.sideDishId)) {
      return false;
    }
    if (this.state != other.state) {
      return false;
    }
    return true;
  }

}
