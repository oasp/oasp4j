package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
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

import javax.annotation.security.RolesAllowed;
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
  @RolesAllowed(PermissionConstants.FIND_ORDER)
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
  @RolesAllowed(PermissionConstants.FIND_ORDER)
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
  @RolesAllowed(PermissionConstants.FIND_ORDER_POSITION)
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
  @RolesAllowed(PermissionConstants.SAVE_ORDER)
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
  @RolesAllowed(PermissionConstants.SAVE_ORDER)
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
  @RolesAllowed(PermissionConstants.FIND_ORDER_POSITION)
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
  @RolesAllowed(PermissionConstants.SAVE_ORDER_POSITION)
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
  @RolesAllowed(PermissionConstants.SAVE_ORDER_POSITION)
  public OrderPositionEto saveOrderPosition(OrderPositionEto orderPosition) {

    return this.salesManagement.saveOrderPosition(orderPosition);
  }

  // again orderId is not explicitly needed here
  /**
   * Delegates to {@link UcManageOrderPosition#markOrderPositionAs}.
   *
   * @param orderPosition the {@link OrderPositionEto} to change
   * @param newState the new {@link OrderPositionState}
   */
  @PUT
  @Path("/order/{orderId}/position/{orderPositionId}/{newstate}")
  @RolesAllowed(PermissionConstants.SAVE_ORDER_POSITION)
  public void markOrderPositionAs(OrderPositionEto orderPosition, @PathParam("newState") OrderPositionState newState) {

    this.salesManagement.markOrderPositionAs(orderPosition, newState);
  }

  /**
   * Delegates to {@link UcFindBill#findBill}
   *
   * @param billId id of the {@link BillEto}
   * @return the {@link BillEto}
   */
  @GET
  @Path("/bill/{billId}")
  @RolesAllowed(PermissionConstants.FIND_BILL)
  public BillEto getBill(@PathParam("billId") Long billId) {

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
  @RolesAllowed(PermissionConstants.SAVE_BILL)
  public PaymentStatus doPayment(@PathParam("billId") Long billId) {

    return this.salesManagement.doPayment(getBill(billId));
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
  @RolesAllowed(PermissionConstants.SAVE_BILL)
  public PaymentStatus doPayment(@PathParam("billId") Long billId, PaymentData paymentData) {

    return this.salesManagement.doPayment(getBill(billId), paymentData);
  }

  /**
   * Delegates to {@link UcManageBill#createBill}.
   *
   * @param orderPositions list of {@link OrderPositionEto}s to be contained in the bill
   * @param tip the tip
   * @return the created {@link BillEto}
   */
  @POST
  @Path("/bill/{tip}")
  @RolesAllowed(PermissionConstants.SAVE_BILL)
  public BillEto createBill(List<OrderPositionEto> orderPositions, @PathParam("tip") Money tip) {

    return this.salesManagement.createBill(orderPositions, tip);
  }

  /**
   * Delegates to {@link UcManageBill#deleteBill}.
   *
   * @param billId id of the {@link BillEto}
   */
  @Path("/bill/{billId}")
  @DELETE
  @RolesAllowed(PermissionConstants.DELETE_BILL)
  public void deleteBill(@PathParam("billId") Long billId) {

    this.salesManagement.deleteBill(billId);
  }
}
