package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.math.BigDecimal;

import org.flywaydb.core.Flyway;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 */

public class SalesmanagementRestServiceTestHelper {

  protected JacksonJsonProvider jacksonJsonProvider;

  // TODO just workaraound, as Jonas solution is not yet approved

  protected Flyway flyway;

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
    this.jacksonJsonProvider = jacksonJsonProvider;
    this.flyway = flyway;
    this.salesmanagement = salesmanagement;
  }

  protected static final String ROLE = "chief";

  protected static final long SAMPLE_OFFER_ID = 4L;

  protected static final int NUMBER_OF_SAMPLE_ORDER_POSITIONS = 2;

  protected static final String SAMPLE_OFFER_NAME = "Salat-Menü";

  protected static final OrderState SAMPLE_ORDER_STATE = OrderState.OPEN;

  protected static final OrderPositionState SAMPLE_ORDER_POSITION_STATE = OrderPositionState.ORDERED;

  protected static final ProductOrderState SAMPLE_DRINK_STATE = ProductOrderState.DELIVERED;

  protected static final Money SAMPLE_PRICE = new Money(new BigDecimal("5.99"));

  protected static final String SAMPLE_COMMENT = null;

  protected static final long SAMPLE_TABLE_ID = 101;

  protected static final String BASE_URL_PRAEFIX = "http://localhost:";

  protected static final String BASE_URL_SUFFIX_1 = "/services/rest";

  protected static final String BASE_URL_SUFFIX_2 = "/salesmanagement/v1/";

  protected static long numberOfOrderPositions = 0;

  // @PostConstruct
  public void init() {

    this.flyway.clean();
    this.flyway.migrate();

  }

  public OrderPositionEto createSampleOrderPositionEto(long orderId) {

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
}
