package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.NUMBER_OF_SAMPLE_ORDERS;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.NUMBER_OF_SAMPLE_ORDER_POSITIONS;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_DRINK_STATE;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_OFFER_ID;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_OFFER_NAME;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_ORDER_POSITION_STATE;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_ORDER_STATE;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_PRICE;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_TABLE_ID;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;

/**
 * This class serves as an example of how to perform a subsystem test (e.g., call a *RestService interface). The test
 * database is accessed via an instance of the class {@link SalesmanagementRestService}.
 *
 */
@SpringApplicationConfiguration(classes = { SpringBootApp.class, SalesmanagementRestTestConfig.class })
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement" })
public class SalesmanagementRestServiceTest extends AbstractRestServiceTest {

  private SalesmanagementRestService service;

  @Inject
  private SalesmanagementRestServiceTestHelper helper;

  /**
   * Provides initialization previous to the creation of the text fixture.
   */
  @Override
  public void doSetUp() {

    super.doSetUp();
    getDbTestHelper().resetDatabase();
    this.service = getRestTestClientBuilder().build(SalesmanagementRestService.class, "waiter");
  }

  /**
   * Provides clean up after tests.
   */
  @Override
  public void doTearDown() {

    this.service = null;
    super.doTearDown();
  }

  /**
   * This test method creates a sample instance of {@link OrderCto} and saves it into the database. Thereafter the
   * {@link OrderEto} object linked to the sample object of {@link OrderCto} is loaded from the database and its
   * attributes are tested for correctness.
   */
  @Test
  public void findOrder() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    // when
    OrderEto responseOrderEto = this.service.findOrder(responseOrderCto.getOrder().getId());

    // then
    assertThat(responseOrderEto).isNotNull();
    assertThat(responseOrderEto.getId()).isEqualTo(responseOrderCto.getOrder().getId());
    assertThat(responseOrderEto.getTableId()).isEqualTo(SAMPLE_TABLE_ID);
  }

  /**
   * This test method creates some sample instances of {@link OrderCto} and saves them into the database. Thereafter all
   * {@link OrderCto} objects are loaded from the database. The method tests the number of loaded OrderCto and the
   * attributes of the sample instances for correctness.
   */
  @Test
  public void findAllOrders() {

    // given
    int oldNumberOfOrders = getNumberOfOrders();

    OrderCto sampleOrderCto;
    OrderCto responseOrderCto;

    ArrayList<OrderCto> savedOrderCtos = new ArrayList<>();
    for (int i = 0; i < NUMBER_OF_SAMPLE_ORDERS; ++i) {

      sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
      responseOrderCto = this.service.saveOrder(sampleOrderCto);
      assertThat(responseOrderCto).isNotNull();
      savedOrderCtos.add(responseOrderCto);
    }

    int newNumberOfOrders = getNumberOfOrders();

    // when
    PaginatedListTo<OrderCto> responseOrderCtos = this.service.findOrders(null);
    assertThat(responseOrderCtos).isNotNull();

    // then
    assertThat(responseOrderCtos).isNotNull();

    assertThat(oldNumberOfOrders + NUMBER_OF_SAMPLE_ORDERS).isEqualTo(newNumberOfOrders);
    assertThat(responseOrderCtos.getResult().size()).isEqualTo(newNumberOfOrders);

    int countNumberOfSavedOrders = 0;

    for (OrderCto responseOrder : responseOrderCtos.getResult()) {
      for (OrderCto savedOrder : savedOrderCtos) {
        if (responseOrder.getOrder().getId() == savedOrder.getOrder().getId()) {
          assertThat(responseOrder.getOrder().getTableId()).isEqualTo(savedOrder.getOrder().getTableId());
          countNumberOfSavedOrders++;
        }
      }
    }
    assertThat(countNumberOfSavedOrders).isEqualTo(NUMBER_OF_SAMPLE_ORDERS);

  }

  /**
   * This test method creates a sample instance of {@link OrderCto} and saves it into the database. Subsequently
   * {@link OrderSearchCriteriaTo} like tableId and {@link OrderState} are defined. Thereafter the sample
   * {@link OrderCto} object is loaded from the database, if its attributes match the {@link OrderSearchCriteriaTo}.
   * Finally the attributes of the sample instance of {@link OrderCto} are tested for correctness.
   */
  @Test
  public void findOrderByPost() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setTableId(SAMPLE_TABLE_ID);
    criteria.setState(SAMPLE_ORDER_STATE);
    PaginationTo pagination = PaginationTo.NO_PAGINATION;
    criteria.setPagination(pagination);

    // when
    PaginatedListTo<OrderCto> orderCtoList = this.service.findOrdersByPost(criteria);

    // then
    assertThat(orderCtoList).isNotNull();
    assertThat(orderCtoList.getResult().size()).isEqualTo(1);
    assertThat(orderCtoList.getResult().get(0).getOrder().getId()).isEqualTo(responseOrderCto.getOrder().getId());
    assertThat(orderCtoList.getResult().get(0).getOrder().getTableId()).isEqualTo(SAMPLE_TABLE_ID);
    assertThat(orderCtoList.getResult().get(0).getOrder().getState()).isEqualTo(SAMPLE_ORDER_STATE);

  }

  /**
   * This test method creates a sample instance of {@link OrderCto} and saves it into the database. In addition a sample
   * instance of {@link OrderPositionEto} is created, linked to the sample {@link OrderCto} object by the id/orderId and
   * saved into the database. Finally a the sample instance of {@link OrderPositionEto} is identified by its id, loaded
   * from the database and its attributes are tested for correctness.
   */
  @Test
  public void findOrderPosition() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderPositionEto sampleOrderPositionEto =
        this.helper.createSampleOrderPositionEto(responseOrderCto.getOrder().getId());
    assertThat(sampleOrderPositionEto).isNotNull();
    OrderPositionEto responseSaveOrderPositionEto = this.service.saveOrderPosition(sampleOrderPositionEto);
    assertThat(responseSaveOrderPositionEto).isNotNull();

    // when
    OrderPositionEto responseFindOrderPositionEto =
        this.service.findOrderPosition(responseSaveOrderPositionEto.getId());

    // then
    assertThat(responseFindOrderPositionEto).isNotNull();
    assertThat(responseFindOrderPositionEto.getId()).isEqualTo(responseSaveOrderPositionEto.getId());
    assertThat(responseFindOrderPositionEto.getOrderId()).isEqualTo(responseSaveOrderPositionEto.getOrderId());
    assertThat(responseFindOrderPositionEto.getOfferId()).isEqualTo(SAMPLE_OFFER_ID);
    assertThat(responseFindOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(responseFindOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(responseFindOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(responseFindOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
  }

  /**
   * This test method creates a sample instance of {@link OrderCto} and saves it into the database. In addition some
   * sample instances of {@link OrderPositionEto} are created, linked to sampleOrderCto by the id/orderId and saved into
   * the database. Thereafter all {@link OrderPositionEto} objects are loaded from the database. The method tests the
   * number of loaded orderPositionEtos and the attributes of the sample instances for correctness.
   */
  @Test
  public void findAllOrderPositions() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    int oldNumberOfOrderPositions = getNumberOfOrderPositions();
    OrderPositionEto sampleOrderPositionEto;
    OrderPositionEto responseOrderPositionEto;

    ArrayList<OrderPositionEto> savedOrderPositionEtos = new ArrayList<>();
    for (int i = 0; i < NUMBER_OF_SAMPLE_ORDER_POSITIONS; ++i) {
      sampleOrderPositionEto = new OrderPositionEto();
      sampleOrderPositionEto.setOrderId(responseOrderCto.getOrder().getId());
      sampleOrderPositionEto.setOfferId(SAMPLE_OFFER_ID);
      responseOrderPositionEto = this.service.saveOrderPosition(sampleOrderPositionEto);
      assertThat(responseOrderPositionEto).isNotNull();
      savedOrderPositionEtos.add(responseOrderPositionEto);
    }

    int newNumberOfOrderPositions = getNumberOfOrderPositions();

    // when
    List<OrderPositionEto> responseOrderPositionEtos = this.service.findOrderPositions(null);

    // then
    assertThat(responseOrderPositionEtos).isNotNull();

    assertThat(oldNumberOfOrderPositions + NUMBER_OF_SAMPLE_ORDER_POSITIONS).isEqualTo(newNumberOfOrderPositions);
    assertThat(responseOrderPositionEtos.size()).isEqualTo(newNumberOfOrderPositions);

    int countNumberOfSavedOrderPositions = 0;

    for (OrderPositionEto responseOrderPosition : responseOrderPositionEtos) {
      for (OrderPositionEto savedOrderPosition : savedOrderPositionEtos) {
        if (responseOrderPosition.getId() == savedOrderPosition.getId()) {

          assertThat(responseOrderPosition.getOrderId()).isEqualTo(savedOrderPosition.getOrderId());
          assertThat(responseOrderPosition.getOfferId()).isEqualTo(savedOrderPosition.getOfferId());
          assertThat(responseOrderPosition.getOfferName()).isEqualTo(savedOrderPosition.getOfferName());
          assertThat(responseOrderPosition.getPrice()).isEqualTo(savedOrderPosition.getPrice());

          countNumberOfSavedOrderPositions++;
        }
      }
    }
    assertThat(countNumberOfSavedOrderPositions).isEqualTo(NUMBER_OF_SAMPLE_ORDER_POSITIONS);
  }

  /**
   * This test method creates a sample instance of {@link OrderCto} and saves it into the database. In addition a sample
   * instance of {@link OrderPositionEto} is created, linked to the sample {@link OrderCto} object by the id/orderId and
   * saved into the database. After that another {@link OrderCto} object comprising a {@link OrderEto} object with the
   * same id/orderId as the the previously saved {@link OrderCto}/{@link OrderEto} object is saved into the database.
   * The resulting effect of the all {@link OrderPositionEto} objects being cancelled is tested.
   */
  @Test
  public void cancelOrderPosition() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderPositionEto sampleOrderPositionEto =
        this.helper.createSampleOrderPositionEto(responseOrderCto.getOrder().getId());
    OrderPositionEto responseOrderPositionEto = this.service.saveOrderPosition(sampleOrderPositionEto);
    assertThat(responseOrderPositionEto).isNotNull();

    OrderEto sampleOrderEto = new OrderEto();
    sampleOrderEto.setId(responseOrderCto.getOrder().getId());
    sampleOrderCto.setOrder(sampleOrderEto);

    // when
    this.service.saveOrder(sampleOrderCto);

    // then
    responseOrderPositionEto = this.service.findOrderPosition(responseOrderPositionEto.getId());
    assertThat(responseOrderPositionEto.getState()).isEqualTo(OrderPositionState.CANCELLED);
    assertThat(responseOrderCto.getOrder().getId()).isEqualTo(sampleOrderCto.getOrder().getId());
  }

  /**
   * This test method loads all saved {@link OrderCto} objects from the database, counts the number and returns it.
   *
   * @return number of orders saved in the database
   */
  protected int getNumberOfOrders() {

    int numberOfOrders = 0;
    PaginatedListTo<OrderCto> orders = this.service.findOrders(null);
    if (orders != null) {
      numberOfOrders = orders.getResult().size();
    }
    return numberOfOrders;
  }

  /**
   * This test method loads all saved {@link OrderPositionEto} objects from the database, counts the number and returns
   * it.
   *
   * @return number of orderPositions saved in the database
   */
  protected int getNumberOfOrderPositions() {

    int numberOfOrderPositions = 0;
    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(null);
    if (orderPositions != null) {
      numberOfOrderPositions = orderPositions.size();
    }
    return numberOfOrderPositions;
  }
}