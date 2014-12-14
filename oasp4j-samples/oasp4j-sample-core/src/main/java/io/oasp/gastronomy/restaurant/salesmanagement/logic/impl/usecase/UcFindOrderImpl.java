package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractOrderUc;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use Case to find an order.
 *
 * @author rjoeris
 */
@Named
public class UcFindOrderImpl extends AbstractOrderUc implements UcFindOrder {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindOrderImpl.class);

  private Salesmanagement salesManagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto findOrder(Long orderId) {

    LOG.debug("Get order.");
    return getBeanMapper().map(getOrderDao().findOne(orderId), OrderEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderEto> findOrderEtos(OrderSearchCriteriaTo criteria) {

    criteria.limitMaximumHitCount(MAXIMUM_HIT_LIMIT);
    List<OrderEntity> orders = getOrderDao().findOrders(criteria);
    return getBeanMapper().mapList(orders, OrderEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderCto> findOrderCtos(OrderSearchCriteriaTo criteria) {

    List<OrderEto> orderEtos = findOrderEtos(criteria);
    List<OrderCto> result = new ArrayList<>(orderEtos.size());
    for (OrderEto order : orderEtos) {
      // REVIEW <who> (hohwille) N+1 problem. Find a better and more efficient way to load the order positions.
      result.add(findOrderCto(order));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderCto findOrderCto(OrderEto order) {

    OrderCto result = new OrderCto();
    result.setOrder(order);
    List<OrderPositionEto> positions = this.salesManagement.findOrderPositionsByOrderId(order.getId());
    result.setPositions(positions);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto findOpenOrderForTable(Long tableId) {

    OrderEntity order = getOrderDao().findOpenOrderByTable(tableId);
    return getBeanMapper().map(order, OrderEto.class);
  }

  /**
   * @param salesManagement the {@link Salesmanagement} to {@link Inject}.
   */
  @Inject
  public void setSalesManagement(Salesmanagement salesManagement) {

    this.salesManagement = salesManagement;
  }

}
