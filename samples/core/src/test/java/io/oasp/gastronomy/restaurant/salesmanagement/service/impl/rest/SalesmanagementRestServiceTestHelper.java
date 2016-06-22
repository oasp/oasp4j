package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.math.BigDecimal;

import io.oasp.gastronomy.restaurant.common.builders.OrderEtoBuilder;
import io.oasp.gastronomy.restaurant.common.builders.OrderPositionEtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 */

public class SalesmanagementRestServiceTestHelper {

  /**
   * The constructor.
   *
   * @param jacksonJsonProvider
   * @param flyway
   * @param salesmanagement
   */

  protected static final String ROLE = "chief";

  protected static final long SAMPLE_OFFER_ID = 4L;

  protected static final int NUMBER_OF_SAMPLE_ORDERS = 2;

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

  public OrderPositionEto createSampleOrderPositionEto(long orderId) {

    OrderPositionEto sampleOrderPositionEto = new OrderPositionEtoBuilder().orderId(orderId).offerId(SAMPLE_OFFER_ID)
        .offerName(SAMPLE_OFFER_NAME).state(SAMPLE_ORDER_POSITION_STATE).drinkState(SAMPLE_DRINK_STATE)
        .price(SAMPLE_PRICE).comment(SAMPLE_COMMENT).createNew();
    return sampleOrderPositionEto;
  }

  protected OrderCto createSampleOrderCto(long tableId) {

    OrderCto sampleOrderCto = new OrderCto();
    OrderEto sampleOrderEto = new OrderEtoBuilder().tableId(SAMPLE_TABLE_ID).createNew();
    sampleOrderCto.setOrder(sampleOrderEto);
    return sampleOrderCto;
  }
}
