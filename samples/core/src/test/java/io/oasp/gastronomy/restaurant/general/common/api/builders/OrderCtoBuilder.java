package io.oasp.gastronomy.restaurant.general.common.api.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

public class OrderCtoBuilder {

  private List<P<OrderCto>> parameterToBeApplied;

  public OrderCtoBuilder() {

    this.parameterToBeApplied = new LinkedList<P<OrderCto>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

  public OrderCtoBuilder order(final OrderEto order) {

    this.parameterToBeApplied.add(new P<OrderCto>() {
      @Override
      public void apply(OrderCto target) {

        target.setOrder(order);
      }
    });
    return this;
  }

  public OrderCtoBuilder positions(final List<OrderPositionEto> positions) {

    this.parameterToBeApplied.add(new P<OrderCto>() {
      @Override
      public void apply(OrderCto target) {

        target.setPositions(positions);
      }
    });
    return this;
  }

  public OrderCto createNew() {

    OrderCto ordercto = new OrderCto();
    for (P<OrderCto> parameter : this.parameterToBeApplied) {
      parameter.apply(ordercto);
    }
    return ordercto;
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

    order(new OrderEtoBuilder().createNew());
  }
}