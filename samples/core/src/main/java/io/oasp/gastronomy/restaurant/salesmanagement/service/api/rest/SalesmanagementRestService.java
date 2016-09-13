package io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest;

import java.util.List;

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

import io.oasp.gastronomy.restaurant.general.common.api.RestService;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcChangeTable;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrderPosition;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 *
 * The service class for REST calls in order to execute the methods in {@link Salesmanagement}.
 *
 */

@Path("/salesmanagement/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface SalesmanagementRestService extends RestService {

  /**
   * Delegates to {@link UcFindOrder#findOrder}.
   *
   * @param orderId specified for the order
   * @return the order
   */
  @Path("/order/{orderId}")
  @GET
  OrderEto findOrder(@PathParam("orderId") long orderId);

  /**
   * Delegates to {@link UcFindOrderPosition#findOrderPositions}.
   *
   * @param info is the {@link UriInfo}.
   * @return the {@link List} of matching {@link OrderCto}s.
   */
  @Path("/order")
  @GET
  PaginatedListTo<OrderCto> findOrders(@Context UriInfo info);

  /**
   * Delegates to {@link UcFindOrderPosition#findOrderPositions}.
   *
   * @param searchCriteriaTo the pagination and search criteria to be used for finding orders.
   * @return the {@link PaginatedListTo list} of matching {@link OrderCto}s.
   */
  @Path("/order/search")
  @POST
  PaginatedListTo<OrderCto> findOrdersByPost(OrderSearchCriteriaTo searchCriteriaTo);

  /**
   * Delegates to {@link UcFindOrderPosition#findOrderPositions}.
   *
   * @param info is the {@link UriInfo}.
   * @return the {@link List} of matching {@link OrderPositionEto}s.
   */
  @Path("/orderposition")
  @GET
  List<OrderPositionEto> findOrderPositions(@Context UriInfo info);

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
  public OrderCto updateOrder(OrderCto order, @PathParam("orderId") long orderId);

  /**
   * Delegates to {@link UcManageOrder#saveOrder}.
   *
   * @param order the {@link OrderCto} to save.
   * @return the saved {@link OrderCto} (with {@link OrderEto#getId() ID}(s) assigned).
   */
  @Path("/order/")
  @POST
  OrderCto saveOrder(OrderCto order);

  /**
   * Delegates to {@link UcFindOrderPosition#findOrderPosition}.
   *
   * @param orderPositionId id of the {@link OrderPositionEto}
   * @return the {@link OrderPositionEto}.
   */
  @Path("/orderposition/{orderPositionId}")
  @GET
  OrderPositionEto findOrderPosition(@PathParam("orderPositionId") long orderPositionId);

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
  OrderPositionEto createOrderPosition(OfferEto offer, @PathParam("orderId") long orderId,
      @PathParam("comment") String comment);

  /**
   * Delegates to {@link UcManageOrderPosition#saveOrderPosition}.
   *
   * @param orderPosition the OrderPositionEto to save
   * @return the saved OrderPositionEto
   */
  @POST
  @Path("/orderposition/")
  OrderPositionEto saveOrderPosition(OrderPositionEto orderPosition);

  /**
   * Delegates to {@link UcFindBill#findBill}.
   *
   * @param billId id of the {@link BillEto}
   * @return the {@link BillEto}
   */
  @GET
  @Path("/bill/{billId}")
  BillCto findBill(@PathParam("billId") long billId);

  /**
   * Delegates to {@link UcManageBill#doPayment}.
   *
   * @param billId id of the bill
   * @return the {@link PaymentStatus}
   */
  @POST
  @Path("/bill/{billId}/payment")
  PaymentStatus doPayment(@PathParam("billId") long billId);

  /**
   * Delegates to {@link UcManageBill#doPayment(BillEto bill, PaymentData paymentDataDebitor)}.
   *
   * @param billId id of the {@link BillEto}
   * @param paymentData the {@link PaymentData}
   * @return the {@link PaymentStatus}
   */
  @Path("/bill/{billId}/payment")
  @POST
  PaymentStatus doPayment(@PathParam("billId") long billId, PaymentData paymentData);

  /**
   * Delegates to {@link UcManageBill#createBill}.
   *
   * @param bill the bill to create
   * @return the created {@link BillEto}
   */
  @POST
  @Path("/bill/")
  BillEto createBill(BillEto bill);

  /**
   * Delegates to {@link UcManageBill#deleteBill}.
   *
   * @param billId id of the {@link BillEto}
   */
  @Path("/bill/{billId}")
  @DELETE
  void deleteBill(@PathParam("billId") long billId);

  /**
   * Delegates to {@link UcChangeTable#changeTable}.
   *
   * @param orderId the Id of the order
   * @param newTableId the Id of the new table
   */
  @Path("/order/{orderId}")
  @POST
  void changeTable(@PathParam("orderId") long orderId, long newTableId);

}
