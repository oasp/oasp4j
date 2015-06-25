package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.DayOfWeek;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Weekly period describing a starting and an ending point. Each is defined as a fixed hour (24h-format) at a specific
 * day of week.
 *
 * @author mbrunnli
 */
public class WeeklySchedulePeriod {

  private DayOfWeek startingDay;

  private int startingHour;

  private DayOfWeek endingDay;

  private int endingHour;

  /**
   * @return startingDay
   */
  public DayOfWeek getStartingDay() {

    return this.startingDay;
  }

  /**
   * @param startingDay new value of {@link #getStartingDay}.
   */
  public void setStartingDay(DayOfWeek startingDay) {

    this.startingDay = startingDay;
  }

  /**
   * @return startingHour
   */
  @Max(24)
  @Min(0)
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
   * @return endingDay
   */
  public DayOfWeek getEndingDay() {

    return this.endingDay;
  }

  /**
   * @param endingDay new value of {@link #getEndingDay}.
   */
  public void setEndingDay(DayOfWeek endingDay) {

    this.endingDay = endingDay;
  }

  /**
   * @return endingHour
   */
  @Max(24)
  @Min(0)
  public int getEndingHour() {

    return this.endingHour;
  }

  /**
   * @param endingHour new value of {@link #getEndingHour}.
   */
  public void setEndingHour(int endingHour) {

    this.endingHour = endingHour;
  }

}
