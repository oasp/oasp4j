#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.common.api.datatype;

/**
 * Represents the {@link ${package}.tablemanagement.common.api.Table${symbol_pound}getState() state} of a
 * {@link ${package}.tablemanagement.common.api.Table}.
 *
 * @author etomety
 */
public enum TableState {
  /** The state of a free {@link ${package}.tablemanagement.common.api.Table}. */
  FREE,

  /** The state of a reserved {@link ${package}.tablemanagement.common.api.Table}. */
  RESERVED,

  /** The state of a occupied {@link ${package}.tablemanagement.common.api.Table}. */
  OCCUPIED;

  /**
   * @return <code>true</code> if {@link ${symbol_pound}FREE}, <code>false</code> otherwise.
   */
  public boolean isFree() {

    return (this == FREE);
  }

  /**
   * @return <code>true</code> if {@link ${symbol_pound}RESERVED}, <code>false</code> otherwise.
   */
  public boolean isReserved() {

    return (this == RESERVED);
  }

  /**
   * @return <code>true</code> if {@link ${symbol_pound}OCCUPIED}, <code>false</code> otherwise.
   */
  public boolean isOccupied() {

    return (this == OCCUPIED);
  }

  // /**
  // * @return <code>true</code> if the
  // * {@link ${package}.tablemanagement.persistence.api.entity.Table} has opened orders.
  // */
  // public boolean isOrdersopen() {
  //
  // return (this == ORDERSOPEN);
  // }
}
