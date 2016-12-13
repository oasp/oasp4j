package io.oasp.gastronomy.restaurant.general.common.api.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;

public class OrderEtoBuilder {

  private List<P<OrderEto>> parameterToBeApplied;

  public OrderEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  public OrderEtoBuilder tableId(final long tableId) {

    this.parameterToBeApplied.add(new P<OrderEto>() {
      @Override
      public void apply(OrderEto target) {

        target.setTableId(tableId);
      }
    });
    return this;
  }

  public OrderEtoBuilder state(final OrderState state) {

    this.parameterToBeApplied.add(new P<OrderEto>() {
      @Override
      public void apply(OrderEto target) {

        target.setState(state);
      }
    });
    return this;
  }

  public OrderEtoBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<OrderEto>() {
      @Override
      public void apply(OrderEto target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  public OrderEto createNew() {

    OrderEto ordereto = new OrderEto();
    for (P<OrderEto> parameter : this.parameterToBeApplied) {
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
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

}
