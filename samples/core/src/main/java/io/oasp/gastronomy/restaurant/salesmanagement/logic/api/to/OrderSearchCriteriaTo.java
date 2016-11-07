package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;

/**
 * This is the {@link SearchCriteriaTo search criteria} {@link net.sf.mmm.util.transferobject.api.TransferObject TO}
 * used to find {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order}s.
 *
 */
public class OrderSearchCriteriaTo extends SearchCriteriaTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private OrderState state;

  private Long tableId;

  /**
   * The constructor.
   */
  public OrderSearchCriteriaTo() {

    super();
  }

  /**
   * @return state
   */
  public OrderState getState() {

    return this.state;
  }

  /**
   * @param state the state to set
   */
  public void setState(OrderState state) {

    this.state = state;
  }

  /**
   * @return tableId
   */
  public Long getTableId() {

    return this.tableId;
  }

  /**
   * @param tableId the tableId to set
   */
  public void setTableId(Long tableId) {

    this.tableId = tableId;
  }

}
