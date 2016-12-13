package io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;

/**
 * {@link ApplicationPersistenceEntity Entity} representing a {@link Table} of the restaurant. A table has a unique
 * {@link #getNumber() number} can be {@link TableState#isReserved() reserved}, {@link TableState#isOccupied() occupied}
 * and may have a {@link io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity waiter}
 * assigned.
 *
 */
@Entity
// Table is a reserved word in SQL/RDBMS and can not be used as table name
@javax.persistence.Table(name = "RestaurantTable")
public class TableEntity extends ApplicationPersistenceEntity implements Table {

  private static final long serialVersionUID = 1L;

  private Long number;

  private Long waiterId;

  private TableState state;

  @Override
  /*
   * Uncomment the following Column annotation if the database used is Oracle 11g and comment the Column annotation just
   * before @Override annotation
   */
  // @Column(name = "\"number\"", unique = true)
  @Column(unique = true)
  public Long getNumber() {

    return this.number;
  }

  @Override
  public void setNumber(Long number) {

    this.number = number;
  }

  @Override
  @Column(name = "waiterId")
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
