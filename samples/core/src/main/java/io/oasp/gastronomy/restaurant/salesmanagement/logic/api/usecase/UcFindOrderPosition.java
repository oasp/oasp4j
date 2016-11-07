package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;

import java.util.List;

/**
 * Interface of {@link io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc use case} to get or find specific
 * {@link OrderPositionEto order position}s.
 *
 */
public interface UcFindOrderPosition {

  /**
   * @param orderPositionId is the {@link OrderPositionEto#getId() ID} of the requested {@link OrderPositionEto}.
   * @return the {@link OrderPositionEto} with the given ID. Will be {@code null} if the {@link OrderPositionEto} does
   *         not exist.
   */
  OrderPositionEto findOrderPosition(long orderPositionId);

  /**
   * @param orderId is the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order#getId() ID} of the
   *        {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order} for which the
   *        {@link OrderPositionEto positions} are requested.
   * @return the {@link List} of {@link OrderPositionEto}s {@link OrderPositionEto#getOrderId() associated} with the
   *         given <code>orderId</code>.
   */
  List<OrderPositionEto> findOrderPositionsByOrderId(long orderId);

  /**
   * @param criteria the {@link OrderPositionSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OrderPositionEto}s.
   */
  List<OrderPositionEto> findOrderPositions(OrderPositionSearchCriteriaTo criteria);

  /**
   * @param orderId for which the {@link OrderPositionEto}s are requested.
   * @return the {@link List} of all {@link OrderPositionEto}s {@link OrderPositionEto#getId() associated} with the
   *         given <code>orderId</code>. Will be the empty {@link List} if no such {@link OrderPositionEto} exists.
   */
  List<OrderPositionEto> findOpenOrderPositionsByOrderId(long orderId);

}
