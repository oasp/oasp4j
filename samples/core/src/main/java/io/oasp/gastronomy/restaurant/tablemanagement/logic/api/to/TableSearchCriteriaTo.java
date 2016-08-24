package io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;

/**
 * This is the {@link SearchCriteriaTo search criteria} {@link net.sf.mmm.util.transferobject.api.TransferObject TO}
 * used to find {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order}s.
 *
 */
public class TableSearchCriteriaTo extends SearchCriteriaTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private Long waiterId;

  private Long number;

  private TableState state;

  /**
   * The constructor.
   */
  public TableSearchCriteriaTo() {

    super();
  }

  /**
   * @return waiterId
   */
  public Long getWaiterId() {

    return this.waiterId;
  }

  /**
   * @param waiterId the waiterId to set
   */
  public void setWaiterId(Long waiterId) {

    this.waiterId = waiterId;
  }

  /**
   * @return state
   */
  public TableState getState() {

    return this.state;
  }

  /**
   * @param state the state to set
   */
  public void setState(TableState state) {

    this.state = state;
  }

  /**
   * @return number
   */
  public Long getNumber() {

    return this.number;
  }

  /**
   * @param number the number to set
   */
  public void setNumber(Long number) {

    this.number = number;
  }

}
