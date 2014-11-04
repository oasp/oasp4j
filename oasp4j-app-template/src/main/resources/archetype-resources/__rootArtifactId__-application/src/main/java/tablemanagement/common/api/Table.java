#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.common.api;

import ${package}.general.common.api.ApplicationEntity;
import ${package}.tablemanagement.common.api.datatype.TableState;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This is the interface for a table of the restaurant. It has a unique {@link ${symbol_pound}getNumber() number} can be
 * {@link TableState${symbol_pound}isReserved() reserved}, {@link TableState${symbol_pound}isOccupied() occupied} and may have a
 * {@link ${symbol_pound}getWaiterId() waiter} assigned.
 *
 * @author hohwille
 */
public interface Table extends ApplicationEntity {

  /**
   * @return the unique table number.
   */
  @NotNull
  @Size(min = 1)
  Long getNumber();

  /**
   * @param number is the new {@link ${symbol_pound}getNumber() number}.
   */
  void setNumber(Long number);

  /**
   * @return the current {@link TableState state} of this {@link Table}.
   */
  TableState getState();

  /**
   * @param state is the new {@link ${symbol_pound}getState() state}.
   */
  void setState(TableState state);

  /**
   * @return the {@link ${package}.staffmanagement.common.api.StaffMember${symbol_pound}getId() ID} of the waiter
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
