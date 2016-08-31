package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;

/**
 * Test data builder for BillEntity generated with cobigen.
 */
public class BillEntityBuilder {

  private List<P<BillEntity>> parameterToBeApplied;

  /**
   * The constructor.
   */
  public BillEntityBuilder() {

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
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

  /**
   * @param orderPositions the orderPositions to add.
   * @return the builder for fluent population of fields.
   */
  public BillEntityBuilder orderPositions(final List<OrderPositionEntity> orderPositions) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setOrderPositions(orderPositions);
      }
    });
    return this;
  }

  /**
   * @param total the total to add.
   * @return the builder for fluent population of fields.
   */
  public BillEntityBuilder total(final Money total) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setTotal(total);
      }
    });
    return this;
  }

  /**
   * @param tip the tip to add.
   * @return the builder for fluent population of fields.
   */
  public BillEntityBuilder tip(final Money tip) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setTip(tip);
      }
    });
    return this;
  }

  /**
   * @param payed the payed to add.
   * @return the builder for fluent population of fields.
   */
  public BillEntityBuilder payed(final boolean payed) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setPayed(payed);
      }
    });
    return this;
  }

  /**
   * @param revision the revision to add.
   * @return the builder for fluent population of fields.
   */
  public BillEntityBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  /**
   * @param orderPositionIds the orderPositionIds to add.
   * @return the builder for fluent population of fields.
   */
  public BillEntityBuilder orderPositionIds(final List orderPositionIds) {

    this.parameterToBeApplied.add(new P<BillEntity>() {
      @Override
      public void apply(BillEntity target) {

        target.setOrderPositionIds(orderPositionIds);
      }
    });
    return this;
  }

  /**
   * @return the populated BillEntity.
   */
  public BillEntity createNew() {

    BillEntity billentity = new BillEntity();
    for (P<BillEntity> parameter : parameterToBeApplied) {
      parameter.apply(billentity);
    }
    return billentity;
  }

}
