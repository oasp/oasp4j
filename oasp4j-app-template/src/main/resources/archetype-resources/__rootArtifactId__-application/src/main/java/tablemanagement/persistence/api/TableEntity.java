#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.persistence.api;

import ${package}.general.persistence.api.ApplicationPersistenceEntity;
import ${package}.tablemanagement.common.api.Table;
import ${package}.tablemanagement.common.api.datatype.TableState;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * {@link ApplicationPersistenceEntity Entity} representing a table of the restaurant. A table has a unique
 * {@link ${symbol_pound}getNumber() number} (primary key) can be {@link TableState${symbol_pound}isReserved() reserved},
 * {@link TableState${symbol_pound}isOccupied() occupied} and may have a
 * {@link ${package}.staffmanagement.persistence.api.StaffMemberEntity waiter} assigned.
 *
 * @author hohwille
 */
@Entity(name = "Table")
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
