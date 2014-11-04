#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api.usecase;

import ${package}.offermanagement.logic.api.to.OfferEto;
import ${package}.salesmanagement.common.api.datatype.OrderPositionState;
import ${package}.salesmanagement.logic.api.to.OrderEto;
import ${package}.salesmanagement.logic.api.to.OrderPositionEto;

/**
 * Interface of UcManageOrderPosition to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageOrderPosition {

  /**
   * Creates an {@link OrderPositionEto} for the given {@link OfferEto} and a given {@link OrderEto}.
   *
   * @param offer is the {@link OfferEto} to be ordered.
   * @param order is the {@link OrderEto order} for which the {@link OrderPositionEto}s are
   * @param comment is the comment for special orders
   *
   * @return the new {@link OrderPositionEto}
   */
  OrderPositionEto createOrderPosition(OfferEto offer, OrderEto order, String comment);

  /**
   * Saves or updates the given {@link OrderPositionEto}.
   *
   * @param orderPosition is the {@link OrderPositionEto} to persist.
   * @return the saved {@link OrderPositionEto}.
   */
  OrderPositionEto updateOrderPosition(OrderPositionEto orderPosition);

  /**
   * Marks the given {@link OrderPositionEto} as 'chosen' {@link OrderPositionState}.
   *
   * @param orderPosition {@link OrderPositionEto}.
   * @param newState new {@link OrderPositionState}.
   */
  void markOrderPositionAs(OrderPositionEto orderPosition, OrderPositionState newState);

}
