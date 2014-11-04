#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api.usecase;

import ${package}.salesmanagement.logic.api.to.OrderCto;
import ${package}.salesmanagement.logic.api.to.OrderEto;
import ${package}.salesmanagement.logic.api.to.OrderSearchCriteriaTo;

import java.util.List;

/**
 * Interface of {@link ${package}.general.logic.base.AbstractUc use case} to get specific or all
 * {@link OrderEto orders}.
 *
 * @author mvielsac
 */
public interface UcFindOrder {

  /**
   * @param criteria the {@link OrderSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OrderEto}s.
   */
  List<OrderEto> findOrderEtos(OrderSearchCriteriaTo criteria);

  /**
   * @param criteria the {@link OrderSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OrderCto}s.
   */
  List<OrderCto> findOrderCtos(OrderSearchCriteriaTo criteria);

  /**
   * @param order the {@link OrderEto}.
   * @return the corresponding {@link OrderCto} (order with order-positions).
   */
  OrderCto findOrderCto(OrderEto order);

  /**
   * This method returns an {@link OrderEto order}.
   *
   * @param orderId identifier of the searched {@link OrderEto order}
   * @return the {@link OrderEto order} with the given identifier. Will be <code>null</code> if the {@link OrderEto
   *         order} does not exist.
   */
  OrderEto findOrder(Long orderId);

  /**
   * This method returns a the {@link ${package}.salesmanagement.common.api.datatype.OrderState${symbol_pound}OPEN
   * open} {@link OrderEto order} for the specified table.
   *
   * @param tableId the {@link ${package}.tablemanagement.common.api.Table${symbol_pound}getId() table ID} the
   *        requested order shall be {@link OrderEto${symbol_pound}getTableId() associated} with.
   * @return the {@link OrderEto order} {@link OrderEto${symbol_pound}getTableId() associated} with the given <code>tableId</code> in
   *         {@link ${package}.salesmanagement.common.api.datatype.OrderState${symbol_pound}OPEN open}
   *         {@link OrderEto${symbol_pound}getState() state} or <code>null</code> if no such {@link OrderEto order} exists.
   */
  OrderEto findOpenOrderForTable(Long tableId);

}
