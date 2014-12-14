package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;

import java.util.Objects;

/**
 * {@link AbstractEto ETO} for an {@link OrderPosition}.
 *
 * @author jozitz
 */
public class OrderPositionEto extends AbstractEto implements OrderPosition {

  private static final long serialVersionUID = 1L;

  private Long orderId;

  private Long cookId;

  private Long offerId;

  private String offerName;

  private OrderPositionState state;

  private Money price;

  private String comment;

  /**
   * The constructor.
   */
  public OrderPositionEto() {

    this.state = OrderPositionState.ORDERED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getOrderId() {

    return this.orderId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOrderId(Long orderId) {

    this.orderId = orderId;
  }

  /**
   * {@inheritDoc}
   */
  public Long getCookId() {

    return this.cookId;
  }

  /**
   * {@inheritDoc}
   */
  public void setCookId(Long cookId) {

    this.cookId = cookId;
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
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.orderId == null) ? 0 : this.orderId.hashCode());
    result = prime * result + ((this.cookId == null) ? 0 : this.cookId.hashCode());
    result = prime * result + ((this.offerName == null) ? 0 : this.offerName.hashCode());
    result = prime * result + ((this.offerId == null) ? 0 : this.offerId.hashCode());
    result = prime * result + ((this.comment == null) ? 0 : this.comment.hashCode());
    result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
    result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    OrderPositionEto other = (OrderPositionEto) obj;
    if (!Objects.equals(this.orderId, other.orderId)) {
      return false;
    }
    if (!Objects.equals(this.cookId, other.cookId)) {
      return false;
    }
    if (!Objects.equals(this.offerId, other.offerId)) {
      return false;
    }
    if (!Objects.equals(this.offerName, other.offerName)) {
      return false;
    }
    if (!Objects.equals(this.price, other.price)) {
      return false;
    }
    if (!Objects.equals(this.comment, other.comment)) {
      return false;
    }
    if (this.state != other.state) {
      return false;
    }
    return true;
  }

  /**
   * Returns the field 'comment'.
   *
   * @return Comment as string
   */
  public String getComment() {

    return this.comment;
  }

  /**
   * Sets the field 'comment'.
   *
   * @param comment new value for comment
   */
  public void setComment(String comment) {

    this.comment = comment;
  }

}
