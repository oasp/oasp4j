/**
 *
 */
package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;

/**
 * {@link ApplicationPersistenceEntity Entity} that represents an {@link Order} of a customer associated with the
 * according {@link io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity}.
 *
 */
@Entity
// Order is a reserved word in SQL/RDBMS and can not be used as table name
@Table(name = "RestaurantOrder")
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

  @Override
  @Column(name = "tableId")
  public long getTableId() {

    return this.tableId;
  }

  @Override
  public void setTableId(long tableId) {

    this.tableId = tableId;
  }

  @Override
  public OrderState getState() {

    return this.state;
  }

  @Override
  public void setState(OrderState state) {

    this.state = state;
  }

}
