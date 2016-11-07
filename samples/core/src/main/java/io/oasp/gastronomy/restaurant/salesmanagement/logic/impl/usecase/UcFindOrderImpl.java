package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractOrderUc;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use Case to find an order.
 *
 */
@Named
@UseCase
public class UcFindOrderImpl extends AbstractOrderUc implements UcFindOrder {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindOrderImpl.class);

  private Salesmanagement salesManagement;

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public OrderEto findOrder(long orderId) {

    LOG.debug("Get order.");
    return getBeanMapper().map(getOrderDao().findOne(orderId), OrderEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public PaginatedListTo<OrderEto> findOrderEtos(OrderSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<OrderEntity> orders = getOrderDao().findOrders(criteria);

    return mapPaginatedEntityList(orders, OrderEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public PaginatedListTo<OrderCto> findOrderCtos(OrderSearchCriteriaTo criteria) {

    PaginatedListTo<OrderEto> orderEtos = findOrderEtos(criteria);

    List<OrderCto> page = new ArrayList<>(orderEtos.getResult().size());
    for (OrderEto order : orderEtos.getResult()) {
      // REVIEW <who> (hohwille) N+1 problem. Find a better and more efficient way to load the order positions.
      page.add(findOrderCto(order));
    }

    PaginatedListTo<OrderCto> result = new PaginatedListTo<>(page, orderEtos.getPagination());
    return result;
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public OrderCto findOrderCto(OrderEto order) {

    OrderCto result = new OrderCto();
    result.setOrder(order);
    List<OrderPositionEto> positions = this.salesManagement.findOrderPositionsByOrderId(order.getId());
    result.setPositions(positions);
    return result;
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public OrderEto findOpenOrderForTable(long tableId) {

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
