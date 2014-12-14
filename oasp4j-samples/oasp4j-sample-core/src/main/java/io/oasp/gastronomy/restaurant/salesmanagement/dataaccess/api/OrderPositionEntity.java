package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * {@link ApplicationPersistenceEntity Entity} that represents a single {@link OrderPosition position} of an
 * {@link OrderEntity}.
 *
 * @author hohwille
 */
@Entity(name = "OrderPosition")
public class OrderPositionEntity extends ApplicationPersistenceEntity implements OrderPosition {

  private static final long serialVersionUID = 1L;

  private OrderEntity order;

  private Long cookId;

  private Long offerId;

  private String offerName;

  private OrderPositionState state;

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
  @JoinColumn(name = "order_id")
  public OrderEntity getOrder() {

    return this.order;
  }

  /**
   * @param order the new value of {@link #getOrder()}.
   */
  public void setOrder(OrderEntity order) {

    this.order = order;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public Long getOrderId() {

    if (this.order == null) {
      return null;
    }
    return this.order.getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOrderId(Long orderId) {

    if (orderId == null) {
      this.order = null;
    }
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setId(orderId);
    this.order = orderEntity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Column(name = "cook_id")
  public Long getCookId() {

    return this.cookId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCookId(Long cookId) {

    this.cookId = cookId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Column(name = "offer_id")
  public Long getOfferId() {

    return this.offerId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOfferId(Long offerId) {

    this.offerId = offerId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getOfferName() {

    return this.offerName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOfferName(String offerName) {

    this.offerName = offerName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderPositionState getState() {

    return this.state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setState(OrderPositionState state) {

    this.state = state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Money getPrice() {

    return this.price;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPrice(Money price) {

    this.price = price;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getComment() {

    return this.comment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setComment(String comment) {

    this.comment = comment;
  }

}
