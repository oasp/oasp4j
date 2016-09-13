package io.oasp.gastronomy.restaurant.salesmanagement.common.api;

import javax.validation.constraints.NotNull;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.validation.NotNegativeMoney;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;

/**
 * This is the interface for an {@link OrderPosition}.
 *
 */
public interface OrderPosition extends ApplicationEntity {

  /**
   * @return the {@link Order#getId ID} of the {@link Order} this {@link OrderPosition} belongs to.
   */
  @NotNull
  Long getOrderId();

  /**
   * @param orderId is the new {@link #getOrderId() orderId}.
   */
  void setOrderId(Long orderId);

  /**
   * @return the {@link io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember#getId() ID} of the
   *         {@link io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember Cook} assigned to this
   *         {@link OrderPosition} or {@code null} if it is unassigned.
   */
  Long getCookId();

  /**
   * @param cookId the new {@link #getCookId() cook ID} or {@code null} to remove the assignment.
   */
  void setCookId(Long cookId);

  /**
   * @return the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getId() ID} of the
   *         {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} associated. Not stored as foreign
   *         key and might not point to an existing
   *         {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} for historic {@link OrderPosition}s.
   */
  @NotNull
  Long getOfferId();

  /**
   * @param offerId is the new {@link #getOfferId() offerId}.
   */
  void setOfferId(Long offerId);

  /**
   * @return the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getName() name} of the
   *         {@link #getOfferId() associated} {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer}.
   *         This is stored redundant so that it is still available later if the
   *         {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} has been changed.
   */
  @NotNull
  String getOfferName();

  /**
   * @param offerName is the new {@link #getOfferName() offerName}.
   */
  void setOfferName(String offerName);

  /**
   * @return the current {@link OrderPositionState state}.
   */
  @NotNull
  OrderPositionState getState();

  /**
   * @return the current {@link ProductOrderState state}.
   */
  ProductOrderState getDrinkState();

  /**
   * @param state the new {@link #getState() state}.
   */
  void setState(OrderPositionState state);

  /**
   * @param drinkState the new {@link #getDrinkState() state}.
   */
  void setDrinkState(ProductOrderState drinkState);

  /**
   * @return the price of the {@link #getOfferId() associated}
   *         {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer}. This is stored redundant so that it
   *         is still available later if the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} has
   *         been changed.
   */
  @NotNull
  @NotNegativeMoney
  Money getPrice();

  /**
   * @param price is the new {@link #getPrice() price}.
   */
  void setPrice(Money price);

  /**
   * @return the comment for this {@link OrderPosition} (e.g. "extra spicy").
   */
  String getComment();

  /**
   * @param comment is the new {@link #getComment() comment}.
   */
  void setComment(String comment);

}
