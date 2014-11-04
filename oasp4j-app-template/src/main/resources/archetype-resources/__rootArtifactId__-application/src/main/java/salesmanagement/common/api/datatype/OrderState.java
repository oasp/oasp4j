#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.common.api.datatype;

/**
 * Represents the state of an {@link ${package}.salesmanagement.common.api.Order}.
 *
 * @author rjoeris
 */
public enum OrderState {

  /**
   * The state if the {@link ${package}.salesmanagement.common.api.Order} has at least one open
   * {@link ${package}.salesmanagement.common.api.OrderPosition}.
   */
  OPEN,

  /**
   * The state if the {@link ${package}.salesmanagement.common.api.Order} is closed. Then also all
   * {@link ${package}.salesmanagement.common.api.OrderPosition} have to be closed.
   */
  CLOSED;

  /**
   * @return <code>true</code> if the {@link ${package}.salesmanagement.persistence.api.OrderEntity}
   *         is ordered
   */
  public boolean isOpen() {

    return (this == OPEN);
  }

  /**
   * @return <code>true</code> if the {@link ${package}.salesmanagement.persistence.api.OrderEntity}
   *         is closed (has reached its final state).
   */
  public boolean isClosed() {

    return ((this == CLOSED));
  }

}
