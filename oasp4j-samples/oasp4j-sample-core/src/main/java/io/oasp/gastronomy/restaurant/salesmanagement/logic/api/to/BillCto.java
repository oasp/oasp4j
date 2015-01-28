package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractCto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link AbstractCto CTO} for an {@link #getBill() bill} with its {@link #getPositions() positions}.
 *
 * @author jmetzler
 */
public class BillCto extends AbstractCto {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private BillEto bill;

  private List<OrderPositionEto> positions;

  /**
   * The constructor.
   */
  public BillCto() {

    super();
  }

  /**
   * @return positions
   */
  public List<OrderPositionEto> getPositions() {

    if (this.positions == null) {
      this.positions = new ArrayList<>();
    }
    return this.positions;
  }

  /**
   * @param positions the positions to set
   */
  public void setPositions(List<OrderPositionEto> positions) {

    this.positions = positions;
  }

  /**
   * @param bill the bill to set
   */
  public void setBill(BillEto bill) {

    this.bill = bill;
  }

  /**
   * @return bill
   */
  public BillEto getBill() {

    return this.bill;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.bill == null) ? 0 : this.bill.hashCode());
    result = prime * result + ((this.positions == null) ? 0 : this.positions.hashCode());
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
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    BillCto other = (BillCto) obj;
    if (Objects.equals(this.bill, other.bill)) {
      return false;
    }
    if (Objects.equals(this.positions, other.positions)) {
      return false;
    }
    return true;
  }

}
