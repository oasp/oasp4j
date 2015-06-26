package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;

/**
 * The {@link ApplicationPersistenceEntity persistent entity} for a special.
 *
 * @author mbrunnli
 */
public class SpecialEntity {

  private OfferEntity offer;

  private WeeklyPeriodEntity activePeriod;

  private Money specialPrice;

  /**
   * Returns the {@link Offer} this special applies for.
   *
   * @return offer {@link Offer} this special applies for.
   */
  public OfferEntity getOffer() {

    return this.offer;
  }

  /**
   * Sets the {@link Offer} this special applies for.
   *
   * @param offer the {@link Offer} this special applies for.
   */
  public void setOffer(OfferEntity offer) {

    this.offer = offer;
  }

  /**
   * Returns the {@link WeeklyPeriodEntity active period} this special applies for.
   *
   * @return activePeriod the {@link WeeklyPeriodEntity active period} this special applies for.
   */
  public WeeklyPeriodEntity getActivePeriod() {

    return this.activePeriod;
  }

  /**
   * Sets the {@link WeeklyPeriodEntity active period} this special applies for.
   *
   * @param activePeriod the {@link WeeklyPeriodEntity active period} this special applies for.
   */
  public void setActivePeriod(WeeklyPeriodEntity activePeriod) {

    this.activePeriod = activePeriod;
  }

  /**
   * Returns the new {@link Money special price} for the {@link Offer}.
   * 
   * @return specialPrice the new {@link Money special price} for the {@link Offer}.
   */
  public Money getSpecialPrice() {

    return this.specialPrice;
  }

  /**
   * Sets the new {@link Money special price} for the {@link Offer}.
   * 
   * @param specialPrice the new {@link Money special price} for the {@link Offer}.
   */
  public void setSpecialPrice(Money specialPrice) {

    this.specialPrice = specialPrice;
  }

}
