package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;
import io.oasp.module.rest.service.api.RequestParameters;

/**
 */
@Named("SalesmanagementRestService")
public class SalesmanagementRestServiceImpl implements SalesmanagementRestService {

  private Salesmanagement salesmanagement;

  @Override
  public OrderEto findOrder(long orderId) {

    return this.salesmanagement.findOrder(orderId);
  }

  @Override
  public PaginatedListTo<OrderCto> findOrders(UriInfo info) {

    RequestParameters parameters = RequestParameters.fromQuery(info);
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setTableId(parameters.get("tableId", Long.class, false));
    criteria.setState(parameters.get("state", OrderState.class, false));

    Integer page = parameters.get("pagination[page]", Integer.class, false);
    if (page != null) {
      PaginationTo pagination = new PaginationTo();
      pagination.setPage(page);
      pagination.setSize(parameters.get("pagination[size]", Integer.class, false));
      criteria.setPagination(pagination);
    }

    return this.salesmanagement.findOrderCtos(criteria);
  }

  @Override
  public PaginatedListTo<OrderCto> findOrdersByPost(OrderSearchCriteriaTo searchCriteriaTo) {

    return this.salesmanagement.findOrderCtos(searchCriteriaTo);
  }

  @Override
  public List<OrderPositionEto> findOrderPositions(UriInfo info) {

    RequestParameters parameters = RequestParameters.fromQuery(info);
    OrderPositionSearchCriteriaTo criteria = new OrderPositionSearchCriteriaTo();
    criteria.setOrderId(parameters.get("orderId", Long.class, false));
    criteria.setCookId(parameters.get("cookId", Long.class, false));
    criteria.setState(parameters.get("state", OrderPositionState.class, false));
    criteria.setMealOrSideDish(parameters.get("mealOrSideDish", boolean.class, false));
    return this.salesmanagement.findOrderPositions(criteria);
  }

  @Override
  @Deprecated
  public OrderCto updateOrder(OrderCto order, long orderId) {

    Objects.requireNonNull(order, "order");
    OrderEto orderEto = order.getOrder();
    Objects.requireNonNull(orderEto, "order");
    Long jsonOrderId = orderEto.getId();
    if (jsonOrderId == null) {
      orderEto.setId(orderId);
    } else if (!jsonOrderId.equals(orderId)) {
      throw new BadRequestException("Order ID of URL does not match JSON payload!");
    }
    return this.salesmanagement.saveOrder(order);
  }

  @Override
  public OrderCto saveOrder(OrderCto order) {

    return this.salesmanagement.saveOrder(order);
  }

  @Override
  public OrderPositionEto findOrderPosition(long orderPositionId) {

    return this.salesmanagement.findOrderPosition(orderPositionId);

  }

  @Override
  @Deprecated
  public OrderPositionEto createOrderPosition(OfferEto offer, long orderId, String comment) {

    return this.salesmanagement.createOrderPosition(offer, findOrder(orderId), comment);
  }

  @Override
  public OrderPositionEto saveOrderPosition(OrderPositionEto orderPosition) {

    return this.salesmanagement.saveOrderPosition(orderPosition);
  }

  @Override
  public BillCto findBill(long billId) {

    return this.salesmanagement.findBill(billId);
  }

  @Override
  public PaymentStatus doPayment(long billId) {

    return this.salesmanagement.doPayment(findBill(billId).getBill());
  }

  @Override
  public PaymentStatus doPayment(long billId, PaymentData paymentData) {

    return this.salesmanagement.doPayment(findBill(billId).getBill(), paymentData);
  }

  @Override
  public BillEto createBill(BillEto bill) {

    return this.salesmanagement.createBill(bill);
  }

  @Override
  public void deleteBill(long billId) {

    this.salesmanagement.deleteBill(billId);
  }

  @Override
  public void changeTable(long orderId, long newTableId) {

    this.salesmanagement.changeTable(orderId, newTableId);
  }

  /**
   * This method sets the field <tt>salesmanagement</tt>.
   *
   * @param salesmanagement the new value of the field salesmanagement
   */
  @Inject
  public void setSalesmanagement(Salesmanagement salesmanagement) {

    this.salesmanagement = salesmanagement;
  }

}
