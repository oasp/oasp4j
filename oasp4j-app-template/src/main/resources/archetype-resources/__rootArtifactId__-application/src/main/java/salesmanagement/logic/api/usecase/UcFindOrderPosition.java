#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api.usecase;

import ${package}.salesmanagement.logic.api.to.OrderPositionEto;

import java.util.List;

/**
 * Interface of {@link ${package}.general.logic.base.AbstractUc use case} to get or find specific
 * {@link OrderPositionEto order position}s.
 *
 * @author mvielsac
 */
public interface UcFindOrderPosition {
  /**
   * @param orderPositionId is the {@link OrderPositionEto${symbol_pound}getId() ID} of the requested {@link OrderPositionEto}.
   * @return the {@link OrderPositionEto} with the given ID. Will be <code>null</code> if the {@link OrderPositionEto}
   *         does not exist.
   */
  OrderPositionEto findOrderPosition(Long orderPositionId);

  /**
   * @param orderId is the {@link ${package}.salesmanagement.common.api.Order${symbol_pound}getId() ID} of the
   *        {@link ${package}.salesmanagement.common.api.Order} for which the
   *        {@link OrderPositionEto positions} are requested.
   * @return the {@link List} of {@link OrderPositionEto}s {@link OrderPositionEto${symbol_pound}getOrderId() associated} with the
   *         given <code>orderId</code>.
   */
  List<OrderPositionEto> findOrderPositionsByOrderId(Long orderId);

  /**
   * @param orderId for which the {@link OrderPositionEto}s are requested.
   * @return the {@link List} of all {@link OrderPositionEto}s {@link OrderPositionEto${symbol_pound}getId() associated} with the
   *         given <code>orderId</code>. Will be the empty {@link List} if no such {@link OrderPositionEto} exists.
   */
  List<OrderPositionEto> findOpenOrderPositionsByOrderId(Long orderId);

}
