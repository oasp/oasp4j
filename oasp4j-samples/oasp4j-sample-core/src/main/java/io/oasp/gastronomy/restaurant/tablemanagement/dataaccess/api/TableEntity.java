package io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * {@link ApplicationPersistenceEntity Entity} representing a {@link Table} of the restaurant. A table has a unique
 * {@link #getNumber() number} can be {@link TableState#isReserved() reserved}, {@link TableState#isOccupied() occupied}
 * and may have a {@link io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity waiter}
 * assigned.
 *
 * @author hohwille
 */
@Entity(name = "Table")
// Table is a reserved word in SQL/RDBMS and can not be used as table name
@javax.persistence.Table(name = "RestaurantTable")
public class TableEntity extends ApplicationPersistenceEntity implements Table {

  private static final long serialVersionUID = 1L;

  private Long number;

  private Long waiterId;

  private TableState state;

  /**
   * {@inheritDoc}
   */
  @Override
  @Column(unique = true)
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
  @Override
  @Column(name = "waiter_id")
  public Long getWaiterId() {

    return this.waiterId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWaiterId(Long waiterId) {

    this.waiterId = waiterId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TableState getState() {

    return this.state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setState(TableState state) {

    this.state = state;
  }

}
