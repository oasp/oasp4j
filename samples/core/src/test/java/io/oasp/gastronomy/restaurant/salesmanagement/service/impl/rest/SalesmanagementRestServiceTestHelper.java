package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.math.BigDecimal;
import java.util.List;

import org.flywaydb.core.Flyway;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */

public class SalesmanagementRestServiceTestHelper {

  // @Value("${local.server.port}")
  protected int port;

  /**
   * @return port
   */
  public int getPort() {

    return this.port;
  }

  /**
   * @param port new value of {@link #getport}.
   */
  public void setPort(int port) {

    this.port = port;
  }

  // @Inject
  protected JacksonJsonProvider jacksonJsonProvider;

  // TODO just workaraound, as Jonas solution is not yet approved
  // @Inject
  protected Flyway flyway;

  // @Inject
  protected Salesmanagement salesmanagement;

  /**
   * The constructor.
   *
   * @param jacksonJsonProvider
   * @param flyway
   * @param salesmanagement
   */
  public SalesmanagementRestServiceTestHelper(JacksonJsonProvider jacksonJsonProvider, Flyway flyway,
      Salesmanagement salesmanagement) {
    super();
    this.jacksonJsonProvider = jacksonJsonProvider;
    this.flyway = flyway;
    this.salesmanagement = salesmanagement;
  }

  protected static long INITIAL_NUMBER_OF_ORDERS = 0;

  protected static long INITIAL_NUMBER_OF_ORDER_POSITIONS = 0;

  protected static final String ROLE = "chief";

  protected static final long SAMPLE_OFFER_ID = 6L;

  protected static final String SAMPLE_OFFER_NAME = "Pizza-Menü";

  protected static final OrderState SAMPLE_ORDER_STATE = OrderState.OPEN;

  protected static final OrderPositionState SAMPLE_ORDER_POSITION_STATE = OrderPositionState.ORDERED;

  protected static final ProductOrderState SAMPLE_DRINK_STATE = ProductOrderState.DELIVERED;

  protected static final Money SAMPLE_PRICE = new Money(new BigDecimal("6.23"));

  protected static final String SAMPLE_COMMENT = null;

  protected static final long SAMPLE_TABLE_ID = 101;

  protected SalesmanagementRestService service;

  protected static final String BASE_URL_PRAEFIX = "http://localhost:";

  protected static final String BASE_URL_SUFFIX_1 = "/services/rest";

  protected static final String BASE_URL_SUFFIX_2 = "/salesmanagement/v1/";

  protected static long numberOfOrderPositions = 0;

  /**
   * @return service
   */
  public SalesmanagementRestService getService() {

    return this.service;
  }

  // @PostConstruct
  public void init() {

    this.service = RestTestClientBuilder.build(SalesmanagementRestService.class, ROLE, ROLE,
        BASE_URL_PRAEFIX + this.port + BASE_URL_SUFFIX_1, this.jacksonJsonProvider);
    INITIAL_NUMBER_OF_ORDER_POSITIONS = getNumberOfOrderPositions();
    INITIAL_NUMBER_OF_ORDERS = getNumberOfOrders();
  }

  // @PreDestroy
  // public void destroy() {
  //
  // }

  // // @Before
  // public void prepareTest() {
  //
  // flyway.clean();
  // flyway.migrate();
  // }

  protected OrderPositionEto createSampleOrderPositionEto(long orderId) {

    OrderPositionEto orderPositionEto = new OrderPositionEto();
    orderPositionEto.setOrderId(orderId);
    orderPositionEto.setOfferId(SAMPLE_OFFER_ID);
    orderPositionEto.setOfferName(SAMPLE_OFFER_NAME);
    orderPositionEto.setState(SAMPLE_ORDER_POSITION_STATE);
    orderPositionEto.setDrinkState(SAMPLE_DRINK_STATE);
    orderPositionEto.setPrice(SAMPLE_PRICE);
    orderPositionEto.setComment(SAMPLE_COMMENT);
    return orderPositionEto;
  }

  protected OrderCto createSampleOrderCto(long tableId) {

    OrderCto sampleOrderCto = new OrderCto();
    OrderEto sampleOrderEto = new OrderEto();
    sampleOrderEto.setTableId(tableId);
    sampleOrderCto.setOrder(sampleOrderEto);
    return sampleOrderCto;
  }

  protected long getNumberOfOrders() {

    long numberOfOrders = 0;
    PaginatedListTo<OrderCto> orderPositions = this.service.findOrders(null);
    if (orderPositions != null) {
      numberOfOrders = orderPositions.getResult().size();
    }
    return numberOfOrders;
  }

  protected long getNumberOfOrderPositions() {

    long numberOfOrderPositions = 0;
    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(null);
    if (orderPositions != null) {
      numberOfOrderPositions = orderPositions.size();
    }
    return numberOfOrderPositions;
  }

}
