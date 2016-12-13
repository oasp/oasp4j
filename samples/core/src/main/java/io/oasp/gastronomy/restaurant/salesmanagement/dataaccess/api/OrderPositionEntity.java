package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;

/**
 * {@link ApplicationPersistenceEntity Entity} that represents a single {@link OrderPosition position} of an
 * {@link OrderEntity}.
 *
 */
@Entity
@Table(name = "OrderPosition")
public class OrderPositionEntity extends ApplicationPersistenceEntity implements OrderPosition {

  private static final long serialVersionUID = 1L;

  private OrderEntity order;

  private Long cookId;

  private Long offerId;

  private String offerName;

  private OrderPositionState state;

  private ProductOrderState drinkState;

  private Money price;

  private String comment;

  /**
   * The constructor.
   */
  public OrderPositionEntity() {

    super();
    this.state = OrderPositionState.ORDERED;
  }

  /**
   * @return the {@link OrderEntity order} this {@link OrderPositionEntity} is associated with.
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "orderId")
  @NotNull
  public OrderEntity getOrder() {

    return this.order;
  }

  /**
   * @param order the new value of {@link #getOrder()}.
   */
  public void setOrder(OrderEntity order) {

    this.order = order;
  }

  @Override
  @Transient
  public Long getOrderId() {

    if (this.order == null) {
      return null;
    }
    return this.order.getId();
  }

  @Override
  public void setOrderId(Long orderId) {

    if (orderId == null) {
      this.order = null;
    }
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setId(orderId);
    this.order = orderEntity;
  }

  @Override
  @Column(name = "cookId")
  public Long getCookId() {

    return this.cookId;
  }

  @Override
  public void setCookId(Long cookId) {

    this.cookId = cookId;
  }

  @Override
  @Column(name = "offerId")
  public Long getOfferId() {

    return this.offerId;
  }

  @Override
  public void setOfferId(Long offerId) {

    this.offerId = offerId;
  }

  @Override
  public String getOfferName() {

    return this.offerName;
  }

  @Override
  public void setOfferName(String offerName) {

    this.offerName = offerName;
  }

  @Override
  public OrderPositionState getState() {

    return this.state;
  }

  @Override
  public void setState(OrderPositionState state) {

    this.state = state;
  }

  @Override
  public Money getPrice() {

    return this.price;
  }

  @Override
  public void setPrice(Money price) {

    this.price = price;
  }

  @Override
  /*
   * Uncomment the following Column annotation if the database used is Oracle 11g
   */
  // @Column(name = "\"comment\"")
  public String getComment() {

    return this.comment;
  }

  @Override
  public void setComment(String comment) {

    this.comment = comment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProductOrderState getDrinkState() {

    return this.drinkState;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDrinkState(ProductOrderState drinkState) {

    this.drinkState = drinkState;

  }

}
