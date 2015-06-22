package io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;

import javax.validation.constraints.Max;

/**
 * {@link AbstractEto ETO} for {@link Table}.
 *
 * @author etomety
 */
public class TableEto extends AbstractEto implements Table {

  private static final long serialVersionUID = 1L;

  private Long waiterId;

  @Max(value = 1000)
  private Long number;

  private TableState state;

  /**
   * The constructor.
   */
  public TableEto() {

    super();
  }

  @Override
  public Long getNumber() {

    return this.number;
  }

  @Override
  public void setNumber(Long number) {

    this.number = number;
  }

  @Override
  public Long getWaiterId() {

    return this.waiterId;
  }

  @Override
  public void setWaiterId(Long waiterId) {

    this.waiterId = waiterId;
  }

  @Override
  public TableState getState() {

    return this.state;
  }

  @Override
  public void setState(TableState state) {

    this.state = state;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
    result = prime * result + ((this.waiterId == null) ? 0 : this.waiterId.hashCode());
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
