package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;
import io.oasp.module.basic.configuration.OaspProfile;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@WebIntegrationTest("server.port:0")
@ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST })
@Transactional

public class SalesmanagementRestServiceTest extends SubsystemTest {

  @Value("${local.server.port}")
  private int port;

  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  private SalesmanagementRestService service;

  private int numberOfOrders;

  private int numberOfOrderPositions;

  private static final int EXPECTED_NUMBER_OF_ORDERS = 1;

  private static final int EXPECTED_NUMBER_OF_ORDER_POSITIONS = 5;

  private static final long SAMPLE_OFFER_ID = 5L;

  private static final String SAMPLE_OFFER_NAME = "Cola";

  private static final OrderState SAMPLE_ORDER_STATE = OrderState.OPEN;

  private static final OrderPositionState SAMPLE_ORDER_POSITION_STATE = OrderPositionState.DELIVERED;

  private static final ProductOrderState SAMPLE_DRINK_STATE = ProductOrderState.DELIVERED;

  private static final long SAMPLE_TABLE_ID = 101;

  private static final Money SAMPLE_PRICE = new Money(6.23);

  // TODO just workaraound, as Jonas solution is not yet approved
  @Inject
  private Flyway flyway;

  // @Path("/orderposition")

  // TODO why is it null with
  // why does it work not work with
  private UriInfo uriInfo;

  // @BeforeClass
  // public void setMembers() {
  //
  // this.numberOfOrderPositions = 0;
  // this.expectedNumberOfOrderPositions = 5;
  // }

  @Before
  public void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();

    this.service = RestTestClientBuilder.build(SalesmanagementRestService.class, "waiter", "waiter",
        "http://localhost:" + this.port + "/services/rest", this.jacksonJsonProvider);
    this.numberOfOrderPositions = 0;

  }

  @Test
  public void testFindOrder() {

    // Setup
    long sampleOrderId = EXPECTED_NUMBER_OF_ORDERS + 1;
    OrderCto sampleOrderCto = createSampleOrderCto(SAMPLE_TABLE_ID);
    this.service.saveOrder(sampleOrderCto);

    // Exercise
    OrderEto expectedOrderEto = this.service.findOrder(sampleOrderId);

    // Verify
    assertThat(expectedOrderEto).isNotNull();
    assertThat(expectedOrderEto.getId()).isEqualTo(sampleOrderId);
    assertThat(expectedOrderEto.getTableId()).isEqualTo(SAMPLE_TABLE_ID);
    assertThat(expectedOrderEto.getState()).isEqualTo(SAMPLE_ORDER_STATE);
  }

  @Test
  public void testFindOrders() {

  }

  @Test
  public void testFindOrdersByPost() {

    // setup
    long sampleOrderId = EXPECTED_NUMBER_OF_ORDERS + 1;

    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setTableId(SAMPLE_TABLE_ID);
    criteria.setState(SAMPLE_ORDER_STATE);
    PaginationTo pagination = PaginationTo.NO_PAGINATION;
    // pagination.setSize(100);

    criteria.setPagination(pagination);

    OrderCto sampleOrderCto = createSampleOrderCto(SAMPLE_TABLE_ID);
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
    assertThat(expectedOrderPositionEto.getOfferId()).isEqualTo(EXPECTED_NUMBER_OF_ORDER_POSITIONS + 1);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(expectedOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
  }

  private OrderCto createSampleOrderCto(long tableId) {

    OrderCto sampleOrderCto = new OrderCto();
    OrderEto sampleOrderEto = new OrderEto();
    sampleOrderEto.setTableId(tableId);
    sampleOrderCto.setOrder(sampleOrderEto);
    return sampleOrderCto;
  }

  // TODO talk with Jonas, where to put this function
  static public OrderPositionEto createSampleOrderPositionEto(long orderId) {

    OrderPositionEto orderPositionEto = new OrderPositionEto();
    orderPositionEto.setOrderId(orderId);
    orderPositionEto.setOfferId(SAMPLE_OFFER_ID);
    orderPositionEto.setOfferName(SAMPLE_OFFER_NAME);
    orderPositionEto.setState(SAMPLE_ORDER_POSITION_STATE);
    orderPositionEto.setDrinkState(SAMPLE_DRINK_STATE);
    orderPositionEto.setPrice(SAMPLE_PRICE);
    orderPositionEto.setComment(SAMPLE_OFFER_NAME);
    return orderPositionEto;
  }
}
/*
 * public OrderPositionEto findOrderPosition(long orderPositionId) {
 *
 * return this.salesmanagement.findOrderPosition(orderPositionId);
 *
 * }
 */
