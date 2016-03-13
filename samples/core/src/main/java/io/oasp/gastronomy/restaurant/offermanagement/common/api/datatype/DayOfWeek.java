package io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype;

import java.util.Calendar;

/**
 * Day of Week enum.
 *
 * @author mbrunnli
 */
public enum DayOfWeek {

  MONDAY(Calendar.MONDAY), TUESDAY(Calendar.TUESDAY), WEDNESDAY(Calendar.WEDNESDAY), THURSDAY(Calendar.THURSDAY), FRIDAY(
      Calendar.FRIDAY), SATURDAY(Calendar.SATURDAY), SUNDAY(Calendar.SUNDAY);

  /** {@link Calendar#DAY_OF_WEEK} representation */
  private int calendarRepresentation;

  private DayOfWeek(int calendarRepresentation) {

    this.calendarRepresentation = calendarRepresentation;
  }

  /**
   * Returns the {@link Calendar#DAY_OF_WEEK} representation.
   *
   * @return {@link Calendar#DAY_OF_WEEK} representation
   */
  public int toCalendarDayOfWeek() {

    return this.calendarRepresentation;
  }
}
