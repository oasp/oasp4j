package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.validation.NotNegativeMoney;
import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.Bill;

import java.util.List;

/**
 * {@link AbstractEto ETO} for {@link Bill}.
 *
 * @author etomety
 */
public class BillEto extends AbstractEto implements Bill {

  private static final long serialVersionUID = 1L;

  private boolean payed;

  private List<Long> orderPositionIds;

  private Money total;

  private Money tip;

  /**
   * The constructor.
   */
  public BillEto() {

    super();
  }

  @Override
  public List<Long> getOrderPositionIds() {

    return this.orderPositionIds;
  }

  @Override
  public void setOrderPositionIds(List<Long> orderPositions) {

    this.orderPositionIds = orderPositions;
  }

  @Override
  public Money getTotal() {

    return this.total;
  }

  @Override
  public void setTotal(Money total) {

    this.total = total;
  }

  @Override
  public Money getTip() {

    return this.tip;
  }

  @Override
  @NotNegativeMoney(message = "The tip must not be negative!")
  public void setTip(Money tip) {

    this.tip = tip;
  }

  @Override
  public boolean isPayed() {

    return this.payed;
  }

  @Override
  public void setPayed(boolean payed) {

    this.payed = payed;
  }

}
