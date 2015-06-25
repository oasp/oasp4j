package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link Special}.
 *
 * @author fkreis
 */
public class SpecialEntity {

  private OfferEntity offer;

  private int startingWeekDay;

  private int startingHour;

  private int endingWeekDay;

  private int endingHour;

  private Money specialPrice;

  /**
   * @return offer
   */
  public OfferEntity getOffer() {

    return this.offer;
  }

  /**
   * @param offer new value of {@link #getOffer}.
   */
  public void setOffer(OfferEntity offer) {

    this.offer = offer;
  }

  /**
   * @return startingWeekDay
   */
  public int getStartingWeekDay() {

    return this.startingWeekDay;
  }

  /**
   * @param startingWeekDay new value of {@link #getStartingWeekDay}.
   */
  public void setStartingWeekDay(int startingWeekDay) {

    this.startingWeekDay = startingWeekDay;
  }

  /**
   * @return startingHour
   */
  public int getStartingHour() {

    return this.startingHour;
  }

  /**
   * @param startingHour new value of {@link #getStartingHour}.
   */
  public void setStartingHour(int startingHour) {

    this.startingHour = startingHour;
  }

  /**
   * @return endingWeekDay
   */
  public int getEndingWeekDay() {

    return this.endingWeekDay;
  }

  /**
   * @param endingWeekDay new value of {@link #getEndingWeekDay}.
   */
  public void setEndingWeekDay(int endingWeekDay) {

    this.endingWeekDay = endingWeekDay;
  }

  /**
   * @return endingHour
   */
  public int getEndingHour() {

    return this.endingHour;
  }

  /**
   * @param endingHour new value of {@link #getEndingHour}.
   */
  public void setEndingHour(int endingHour) {

    this.endingHour = endingHour;
  }

  /**
   * @return specialPrice
   */
  public Money getSpecialPrice() {

    return this.specialPrice;
  }

  /**
   * @param specialPrice new value of {@link #getSpecialPrice}.
   */
  public void setSpecialPrice(Money specialPrice) {

    this.specialPrice = specialPrice;
  }

}
