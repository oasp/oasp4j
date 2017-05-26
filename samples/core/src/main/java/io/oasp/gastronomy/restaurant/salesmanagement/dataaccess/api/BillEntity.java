package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.Bill;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;

/**
 * {@link ApplicationPersistenceEntity Entity} that represents the {@link Bill} related to one or multiple
 * {@link OrderPosition order positions}.
 *
 */
@Entity
@Table(name = "Bill")
public class BillEntity extends ApplicationPersistenceEntity implements Bill {

  private static final long serialVersionUID = 1L;

  private boolean payed;

  private List<OrderPositionEntity> orderPositions;

  private Money total;

  private Money tip;

  /**
   * The constructor.
   */
  public BillEntity() {

    super();
  }

  /**
   * @return the {@link List} of {@link OrderPositionEntity} objects associated with this {@link BillEntity}.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @Column(name = "orderPositionsId")
  @JoinTable(name = "BillOrderPosition", joinColumns = {
  @JoinColumn(name = "billId") }, inverseJoinColumns = @JoinColumn(name = "orderPositionsId")

  )

  public List<OrderPositionEntity> getOrderPositions() {

    return this.orderPositions;
  }

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

  @Override
  public Money getTotal() {

    return this.total;
  }

  @Override
  public void setTotal(Money total) {

    this.total = total;
  }

  @Override
  public Money getTip() {

    return this.tip;
  }

  @Override
  public void setTip(Money tip) {

    this.tip = tip;
  }

  @Override
  public boolean isPayed() {

    return this.payed;
  }

  @Override
  public void setPayed(boolean payed) {

    this.payed = payed;
  }
}
