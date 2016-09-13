package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.module.basic.common.api.to.AbstractCto;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractCto CTO} for an {@link #getBill() bill} with its {@link #getPositions() positions}.
 *
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

}
