package io.oasp.gastronomy.restaurant.general.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;

/**
 * Cobigen generated builder for {@link BillEntity}. Default values can be set in method {@link createNew()}.
 *
 * @author sroeger
 */
public class BillEntityBuilder {

  private List<P<BillEntity>> parameterToBeApplied;

  /**
   * The constructor of a Cobigen generated builder for {@link BillEntity}.
   */
  public BillEntityBuilder() {

    this.parameterToBeApplied = new LinkedList<P<BillEntity>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

  public BillEntityBuilder orderPositions(final List<OrderPositionEntity> orderPositions) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setOrderPositions(orderPositions);
      }
    });
    return this;
  }

  public BillEntityBuilder total(final Money total) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setTotal(total);
      }
    });
    return this;
  }

  public BillEntityBuilder tip(final Money tip) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setTip(tip);
      }
    });
    return this;
  }

  public BillEntityBuilder payed(final boolean payed) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setPayed(payed);
      }
    });
    return this;
  }

  public BillEntityBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  public BillEntityBuilder orderPositionIds(final List orderPositionIds) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setOrderPositionIds(orderPositionIds);
      }
    });
    return this;
  }

  public BillEntity createNew() {

    BillEntity billentity = new BillEntity();
    for (P<BillEntity> parameter : this.parameterToBeApplied) {
      parameter.apply(billentity);
    }
    return billentity;
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

}
