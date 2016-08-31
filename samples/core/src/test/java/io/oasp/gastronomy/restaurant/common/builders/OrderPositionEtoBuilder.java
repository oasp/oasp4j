package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

/**
 * Test data builder for OrderPositionEto generated with cobigen.
 */
public class OrderPositionEtoBuilder {

  private List<P<OrderPositionEto>> parameterToBeApplied;

  /**
   * The constructor.
   */
  public OrderPositionEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Might be enriched to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

  /**
   * @param orderId the orderId to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder orderId(final Long orderId) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setOrderId(orderId);
      }
    });
    return this;
  }

  /**
   * @param cookId the cookId to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder cookId(final Long cookId) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setCookId(cookId);
      }
    });
    return this;
  }

  /**
   * @param offerId the offerId to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder offerId(final Long offerId) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setOfferId(offerId);
      }
    });
    return this;
  }

  /**
   * @param offerName the offerName to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder offerName(final String offerName) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setOfferName(offerName);
      }
    });
    return this;
  }

  /**
   * @param state the state to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder state(final OrderPositionState state) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setState(state);
      }
    });
    return this;
  }

  /**
   * @param price the price to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder price(final Money price) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setPrice(price);
      }
    });
    return this;
  }

  /**
   * @param comment the comment to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder comment(final String comment) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setComment(comment);
      }
    });
    return this;
  }

  /**
   * @param drinkState the drinkState to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder drinkState(final ProductOrderState drinkState) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setDrinkState(drinkState);
      }
    });
    return this;
  }

  /**
   * @param mealState the mealState to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder mealState(final ProductOrderState mealState) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setMealState(mealState);
      }
    });
    return this;
  }

  /**
   * @param sidedishState the sidedishState to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder sidedishState(final ProductOrderState sidedishState) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setSidedishState(sidedishState);
      }
    });
    return this;
  }

  /**
   * @param revision the revision to add.
   * @return the builder for fluent population of fields.
   */
  public OrderPositionEtoBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<OrderPositionEto>() {
      @Override
      public void apply(OrderPositionEto target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  /**
   * @return the populated OrderPositionEto.
   */
  public OrderPositionEto createNew() {

    OrderPositionEto orderpositioneto = new OrderPositionEto();
    // default values
    orderpositioneto.setState(OrderPositionState.ORDERED);
    orderpositioneto.setMealState(ProductOrderState.ORDERED);
    orderpositioneto.setSidedishState(ProductOrderState.ORDERED);
    orderpositioneto.setDrinkState(ProductOrderState.ORDERED);
    for (P<OrderPositionEto> parameter : this.parameterToBeApplied) {
      parameter.apply(orderpositioneto);
    }
    return orderpositioneto;
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

}
