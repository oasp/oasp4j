package io.oasp.gastronomy.restaurant.tablemanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * This is the interface for a table of the restaurant. It has a unique {@link #getNumber() number} can be
 * {@link TableState#isReserved() reserved}, {@link TableState#isOccupied() occupied} and may have a
 * {@link #getWaiterId() waiter} assigned.
 *
 */
public interface Table extends ApplicationEntity {

  /**
   * @return the unique table number.
   */
  @NotNull
  @Min(0)
  Long getNumber();

  /**
   * @param number is the new {@link #getNumber() number}.
   */
  void setNumber(Long number);

  /**
   * @return the current {@link TableState state} of this {@link Table}.
   */
  TableState getState();

  /**
   * @param state is the new {@link #getState() state}.
   */
  void setState(TableState state);

  /**
   * @return the {@link io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember#getId() ID} of the waiter
   *         currently responsible for this table.
   */
  Long getWaiterId();

  /**
   * Sets the field 'waiterId'.
   *
   * @param waiterId New value for waiterId
   */
  void setWaiterId(Long waiterId);

}
