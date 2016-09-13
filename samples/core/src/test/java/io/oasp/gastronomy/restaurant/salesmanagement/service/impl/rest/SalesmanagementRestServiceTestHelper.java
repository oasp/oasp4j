package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.math.BigDecimal;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

/**
 * This is a helper class for the classes {@link SalesmanagementRestServiceTest} and
 * {@link SalesmanagementHttpRestServiceTest}. It capsulates some sample attributes as constants used both testing
 * classes. Moreover it provides two methods to create sample {@link OrderCto} and {@link OrderPositionEto} objects
 * using the sample attributes defined by this class.
 *
 */
public class SalesmanagementRestServiceTestHelper {

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

  /**
   * This method creates a sample {@link OrderCto} depending on the constants defined by this class.
   *
   * @param tableId
   * @return {@link OrderCto}
   */
  protected OrderCto createSampleOrderCto(long tableId) {

    OrderCto sampleOrderCto = new OrderCto();
    OrderEto sampleOrderEto = new OrderEto();
    sampleOrderEto.setTableId(SAMPLE_TABLE_ID);
    sampleOrderCto.setOrder(sampleOrderEto);

    return sampleOrderCto;
  }

  /**
   * This method creates a sample {@link OrderPositionEto} depending on the constants defined by this class.
   *
   * @param orderId
   * @return {@link OrderPositionEto}
   */
  public OrderPositionEto createSampleOrderPositionEto(long orderId) {

    OrderPositionEto sampleOrderPositionEto = new OrderPositionEto();
    sampleOrderPositionEto.setOrderId(orderId);
    sampleOrderPositionEto.setOfferId(SAMPLE_OFFER_ID);
    sampleOrderPositionEto.setOfferName(SAMPLE_OFFER_NAME);
    sampleOrderPositionEto.setState(SAMPLE_ORDER_POSITION_STATE);
    sampleOrderPositionEto.setDrinkState(SAMPLE_DRINK_STATE);
    sampleOrderPositionEto.setPrice(SAMPLE_PRICE);
    sampleOrderPositionEto.setComment(SAMPLE_COMMENT);

    return sampleOrderPositionEto;
  }

}
