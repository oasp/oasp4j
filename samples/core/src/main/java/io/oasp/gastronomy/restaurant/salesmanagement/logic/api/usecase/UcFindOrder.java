package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

/**
 * Interface of {@link io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc use case} to get specific or all
 * {@link OrderEto orders}.
 *
 */
public interface UcFindOrder {

  /**
   * @param criteria the {@link OrderSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OrderEto}s.
   */
  PaginatedListTo<OrderEto> findOrderEtos(OrderSearchCriteriaTo criteria);

  /**
   * @param criteria the {@link OrderSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OrderCto}s.
   */
  PaginatedListTo<OrderCto> findOrderCtos(OrderSearchCriteriaTo criteria);

  /**
   * @param order the {@link OrderEto}.
   * @return the corresponding {@link OrderCto} (order with order-positions).
   */
  OrderCto findOrderCto(OrderEto order);

  /**
   * This method returns an {@link OrderEto order}.
   *
   * @param orderId identifier of the searched {@link OrderEto order}
   * @return the {@link OrderEto order} with the given identifier. Will be {@code null} if the {@link OrderEto order}
   *         does not exist.
   */
  OrderEto findOrder(long orderId);

  /**
   * This method returns a the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState#OPEN
   * open} {@link OrderEto order} for the specified table.
   *
   * @param tableId the {@link io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table#getId() table ID} the
   *        requested order shall be {@link OrderEto#getTableId() associated} with.
   * @return the {@link OrderEto order} {@link OrderEto#getTableId() associated} with the given <code>tableId</code> in
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState#OPEN open}
   *         {@link OrderEto#getState() state} or {@code null} if no such {@link OrderEto order} exists.
   */
  OrderEto findOpenOrderForTable(long tableId);

}
