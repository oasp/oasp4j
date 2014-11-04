#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 *
 */
package ${package}.salesmanagement.persistence.api;

import ${package}.general.persistence.api.ApplicationPersistenceEntity;
import ${package}.salesmanagement.common.api.Order;
import ${package}.salesmanagement.common.api.datatype.OrderState;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * {@link ApplicationPersistenceEntity Entity} that represents an order of a customer associated with the according
 * {@link ${package}.tablemanagement.persistence.api.TableEntity}.
 *
 * @author rjoeris
 */
@Entity(name = "Order")
// Order is a reserved word in SQL/RDBMS and can not be used as table name
@Table(name = "TableOrder")
public class OrderEntity extends ApplicationPersistenceEntity implements Order {

  private static final long serialVersionUID = 1L;

  private long tableId;

  private OrderState state;

  /**
   * The constructor.
   */
  public OrderEntity() {

    super();
    this.state = OrderState.OPEN;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getTableId() {

    return this.tableId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTableId(long tableId) {

    this.tableId = tableId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderState getState() {

    return this.state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setState(OrderState state) {

    this.state = state;
  }

}
