package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

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
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrderPosition;
import io.oasp.module.rest.service.api.RequestParameters;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.transaction.annotation.Transactional;

/**
 * This class contains methods for handling REST calls for {@link Salesmanagement}.
 *
 * @author agreul
 */
@Path("/salesmanagement")
@Named("SalesmanagementRestService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class SalesmanagementRestServiceImpl {

  private Salesmanagement salesManagement;

  /**
   * @param salesManagement the salesManagement to be set
   */
  @Inject
  public void setSalesManagement(Salesmanagement salesManagement) {

    this.salesManagement = salesManagement;
  }

  /**
   * Delegates to {@link UcFindOrder#findOrder}.
   *
   * @param orderId specified for the order
   * @return the order
   */
  @Path("/order/{orderId}")
  @GET
  public OrderEto findOrder(@PathParam("orderId") Long orderId) {

    return this.salesManagement.findOrder(orderId);
  }

  /**
   * Delegates to {@link UcFindOrderPosition#findOrderPositions}.
   *
   * @param info is the {@link UriInfo}.
   * @return the {@link List} of matching {@link OrderCto}s.
   */
  @Path("/order")
  @GET
  public List<OrderCto> findOrders(@Context UriInfo info) {

    RequestParameters parameters = RequestParameters.fromQuery(info);
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setTableId(parameters.get("tableId", Long.class, false));
    criteria.setState(parameters.get("state", OrderState.class, false));
    return this.salesManagement.findOrderCtos(criteria);
  }

  /**
   * Delegates to {@link UcFindOrderPosition#findOrderPositions}.
   *
   * @param info is the {@link UriInfo}.
   * @return the {@link List} of matching {@link OrderPositionEto}s.
   */
  @Path("/orderposition")
  @GET
  public List<OrderPositionEto> findOrderPositions(@Context UriInfo info) {

    RequestParameters parameters = RequestParameters.fromQuery(info);
    OrderPositionSearchCriteriaTo criteria = new OrderPositionSearchCriteriaTo();
    criteria.setOrderId(parameters.get("orderId", Long.class, false));
    criteria.setCookId(parameters.get("cookId", Long.class, false));
    criteria.setState(parameters.get("state", OrderPositionState.class, false));
    criteria.setMealOrSideDish(parameters.get("mealOrSideDish", boolean.class, false));
    return this.salesManagement.findOrderPositions(criteria);
  }

  /**
   * Delegates to {@link UcManageOrder#saveOrder}.
   *
   * @param order the {@link OrderCto} to update.
   * @param orderId the {@link OrderEto#getId() ID} of the order.
   * @return the updated {@link OrderCto}.
   */
  @Path("/order/{orderId}")
  @PUT
  @Deprecated
  public OrderCto updateOrder(OrderCto order, @PathParam("orderId") Long orderId) {

    Objects.requireNonNull(order, "order");
    OrderEto orderEto = order.getOrder();
    Objects.requireNonNull(orderEto, "order");
    Long jsonOrderId = orderEto.getId();
    if (jsonOrderId == null) {
      orderEto.setId(orderId);
    } else if (!jsonOrderId.equals(orderId)) {
      throw new BadRequestException("Order ID of URL does not match JSON payload!");
    }
    return this.salesManagement.saveOrder(order);
  }

  /**
   * Delegates to {@link UcManageOrder#saveOrder}.
   *
   * @param order the {@link OrderCto} to save.
   * @return the saved {@link OrderCto} (with {@link OrderEto#getId() ID}(s) assigned).
   */
  @Path("/order/")
  @POST
  public OrderCto saveOrder(OrderCto order) {

    return this.salesManagement.saveOrder(order);
  }

  /**
   * Delegates to {@link UcFindOrderPosition#findOrderPosition}.
   *
   * @param orderPositionId id of the {@link OrderPositionEto}
   * @return the {@link OrderPositionEto}.
   */
  @Path("/orderposition/{orderPositionId}")
  @GET
  public OrderPositionEto findOrderPosition(@PathParam("orderPositionId") Long orderPositionId) {

    return this.salesManagement.findOrderPosition(orderPositionId);

  }

  /**
   * Delegates to {@link UcManageOrderPosition#createOrderPosition}.
   *
   * @param offer the offer as new position within an order as JSON
   * @param orderId the order id
   * @param comment the comment together with an orderPosition
   * @return a new orderPosition
   * @deprecated not REST style, will be removed.
   */
  @Path("/order/{orderId}/position/{comment}")
  @POST
  @Deprecated
  public OrderPositionEto createOrderPosition(OfferEto offer, @PathParam("orderId") Long orderId,
      @PathParam("comment") String comment) {

    return this.salesManagement.createOrderPosition(offer, findOrder(orderId), comment);
  }

  /**
   * Delegates to {@link UcManageOrderPosition#saveOrderPosition}.
   *
   * @param orderPosition the OrderPositionEto to save
   * @return the saved OrderPositionEto
   */
  @POST
  @Path("/orderposition/")
  public OrderPositionEto saveOrderPosition(OrderPositionEto orderPosition) {

    return this.salesManagement.saveOrderPosition(orderPosition);
  }

  /**
   * Delegates to {@link UcFindBill#findBill}
   *
   * @param billId id of the {@link BillEto}
   * @return the {@link BillEto}
   */
  @GET
  @Path("/bill/{billId}")
  public BillCto findBill(@PathParam("billId") Long billId) {

    return this.salesManagement.findBill(billId);
  }

  /**
   * Delegates to {@link UcManageBill#doPayment}
   *
   * @param billId id of the bill
   * @return the {@link PaymentStatus}
   */
  @POST
  @Path("/bill/{billId}/payment")
  public PaymentStatus doPayment(@PathParam("billId") Long billId) {

    return this.salesManagement.doPayment(findBill(billId).getBill());
  }

  /**
   * Delegates to {@link UcManageBill#doPayment(BillEto bill, PaymentData paymentDataDebitor)}
   *
   * @param billId id of the {@link BillEto}
   * @param paymentData the {@link PaymentData}
   * @return the {@link PaymentStatus}
   */
  @Path("/bill/{billId}/payment")
  @POST
  public PaymentStatus doPayment(@PathParam("billId") Long billId, PaymentData paymentData) {

    return this.salesManagement.doPayment(findBill(billId).getBill(), paymentData);
  }

  /**
   * Delegates to {@link UcManageBill#createBill}.
   *
   * @param bill the bill to create
   * @return the created {@link BillEto}
   */
  @POST
  @Path("/bill/")
  public BillEto createBill(BillEto bill) {

    return this.salesManagement.createBill(bill);
  }

  /**
   * Delegates to {@link UcManageBill#deleteBill}.
   *
   * @param billId id of the {@link BillEto}
   */
  @Path("/bill/{billId}")
  @DELETE
  public void deleteBill(@PathParam("billId") Long billId) {

    this.salesManagement.deleteBill(billId);
  }
}
