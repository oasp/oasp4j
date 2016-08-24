package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.module.basic.common.api.to.AbstractEto;

/**
 * {@link AbstractEto ETO} for an {@link OrderPosition}.
 *
 */
public class OrderPositionEto extends AbstractEto implements OrderPosition {

  private static final long serialVersionUID = 1L;

  private Long orderId;

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
  public OrderPositionEto() {

    this.state = OrderPositionState.ORDERED;

  }

  @Override
  public Long getOrderId() {

    return this.orderId;
  }

  @Override
  public void setOrderId(Long orderId) {

    this.orderId = orderId;
  }

  @Override
  public Long getCookId() {

    return this.cookId;
  }

  @Override
  public void setCookId(Long cookId) {

    this.cookId = cookId;
  }

  @Override
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

  /**
   * Returns the field 'comment'.
   *
   * @return Comment as string
   */
  @Override
  public String getComment() {

    return this.comment;
  }

  /**
   * Sets the field 'comment'.
   *
   * @param comment new value for comment
   */
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
