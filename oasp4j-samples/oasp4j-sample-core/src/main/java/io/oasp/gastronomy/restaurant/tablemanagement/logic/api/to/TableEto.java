package io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;

/**
 * {@link AbstractEto ETO} for {@link Table}.
 *
 * @author etomety
 */
public class TableEto extends AbstractEto implements Table {

  private static final long serialVersionUID = 1L;

  private Long waiterId;

  private Long number;

  private TableState state;

  /**
   * The constructor.
   */
  public TableEto() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getNumber() {

    return this.number;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setNumber(Long number) {

    this.number = number;
  }

  /**
   * {@inheritDoc}
   */
  public Long getWaiterId() {

    return this.waiterId;
  }

  /**
   * {@inheritDoc}
   */
  public void setWaiterId(Long waiterId) {

    this.waiterId = waiterId;
  }

  /**
   * {@inheritDoc}
   */
  public TableState getState() {

    return this.state;
  }

  /**
   * {@inheritDoc}
   */
  public void setState(TableState state) {

    this.state = state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
    result = prime * result + ((this.waiterId == null) ? 0 : this.waiterId.hashCode());
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
    if (getClass() != obj.getClass()) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    TableEto other = (TableEto) obj;
    if (this.state != other.state) {
      return false;
    }
    if (this.waiterId == null) {
      if (other.waiterId != null) {
        return false;
      }
    } else if (!this.waiterId.equals(other.waiterId)) {
      return false;
    }
    return true;
  }
}
