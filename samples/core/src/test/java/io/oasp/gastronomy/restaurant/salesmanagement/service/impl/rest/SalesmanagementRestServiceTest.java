package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.util.List;

import org.junit.Test;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */

public class SalesmanagementRestServiceTest extends SalesmanagementTest {

  private static int numberOfOrders;

  private static final int EXPECTED_NUMBER_OF_ORDER_POSITIONS = 5;

  public SalesmanagementRestServiceTest() {
    super();
  }

  @Test
  public void findOrder() {

    // setup
    long sampleOrderId = EXPECTED_NUMBER_OF_ORDERS + 1;
    OrderCto sampleOrderCto = createSampleOrderCto(SAMPLE_TABLE_ID);
    this.service.saveOrder(sampleOrderCto);

    // exercise
    OrderEto expectedOrderEto = this.service.findOrder(sampleOrderId);

    // verify
    assertThat(expectedOrderEto).isNotNull();
    assertThat(expectedOrderEto.getId()).isEqualTo(sampleOrderId);
    assertThat(expectedOrderEto.getTableId()).isEqualTo(SAMPLE_TABLE_ID);
  }

  @Test
  public void findOrders() {

    // MultivaluedMap<String, String> myParameterMap = new MultivaluedHashMap<>();
    // myParameterMap.add("tableId", "102");
    // myParameterMap.add("state", OrderState.OPEN.toString());
    // myParameterMap.add("pagination[page]", "1");
    // UriInfo uriInfo = Mockito.mock(UriInfo.class);
    // Mockito.when(uriInfo.getQueryParameters()).thenReturn(myParameterMap);
    //
    //

    PaginatedListTo<OrderCto> orders = this.service.findOrders(new MyUriInfo(null, null));
    assertThat(orders).isNotNull();

    // System.out.println("\n\n\n---------------OHWACHT!!!!----------------\n\n\n");
    // for (OrderCto cto : orders.getResult()) {

    // LOG.debug("cto: " + cto.getOrder().getTableId());
    // System.out.println(("cto: " + cto.getOrder().getTableId()));
    // }

    assertThat(orders).isNotNull();

  }

  @Test
  public void findOrdersByPost() {

    // setup
    long sampleOrderId = EXPECTED_NUMBER_OF_ORDERS + 1;

    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setTableId(SAMPLE_TABLE_ID + 1);
    criteria.setState(SAMPLE_ORDER_STATE);
    PaginationTo pagination = PaginationTo.NO_PAGINATION;
    criteria.setPagination(pagination);

    OrderCto sampleOrderCto = createSampleOrderCto(SAMPLE_TABLE_ID + 1);
    this.service.saveOrder(sampleOrderCto);

    // exercise
    PaginatedListTo<OrderCto> orderCtoList = this.service.findOrdersByPost(criteria);

    // verify
    assertThat(orderCtoList).isNotNull();
    // TODO Jonas
    assertThat(orderCtoList.getResult().size()).isEqualTo(1);
    assertThat(orderCtoList.getResult().get(0).getOrder().getId()).isEqualTo(sampleOrderId);
    assertThat(orderCtoList.getResult().get(0).getOrder().getTableId()).isEqualTo(SAMPLE_TABLE_ID + 1);
    assertThat(orderCtoList.getResult().get(0).getOrder().getState()).isEqualTo(SAMPLE_ORDER_STATE);

  }

  @Test
  public void cancelOrderPosition() {

    // setup
    long sampleOrderId = EXPECTED_NUMBER_OF_ORDERS + 1;

    OrderCto sampleOrderCto = createSampleOrderCto(SAMPLE_TABLE_ID + 1);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    long responseOrderId = responseOrderCto.getOrder().getId();

    OrderPositionEto sampleOrderPositionEto = createSampleOrderPositionEto(sampleOrderId);
    OrderPositionEto responseOrderPositionEto = this.service.saveOrderPosition(sampleOrderPositionEto);
    long sampleOrderPositionId = responseOrderPositionEto.getId();

    OrderEto sampleOrderEto = new OrderEto();
    sampleOrderEto.setId(responseOrderId);
    sampleOrderCto.setOrder(sampleOrderEto);

    // execute
    this.service.saveOrder(sampleOrderCto);

    // TODO Jonas, is this also execution
    responseOrderPositionEto = this.service.findOrderPosition(sampleOrderPositionId);

    // verify
    assertThat(responseOrderPositionEto.getState()).isEqualTo(OrderPositionState.CANCELLED);

    // TODO Jonas where is not scope of the test
    assertThat(responseOrderPositionEto.getId()).isEqualTo(sampleOrderPositionId);
    assertThat(responseOrderId).isEqualTo(sampleOrderId);

  }

  @Test
  public void findOrderPositions() {

    // setup
    OrderPositionEto sampleOrderPositionEto = createSampleOrderPositionEto(EXPECTED_NUMBER_OF_ORDERS);
    this.service.saveOrderPosition(sampleOrderPositionEto);

    // execute
    List<OrderPositionEto> orderPositionEtos = this.service.findOrderPositions(null);

    // verify
    assertThat(getNumberOfOrderPositions()).isEqualTo(EXPECTED_NUMBER_OF_ORDER_POSITIONS + 1);
    for (int i = 0; i < orderPositionEtos.size(); i++) {
      assertThat(orderPositionEtos.get(i).getId()).isEqualTo(i + 1);
      assertThat(orderPositionEtos.get(i).getOrderId()).isEqualTo(EXPECTED_NUMBER_OF_ORDERS);
    }
  }

  @Test
  public void findOrderPosition() {

    // setup
    OrderPositionEto sampleOrderPositionEto = createSampleOrderPositionEto(EXPECTED_NUMBER_OF_ORDERS);
    this.service.saveOrderPosition(sampleOrderPositionEto);

    // exercise
    OrderPositionEto expectedOrderPositionEto = this.service.findOrderPosition(numberOfOrderPositions + 1);

    // verify
    assertThat(expectedOrderPositionEto).isNotNull();
    assertThat(numberOfOrderPositions).isEqualTo(EXPECTED_NUMBER_OF_ORDER_POSITIONS);
    assertThat(expectedOrderPositionEto.getId()).isEqualTo(numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto.getOrderId()).isEqualTo(EXPECTED_NUMBER_OF_ORDERS);
    assertThat(expectedOrderPositionEto.getOfferId()).isEqualTo(SAMPLE_OFFER_ID);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(expectedOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
  }
}
/*
 * public OrderPositionEto findOrderPosition(long orderPositionId) {
 *
 * return salesmanagement.findOrderPosition(orderPositionId);
 *
 * }
 */
