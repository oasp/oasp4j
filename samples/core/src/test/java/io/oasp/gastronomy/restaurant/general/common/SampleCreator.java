package io.oasp.gastronomy.restaurant.general.common;

import java.math.BigDecimal;
import java.util.ArrayList;

import io.oasp.gastronomy.restaurant.common.builders.BillEntityBuilder;
import io.oasp.gastronomy.restaurant.common.builders.DrinkEntityBuilder;
import io.oasp.gastronomy.restaurant.common.builders.OrderCtoBuilder;
import io.oasp.gastronomy.restaurant.common.builders.OrderEtoBuilder;
import io.oasp.gastronomy.restaurant.common.builders.OrderPositionEtoBuilder;
import io.oasp.gastronomy.restaurant.common.builders.TableEtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * This class creates sample instances with sample attributes used for testing.
 *
 * @author shuber
 */

public class SampleCreator {

  // offermanagement
  public static final long SAMPLE_OFFER_ID = 1L;

  public static final String SAMPLE_OFFER_NAME = "Schnitzel-Menü";

  public static final String SAMPLE_OFFER_DESCRIPTION = "Description of Schnitzel-Menü";

  public static final long SAMPLE_OFFER_SIDEDISH_ID = 7;

  public static final long SAMPLE_OFFER_DRINK_ID = 12;

  public static final int NUMBER_OF_MEALS = 6;

  public static final long SAMPLE_MEAL_ID = 1;

  public static final String SAMPLE_MEAL_DESCRIPTION = "Schnitzel";

  public static final int NUMBER_OF_SIDEDISHES = 4;

  public static final int NUMBER_OF_DRINKS = 4;

  public static final boolean NEW_ALCOHOLIC_FLAG = false;

  public static final String NEW_DRINK_NAME = "Spring Paradise";

  public static final String NEW_DRINK_DESCRIPTION = "without alcohol";

  // salesmanagement
  public static final long SAMPLE_ORDER_ID = 1;

  public static final int NUMBER_OF_NEW_ORDERS = 2;

  public static final OrderState SAMPLE_ORDER_STATE = OrderState.OPEN;

  public static final long SAMPLE_ORDERPOSITION_ID = 1;

  public static final double SAMPLE_ORDERPOSITION_PRICE = 6.99;

  public static final Money SAMPLE_ORDERPOSITION_PRICE_AS_MONEY =
      new Money(new BigDecimal(Double.toString(SampleCreator.SAMPLE_ORDERPOSITION_PRICE)));

  public static final OrderPositionState SAMPLE_ORDER_POSITION_STATE = OrderPositionState.ORDERED;

  public static final int NUMBER_OF_NEW_ORDER_POSITIONS = 2;

  public static final ProductOrderState SAMPLE_DRINK_STATE = ProductOrderState.DELIVERED;

  public static final String NEW_ORDER_COMMENT = "mit Ketchup";

  public static final double NEW_TOTAL = 42.42;

  public static final double NEW_TIP = 1.0;

  public static final boolean NEW_PAYED_FLAG = true;

  // tablemanagement
  public static final long SAMPLE_TABLE_ID = 102;

  public static final long NEW_TABLE_NUMBER = 7;

  public static final long SAMPLE_WAITER_ID = 2;

  // offermanagement
  /**
   * Creates a sample instance of type {@link DrinkEntity}.
   *
   * @return {@link DrinkEntity}.
   */
  public static final DrinkEntity createSampleDrinkEntity() {

    DrinkEntity sampleDrinkEntity = new DrinkEntityBuilder().alcoholic(NEW_ALCOHOLIC_FLAG)
        .description(NEW_DRINK_DESCRIPTION).name(NEW_DRINK_NAME).createNew();
    return sampleDrinkEntity;
  }

  // salesmanagement
  /**
   * Creates a sample instance of type {@link OrderEto}.
   *
   * @return {@link OrderEto}.
   */
  public static final OrderEto createSampleOrderEto() {

    OrderEto sampleOrderEto = new OrderEtoBuilder().tableId(SAMPLE_TABLE_ID).createNew();
    return sampleOrderEto;
  }

  /**
   * Creates a sample instance of type {@link OrderCto}.
   *
   * @return {@link OrderCto}.
   */
  public static final OrderCto createSampleOrderCto() {

    OrderCto sampleOrderCto = new OrderCtoBuilder().order(createSampleOrderEto()).createNew();
    return sampleOrderCto;
  }

  /**
   * Creates a sample instance of type {@link OrderPositionEto}.
   *
   * @return {@link OrderPositionEto}
   */
  public static final OrderPositionEto createSampleOrderPositionEto() {

    OrderPositionEto sampleOrderPositionEto = createSampleOrderPositionEto(SAMPLE_ORDER_ID);
    return sampleOrderPositionEto;
  }

  /**
   * Creates a sample instance of type {@link OrderPositionEto}.
   *
   * @param orderId: id of order it belongs to
   *
   * @return {@link OrderPositionEto}
   */
  public static final OrderPositionEto createSampleOrderPositionEto(long orderId) {

    OrderPositionEto sampleOrderPositionEto = new OrderPositionEtoBuilder().orderId(orderId).offerId(SAMPLE_OFFER_ID)
        .comment(NEW_ORDER_COMMENT).offerName(SAMPLE_OFFER_NAME).drinkState(SAMPLE_DRINK_STATE)
        .price(SAMPLE_ORDERPOSITION_PRICE_AS_MONEY).createNew();
    return sampleOrderPositionEto;
  }

  /**
   * Creates a sample instance of type {@link BillEntity}.
   *
   * @return {@link BillEntity}
   */
  public static final BillEntity createSampleBillEntity() {

    ArrayList sampeOrderPositionIdList = new ArrayList<Long>();
    sampeOrderPositionIdList.add(SAMPLE_ORDERPOSITION_ID);
    BillEntity sampleBillEntity = new BillEntityBuilder().orderPositionIds(sampeOrderPositionIdList)
        .total(new Money(NEW_TOTAL)).tip(new Money(NEW_TIP)).payed(NEW_PAYED_FLAG).createNew();
    return sampleBillEntity;
  }

  // tablemanagement
  /**
   * Creates a sample instance of type {@link TableEto}.
   *
   * @return {@link TableEto}
   */
  public static final TableEto createSampleTableEto() {

    TableEto sampleTableEto = new TableEtoBuilder().number(NEW_TABLE_NUMBER).waiterId(SAMPLE_WAITER_ID).createNew();
    return sampleTableEto;
  }

}
