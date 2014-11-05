#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api.to;

import ${package}.general.common.api.datatype.Money;
import ${package}.general.common.api.to.AbstractEto;
import ${package}.salesmanagement.common.api.Bill;

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

  private Money totalAmount;

  private Money tip;

  /**
   * The constructor.
   */
  public BillEto() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Long> getOrderPositionIds() {

    return this.orderPositionIds;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOrderPositionIds(List<Long> orderPositions) {

    this.orderPositionIds = orderPositions;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Money getTotalAmount() {

    return this.totalAmount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTotalAmount(Money totalAmount) {

    this.totalAmount = totalAmount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Money getTip() {

    return this.tip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTip(Money tip) {

    this.tip = tip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isPayed() {

    return this.payed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPayed(boolean payed) {

    this.payed = payed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.orderPositionIds == null) ? 0 : this.orderPositionIds.hashCode());
    result = prime * result + (this.payed ? 1 : 0);
    result = prime * result + ((this.tip == null) ? 0 : this.tip.hashCode());
    result = prime * result + ((this.totalAmount == null) ? 0 : this.totalAmount.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
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
    if (Objects.equals(this.totalAmount, other.totalAmount)) {
      return false;
    }
    if (Objects.equals(this.tip, other.tip)) {
      return false;
    }
    return true;
  }

}
