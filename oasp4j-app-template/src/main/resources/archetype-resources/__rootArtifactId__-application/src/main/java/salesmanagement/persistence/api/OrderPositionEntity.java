#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.persistence.api;

import ${package}.general.common.api.datatype.Money;
import ${package}.general.persistence.api.ApplicationPersistenceEntity;
import ${package}.salesmanagement.common.api.OrderPosition;
import ${package}.salesmanagement.common.api.datatype.OrderPositionState;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * {@link ApplicationPersistenceEntity Entity} that represents a single position of an {@link OrderEntity}.
 *
 * @author hohwille
 */
@Entity(name = "OrderPosition")
public class OrderPositionEntity extends ApplicationPersistenceEntity implements OrderPosition {

  private static final long serialVersionUID = 1L;

  private OrderEntity order;

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
   * @param order the new value of {@link ${symbol_pound}getOrder()}.
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
