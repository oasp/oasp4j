package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationDao;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link OrderPositionEntity} entity.
 *
 */
public interface OrderPositionDao extends ApplicationDao<OrderPositionEntity> {

  /**
   * @param orderId is the {@link io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto#getId() ID} for
   *        which the {@link OrderPositionEntity}s are requested.
   * @return the {@link List} of all {@link OrderPositionEntity}s {@link OrderPositionEntity#getOrderId() associated}
   *         with the given <code>orderId</code>.
   */
  List<OrderPositionEntity> findOrderPositionsByOrder(Long orderId);

  /**
   * @param orderId is the {@link io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto#getId() table ID}
   *        for which the {@link OrderPositionEntity}s are requested.
   * @return the {@link List} of all {@link OrderPositionEntity}s that are NOT
   *         {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState#isClosed()
   *         closed} and are {@link io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto#getId() table
   *         ID} with the given {@link io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto}. Will be the
   *         empty {@link List} if no such {@link OrderPositionEntity} exists.
   */
  List<OrderPositionEntity> findOpenOrderPositionsByOrder(Long orderId);

  /**
   * Finds the {@link OrderPositionEntity order positions} matching the given {@link OrderPositionSearchCriteriaTo}.
   *
   * @param criteria is the {@link OrderPositionSearchCriteriaTo}.
   * @return the {@link List} with the matching {@link OrderPositionEntity} objects.
   */
  List<OrderPositionEntity> findOrderPositions(OrderPositionSearchCriteriaTo criteria);
}
