package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */

public class SalesmanagementRestServiceTest extends SalesmanagementTest {

  private int numberOfOrders;

  private int numberOfOrderPositions;

  private static final int EXPECTED_NUMBER_OF_ORDERS = 1;

  private static final int EXPECTED_NUMBER_OF_ORDER_POSITIONS = 5;

  public SalesmanagementRestServiceTest() {
    super();
  }

  @Override
  @Before
  public void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();
    // cannot be put into constructor, as port is set after the constructor invocation
    this.service = RestTestClientBuilder.build(SalesmanagementRestService.class, this.ROLE, this.ROLE,
        "http://localhost:" + this.port + "/services/rest", this.jacksonJsonProvider);
    this.numberOfOrderPositions = 0;

  }

  @Test
  public void testFindOrder() {

    // Setup
    long sampleOrderId = EXPECTED_NUMBER_OF_ORDERS + 1;
    OrderCto sampleOrderCto = createSampleOrderCto(this.SAMPLE_TABLE_ID);
    this.service.saveOrder(sampleOrderCto);

    // Exercise
    OrderEto expectedOrderEto = this.service.findOrder(sampleOrderId);

    // Verify
    assertThat(expectedOrderEto).isNotNull();
    assertThat(expectedOrderEto.getId()).isEqualTo(sampleOrderId);
    assertThat(expectedOrderEto.getTableId()).isEqualTo(this.SAMPLE_TABLE_ID);
    assertThat(expectedOrderEto.getState()).isEqualTo(this.SAMPLE_ORDER_STATE);
  }

  @Test
  public void testFindOrders() {

  }

  @Test
  public void testFindOrdersByPost() {

    // setup
    long sampleOrderId = EXPECTED_NUMBER_OF_ORDERS + 1;

    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setTableId(this.SAMPLE_TABLE_ID);
    criteria.setState(this.SAMPLE_ORDER_STATE);
    PaginationTo pagination = PaginationTo.NO_PAGINATION;
    // pagination.setSize(100);

    criteria.setPagination(pagination);

    OrderCto sampleOrderCto = createSampleOrderCto(this.SAMPLE_TABLE_ID);
    List<OrderPositionEto> sampleOrderPositions = new LinkedList<OrderPositionEto>();
    sampleOrderPositions.add(createSampleOrderPositionEto(sampleOrderId));
    sampleOrderCto.setPositions(sampleOrderPositions);
    this.service.saveOrder(sampleOrderCto);

    // exercise

    PaginatedListTo<OrderCto> orderCtoList = this.service.findOrdersByPost(criteria);
    System.out.println(orderCtoList.toString());

  }

  @Test
  public void testFindOrderPositions() {

    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(this.uriInfo);
    if (orderPositions != null) {
      this.numberOfOrderPositions = orderPositions.size();
    }
    assertThat(this.numberOfOrderPositions).isEqualTo(EXPECTED_NUMBER_OF_ORDER_POSITIONS);
  }

  @Test
  public void testFindOrderPosition() {

    // Setup

    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(this.uriInfo);
    if (orderPositions != null) {
      this.numberOfOrderPositions = orderPositions.size();
    }

    OrderPositionEto sampleOrderPositionEto = createSampleOrderPositionEto(EXPECTED_NUMBER_OF_ORDERS);
    this.service.saveOrderPosition(sampleOrderPositionEto);

    // Exercise
    OrderPositionEto expectedOrderPositionEto = this.service.findOrderPosition(this.numberOfOrderPositions + 1);

    // Verify
    assertThat(expectedOrderPositionEto).isNotNull();
    assertThat(this.numberOfOrderPositions).isEqualTo(EXPECTED_NUMBER_OF_ORDER_POSITIONS);
    assertThat(expectedOrderPositionEto.getId()).isEqualTo(this.numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto.getOrderId()).isEqualTo(EXPECTED_NUMBER_OF_ORDERS);
    assertThat(expectedOrderPositionEto.getOfferId()).isEqualTo(this.SAMPLE_OFFER_ID);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(this.SAMPLE_OFFER_NAME);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(this.SAMPLE_ORDER_POSITION_STATE);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(this.SAMPLE_DRINK_STATE);
    assertThat(expectedOrderPositionEto.getPrice()).isEqualTo(this.SAMPLE_PRICE);
  }
}
/*
 * public OrderPositionEto findOrderPosition(long orderPositionId) {
 *
 * return this.salesmanagement.findOrderPosition(orderPositionId);
 *
 * }
 */
