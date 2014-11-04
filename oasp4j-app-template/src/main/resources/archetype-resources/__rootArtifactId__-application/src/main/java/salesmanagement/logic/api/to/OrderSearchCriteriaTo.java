#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api.to;

import ${package}.general.common.api.to.SearchCriteriaTo;
import ${package}.salesmanagement.common.api.datatype.OrderState;

/**
 * This is the {@link SearchCriteriaTo search criteria} {@link net.sf.mmm.util.transferobject.api.TransferObject TO}
 * used to find an {@link ${package}.salesmanagement.common.api.Order}.
 *
 * @author hohwille
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
