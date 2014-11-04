#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.persistence.api;

import ${package}.general.common.api.datatype.Money;
import ${package}.general.persistence.api.ApplicationPersistenceEntity;
import ${package}.salesmanagement.common.api.Bill;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 * {@link ApplicationPersistenceEntity Entity} that represents the bill related to one or multiple {@OrderPosition
 *  order positions}.
 *
 * @author etomety
 */
@Entity(name = "Bill")
public class BillEntity extends ApplicationPersistenceEntity implements Bill {

  private static final long serialVersionUID = 1L;

  private boolean payed;

  private List<OrderPositionEntity> orderPositions;

  private Money totalAmount;

  private Money tip;

  /**
   * The constructor.
   */
  public BillEntity() {

    super();
  }

  /**
   * Returns the field 'orderPositions'.
   *
   * @return Value of orderPositions
   */
  @ManyToMany(fetch = FetchType.EAGER)
  public List<OrderPositionEntity> getOrderPositions() {

    return this.orderPositions;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public List<Long> getOrderPositionIds() {

    if (this.orderPositions == null) {
      return null;
    }
    List<Long> result = new ArrayList<>(this.orderPositions.size());
    for (OrderPositionEntity position : this.orderPositions) {
      result.add(position.getId());
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOrderPositionIds(List<Long> ids) {

    if (ids == null) {
      this.orderPositions = null;
    } else {
      List<OrderPositionEntity> list = new ArrayList<>(ids.size());
      for (Long id : ids) {
        OrderPositionEntity position = new OrderPositionEntity();
        position.setId(id);
        list.add(position);
      }
      this.orderPositions = list;
    }
  }

  /**
   * Sets the field 'orderPositions'.
   *
   * @param orderPositions New value for orderPositions
   */
  public void setOrderPositions(List<OrderPositionEntity> orderPositions) {

    this.orderPositions = orderPositions;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Money getTotalAmount() {

    return this.totalAmount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTotalAmount(Money totalAmount) {

    this.totalAmount = totalAmount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Money getTip() {

    return this.tip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTip(Money tip) {

    this.tip = tip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isPayed() {

    return this.payed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPayed(boolean payed) {

    this.payed = payed;
  }
}
