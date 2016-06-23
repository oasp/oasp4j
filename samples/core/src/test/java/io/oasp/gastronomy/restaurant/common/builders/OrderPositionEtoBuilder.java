package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

/**
 * Cobigen generated builder for {@link OrderPositionEto}. Default values can be set in method {@link createNew()}.
 *
 * @author sroeger
 */
public class OrderPositionEtoBuilder {

  private List<P<OrderPositionEto>> parameterToBeApplied;

  /**
   * The constructor of a Cobigen generated builder for {@link OrderPositionEto}.
   */
  public OrderPositionEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<P<OrderPositionEto>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

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
    for (P<OrderPositionEto> parameter : this.parameterToBeApplied) {
      parameter.apply(orderpositioneto);
    }
    return orderpositioneto;
  }

}
