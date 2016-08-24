package io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.module.basic.common.api.to.AbstractEto;

import javax.validation.constraints.Max;

/**
 * {@link AbstractEto ETO} for {@link Table}.
 *
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
}
