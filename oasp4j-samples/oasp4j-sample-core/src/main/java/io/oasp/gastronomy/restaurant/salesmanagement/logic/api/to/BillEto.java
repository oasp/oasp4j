package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.Bill;

import java.util.List;
import java.util.Objects;

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

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.orderPositionIds == null) ? 0 : this.orderPositionIds.hashCode());
    result = prime * result + (this.payed ? 1 : 0);
    result = prime * result + ((this.tip == null) ? 0 : this.tip.hashCode());
    result = prime * result + ((this.total == null) ? 0 : this.total.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    BillEto other = (BillEto) obj;
    if (Objects.equals(this.orderPositionIds, other.orderPositionIds)) {
      return false;
    }
    if (this.payed != other.payed) {
      return false;
    }
    if (Objects.equals(this.total, other.total)) {
      return false;
    }
    if (Objects.equals(this.tip, other.tip)) {
      return false;
    }
    return true;
  }

}
