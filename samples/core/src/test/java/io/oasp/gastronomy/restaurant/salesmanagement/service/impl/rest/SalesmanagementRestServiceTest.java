package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import static io.oasp.gastronomy.restaurant.general.common.api.builders.OrderEtoBuilder.STATE_ORDER;
import static io.oasp.gastronomy.restaurant.general.common.api.builders.OrderPositionEtoBuilder.NAME_OFFER_SCHNITZELMENUE;
import static io.oasp.gastronomy.restaurant.general.common.api.builders.OrderPositionEtoBuilder.PRICE_OFFER_SCHNITZELMENUE_AS_MONEY;
import static io.oasp.gastronomy.restaurant.general.common.api.builders.OrderPositionEtoBuilder.STATE_ORDERPOSITION;
import static io.oasp.gastronomy.restaurant.general.common.api.builders.OrderPositionEtoBuilder.STATE_PRODUCT_DRINK;
import static io.oasp.gastronomy.restaurant.offermanagement.common.constants.OffermanagementTestDataConstants.ID_OFFER_SCHNITZELMENUE;
import static io.oasp.gastronomy.restaurant.tablemanagement.common.constants.TablemanagementTestDataConstants.ID_TABLE;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.api.builders.OrderCtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.api.builders.OrderPositionEtoBuilder;
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
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { SpringBootApp.class, SalesmanagementRestTestConfig.class })
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement" })

public class SalesmanagementRestServiceTest extends AbstractRestServiceTest {

  private SalesmanagementRestService service;

  protected static final String ROLE = "chief";

  /**
   * Provides initialization previous to the creation of the text fixture.
   */
  @Before
  public void init() {

    getDbTestHelper().resetDatabase();
    this.service = getRestTestClientBuilder().build(SalesmanagementRestService.class);
  }

  /**
   * Provides clean up after tests.
   */
  @After
  public void clean() {

    this.service = null;
  }

  /**
   * This test method creates a sample instance of {@link OrderCto} and saves it into the database. Thereafter the
   * {@link OrderEto} object linked to the sample object of {@link OrderCto} is loaded from the database and its
   * attributes are tested for correctness.
   */
  @Test
  public void findOrder() {

    // given
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    // when
    OrderEto responseOrderEto = this.service.findOrder(responseOrderCto.getOrder().getId());

    // then
    assertThat(responseOrderEto).isNotNull();
    assertThat(responseOrderEto.getId()).isEqualTo(responseOrderCto.getOrder().getId());
    assertThat(responseOrderEto.getTableId()).isEqualTo(ID_TABLE);
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

    int numberOfSampleOrders = 2;

    ArrayList<OrderCto> savedOrderCtos = new ArrayList<>();
    for (int i = 0; i < numberOfSampleOrders; ++i) {

      sampleOrderCto = new OrderCtoBuilder().createNew();
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

    assertThat(oldNumberOfOrders + numberOfSampleOrders).isEqualTo(newNumberOfOrders);
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
    assertThat(countNumberOfSavedOrders).isEqualTo(numberOfSampleOrders);

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
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setTableId(ID_TABLE);
    criteria.setState(STATE_ORDER);
    PaginationTo pagination = PaginationTo.NO_PAGINATION;
    criteria.setPagination(pagination);

    // when
    PaginatedListTo<OrderCto> orderCtoList = this.service.findOrdersByPost(criteria);

    // then
    assertThat(orderCtoList).isNotNull();
    assertThat(orderCtoList.getResult().size()).isEqualTo(1);
    assertThat(orderCtoList.getResult().get(0).getOrder().getId()).isEqualTo(responseOrderCto.getOrder().getId());
    assertThat(orderCtoList.getResult().get(0).getOrder().getTableId()).isEqualTo(ID_TABLE);
    assertThat(orderCtoList.getResult().get(0).getOrder().getState()).isEqualTo(STATE_ORDER);

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
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderPositionEto sampleOrderPositionEto =
        new OrderPositionEtoBuilder().orderId(responseOrderCto.getOrder().getId()).createNew();
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
    assertThat(responseFindOrderPositionEto.getOfferId()).isEqualTo(ID_OFFER_SCHNITZELMENUE);
    assertThat(responseFindOrderPositionEto.getOfferName()).isEqualTo(NAME_OFFER_SCHNITZELMENUE);
    assertThat(responseFindOrderPositionEto.getState()).isEqualTo(STATE_ORDERPOSITION);
    assertThat(responseFindOrderPositionEto.getDrinkState()).isEqualTo(STATE_PRODUCT_DRINK);
    assertThat(responseFindOrderPositionEto.getPrice()).isEqualTo(PRICE_OFFER_SCHNITZELMENUE_AS_MONEY);
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
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    int oldNumberOfOrderPositions = getNumberOfOrderPositions();
    OrderPositionEto sampleOrderPositionEto;
    OrderPositionEto responseOrderPositionEto;

    ArrayList<OrderPositionEto> savedOrderPositionEtos = new ArrayList<>();
    int numberOfSampleOrderpositions = 2;

    for (int i = 0; i < numberOfSampleOrderpositions; ++i) {
      sampleOrderPositionEto = new OrderPositionEtoBuilder().orderId(responseOrderCto.getOrder().getId()).createNew();
      responseOrderPositionEto = this.service.saveOrderPosition(sampleOrderPositionEto);
      assertThat(responseOrderPositionEto).isNotNull();
      savedOrderPositionEtos.add(responseOrderPositionEto);
    }

    int newNumberOfOrderPositions = getNumberOfOrderPositions();

    // when
    List<OrderPositionEto> responseOrderPositionEtos = this.service.findOrderPositions(null);

    // then
    assertThat(responseOrderPositionEtos).isNotNull();

    assertThat(oldNumberOfOrderPositions + numberOfSampleOrderpositions).isEqualTo(newNumberOfOrderPositions);
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
    assertThat(countNumberOfSavedOrderPositions).isEqualTo(numberOfSampleOrderpositions);
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
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderPositionEto sampleOrderPositionEto =
        new OrderPositionEtoBuilder().orderId(responseOrderCto.getOrder().getId()).createNew();
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