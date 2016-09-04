package io.oasp.gastronomy.restaurant.common.builders;

import static io.oasp.gastronomy.restaurant.salesmanagement.common.SalesmanagementTestDataConstants.ID_ORDERPOSITION_SCHNITZELMENUE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;

public class BillEntityBuilder {

  private List<P<BillEntity>> parameterToBeApplied;

  public static final boolean FLAG_BILL_PAYED = true;

  public static final double TOTAL_BILL = 42.42;

  public static final Money TOTAL_BILL_AS_MONEY = new Money(new BigDecimal(Double.toString(TOTAL_BILL)));

  public static final double TIP_BILL = 1.0;

  public static final Money TIP_BILL_AS_MONEY = new Money(new BigDecimal(Double.toString(TIP_BILL)));

  public BillEntityBuilder() {

    this.parameterToBeApplied = new LinkedList<P<BillEntity>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
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

    ArrayList<Long> sampeOrderPositionIdList = new ArrayList<>();
    sampeOrderPositionIdList.add(ID_ORDERPOSITION_SCHNITZELMENUE);
    billentity.setTotal(TOTAL_BILL_AS_MONEY);
    billentity.setTip(TIP_BILL_AS_MONEY);
    billentity.setPayed(FLAG_BILL_PAYED);
    billentity.setOrderPositionIds(sampeOrderPositionIdList);

    for (P<BillEntity> parameter : this.parameterToBeApplied) {
      parameter.apply(billentity);
    }
    return billentity;
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

}
