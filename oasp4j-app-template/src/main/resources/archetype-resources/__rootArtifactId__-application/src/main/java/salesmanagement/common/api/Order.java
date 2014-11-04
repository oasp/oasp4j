#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.common.api;

import ${package}.general.common.api.ApplicationEntity;
import ${package}.salesmanagement.common.api.datatype.OrderState;

/**
 * This is the interface for an {@link Order}. It is {@link ${symbol_pound}getTableId() associated} with a
 * {@link ${package}.tablemanagement.common.api.Table} and consists of multiple {@link OrderPosition
 * positions}.
 *
 * @author hohwille
 */
public interface Order extends ApplicationEntity {

  /**
   * @return the {@link ${package}.tablemanagement.common.api.Table${symbol_pound}getId() ID} of the associated
   *         {@link ${package}.tablemanagement.common.api.Table} where this {@link Order} was
   *         disposed.
   */
  long getTableId();

  /**
   * @param tableId the new {@link ${symbol_pound}getTableId() tableId}.
   */
  void setTableId(long tableId);

  /**
   * @return the {@link OrderState state} of this {@link Order}.
   */
  OrderState getState();

  /**
   * @param state is the new {@link ${symbol_pound}getState() state}.
   */
  void setState(OrderState state);
}
