#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.service.impl.rest;

import ${package}.general.common.api.datatype.Money;
import ${package}.general.logic.api.PermissionConstants;
import ${package}.offermanagement.logic.api.to.OfferEto;
import ${package}.salesmanagement.common.api.datatype.OrderPositionState;
import ${package}.salesmanagement.common.api.datatype.OrderState;
import ${package}.salesmanagement.common.api.datatype.PaymentStatus;
import ${package}.salesmanagement.logic.api.Salesmanagement;
import ${package}.salesmanagement.logic.api.to.BillEto;
import ${package}.salesmanagement.logic.api.to.OrderCto;
import ${package}.salesmanagement.logic.api.to.OrderEto;
import ${package}.salesmanagement.logic.api.to.OrderPositionEto;
import ${package}.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import ${package}.salesmanagement.logic.api.to.PaymentData;
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
   * @param orderId specified for the order
   * @return the order
   */
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/order/{orderId}")
  @GET
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public OrderEto getOrder(@PathParam("orderId") Long orderId) {

    return this.salesManagement.findOrder(orderId);
  }

  /**
   * @param info is the {@link UriInfo}.
   * @return the {@link List} of matching {@link OrderCto}s.
   */
  @Produces(MediaType.APPLICATION_JSON)
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
   * @param order the {@link OrderCto} to update.
   * @param orderId the {@link OrderEto${symbol_pound}getId() ID} of the order.
   * @return the updated {@link OrderCto}.
   */
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/order/{orderId}")
  @PUT
  @RolesAllowed(PermissionConstants.UPDATE_ORDER)
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
    return this.salesManagement.updateOrder(order);
  }

  /**
   * @param order the {@link OrderCto} to create.
   * @return the created {@link OrderCto} (with {@link OrderEto${symbol_pound}getId() ID}(s) assigned).
   */
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/order/")
  @POST
  @RolesAllowed(PermissionConstants.CREATE_ORDER)
  public OrderCto createOrder(OrderCto order) {

    Objects.requireNonNull(order, "order");
    OrderEto orderEto = order.getOrder();
    Objects.requireNonNull(orderEto, "order");
    // Long orderId = orderEto.getId();
    // if (orderId == null) {
    // throw new BadRequestException("Create order shall not provide ID!");
    // }
    return this.salesManagement.updateOrder(order);
  }

  /**
   * @param offer the offer as new position within an order as JSON
   * @param orderId the order id
   * @param comment the comment together with an orderPosition
   * @return a new orderPosition
   */
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/order/{orderId}/position/{comment}")
  @POST
  @RolesAllowed(PermissionConstants.CREATE_ORDER)
  public OrderPositionEto createOrderPosition(OfferEto offer, @PathParam("orderId") Long orderId,
      @PathParam("comment") String comment) {

    return this.salesManagement.createOrderPosition(offer, getOrder(orderId), comment);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/order/{orderId}/position/{orderPositionId}")
  @GET
  @RolesAllowed(PermissionConstants.FIND_ORDER)
  public OrderPositionEto getOrderPosition(@PathParam("orderPositionId") Long orderPositionId) {

    return this.salesManagement.findOrderPosition(orderPositionId);

  }

  // although orderId and orderPositionId are not explicitly needed here, the path structure is intentionally chosen
  // see createOrderPosition for a similar reason
  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @PUT
  @Path("/order/{orderId}/position/{orderPositionId}")
  @RolesAllowed(PermissionConstants.UPDATE_ORDER)
  public void updateOrderPosition(OrderPositionEto order) {

    this.salesManagement.updateOrderPosition(order);
  }

  // again orderId is not explicitly needed here
  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @PUT
  @Path("/order/{orderId}/position/{orderPositionId}/{newstate}")
  @RolesAllowed(PermissionConstants.UPDATE_ORDER)
  public void markOrderPositionAs(OrderPositionEto orderPosition, @PathParam("newState") OrderPositionState newState) {

    this.salesManagement.markOrderPositionAs(orderPosition, newState);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/bill/{billId}")
  @RolesAllowed(PermissionConstants.FIND_BILL)
  public BillEto getBill(@PathParam("billId") Long billId) {

    return this.salesManagement.findBill(billId);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @POST
  @Path("/bill/{billId}/payment")
  @RolesAllowed(PermissionConstants.UPDATE_BILL)
  public PaymentStatus doPayment(@PathParam("billId") Long billId) {

    return this.salesManagement.doPayment(getBill(billId));
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/bill/{billId}/payment")
  @POST
  @RolesAllowed(PermissionConstants.UPDATE_BILL)
  public PaymentStatus doPayment(@PathParam("billId") Long billId, PaymentData paymentData) {

    return this.salesManagement.doPayment(getBill(billId), paymentData);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("/bill/{tip}")
  @RolesAllowed(PermissionConstants.CREATE_BILL)
  public BillEto createBill(List<OrderPositionEto> orderPositions, @PathParam("tip") Money tip) {

    return this.salesManagement.createBill(orderPositions, tip);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Path("/bill/{billId}")
  @DELETE
  @RolesAllowed(PermissionConstants.DELETE_BILL)
  public void deleteBill(@PathParam("billId") Long billId) {

    this.salesManagement.deleteBill(billId);
  }
}
