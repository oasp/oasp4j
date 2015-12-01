/**
 *
 */
package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * {@link ApplicationPersistenceEntity Entity} that represents an {@link Order} of a customer associated with the
 * according {@link io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity}.
 *
 * @author rjoeris
 */
@Entity
// Order is a reserved word in SQL/RDBMS and can not be used as table name
@Table(name = "RestaurantOrder")
public class OrderEntity extends ApplicationPersistenceEntity implements Order {

  private static final long serialVersionUID = 1L;

  private long tableId;

  private OrderState state;

  private Date created;

  /**
   * The constructor.
   */
  public OrderEntity() {

    super();
    this.state = OrderState.OPEN;
    this.created = new Date();
  }

  @Override
  @Column(name = "table_id")
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

  @Override
  @Temporal(TemporalType.TIMESTAMP)
  @Column()
  public Date getCreated() {

    return this.created;
  }

  @Override
  public void setCreated(Date created) {

    this.created = created;
  }

  /**
   * Otherwise the date isn't initialized from the database (HANA especially)
   */
  @PrePersist
  protected void onCreate() {

    this.created = new Date();
  }

}
