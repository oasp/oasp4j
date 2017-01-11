package io.oasp.gastronomy.restaurant.general.common.api.builders;

import static io.oasp.gastronomy.restaurant.offermanagement.common.constants.OffermanagementTestDataConstants.ID_OFFER_SCHNITZELMENUE;
import static io.oasp.gastronomy.restaurant.salesmanagement.common.constants.SalesmanagementTestDataConstants.ID_ORDER;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

public class OrderPositionEtoBuilder {

  private List<P<OrderPositionEto>> parameterToBeApplied;

  public static final String COMMENT_ORDERPOSITION = "mit Ketchup";

  public static final OrderPositionState STATE_ORDERPOSITION = OrderPositionState.ORDERED;

  public static final ProductOrderState STATE_PRODUCT_DRINK = ProductOrderState.DELIVERED;

  public static final String NAME_OFFER_SCHNITZELMENUE = "Schnitzel-Menü";

  public static final double PRICE_OFFER_SCHNITZELMENUE = 6.99;

  public static final Money PRICE_OFFER_SCHNITZELMENUE_AS_MONEY =
      new Money(new BigDecimal(Double.toString(PRICE_OFFER_SCHNITZELMENUE)));

  public OrderPositionEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

    orderId(ID_ORDER);
    offerId(ID_OFFER_SCHNITZELMENUE);
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

  public OrderPositionEtoBuilder orderId(final Long orderId) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setOrderId(orderId);
      }
    });
    return this;
  }

  public OrderPositionEtoBuilder cookId(final Long cookId) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setCookId(cookId);
      }
    });
    return this;
  }

  public OrderPositionEtoBuilder offerId(final Long offerId) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setOfferId(offerId);
      }
    });
    return this;
  }

  public OrderPositionEtoBuilder offerName(final String offerName) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setOfferName(offerName);
      }
    });
    return this;
  }

  public OrderPositionEtoBuilder state(final OrderPositionState state) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setState(state);
      }
    });
    return this;
  }

  public OrderPositionEtoBuilder price(final Money price) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setPrice(price);
      }
    });
    return this;
  }

  public OrderPositionEtoBuilder comment(final String comment) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setComment(comment);
      }
    });
    return this;
  }

  public OrderPositionEtoBuilder drinkState(final ProductOrderState drinkState) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setDrinkState(drinkState);
      }
    });
    return this;
  }

  public OrderPositionEtoBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  public OrderPositionEto createNew() {

    OrderPositionEto orderpositioneto = new OrderPositionEto();

    orderpositioneto.setComment(COMMENT_ORDERPOSITION);
    orderpositioneto.setState(STATE_ORDERPOSITION);
    orderpositioneto.setDrinkState(STATE_PRODUCT_DRINK);

    for (P<OrderPositionEto> parameter : this.parameterToBeApplied) {
      parameter.apply(orderpositioneto);
    }
    return orderpositioneto;
  }

}
