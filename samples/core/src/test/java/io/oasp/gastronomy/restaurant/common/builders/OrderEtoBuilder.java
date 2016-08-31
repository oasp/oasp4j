package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;

/**
 * Test data builder for OrderEto generated with cobigen.
 */
public class OrderEtoBuilder {

  private List<P<OrderEto>> parameterToBeApplied;

  /**
   * The constructor.
   */
  public OrderEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * @param tableId the tableId to add.
   * @return the builder for fluent population of fields.
   */
  public OrderEtoBuilder tableId(final long tableId) {

    this.parameterToBeApplied.add(new P<OrderEto>() {
      @Override
      public void apply(OrderEto target) {

        target.setTableId(tableId);
      }
    });
    return this;
  }

  /**
   * @param state the state to add.
   * @return the builder for fluent population of fields.
   */
  public OrderEtoBuilder state(final OrderState state) {

    this.parameterToBeApplied.add(new P<OrderEto>() {
      @Override
      public void apply(OrderEto target) {

        target.setState(state);
      }
    });
    return this;
  }

  /**
   * @param revision the revision to add.
   * @return the builder for fluent population of fields.
   */
  public OrderEtoBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<OrderEto>() {
      @Override
      public void apply(OrderEto target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  /**
   * @return the populated OrderEto.
   */
  public OrderEto createNew() {

    OrderEto ordereto = new OrderEto();
    for (P<OrderEto> parameter : parameterToBeApplied) {
      parameter.apply(ordereto);
    }
    return ordereto;
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

  /**
   * Might be enriched to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

}
