package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Special;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link Special}.
 *
 * @author fkreis
 */
@Entity(name = "Special")
@Table()
public class SpecialEntity extends MenuItemEntity {

  private static final long serialVersionUID = 1L;

  private OfferEntity offer;

  private int startingWeekDay;

  private int startingHour;

  private int endgingWeekDay;

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
   * @return endgingWeekDay
   */
  public int getEndgingWeekDay() {

    return this.endgingWeekDay;
  }

  /**
   * @param endgingWeekDay new value of {@link #getEndgingWeekDay}.
   */
  public void setEndgingWeekDay(int endgingWeekDay) {

    this.endgingWeekDay = endgingWeekDay;
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

  private int endingHour;

  private Money specialPrice;

}