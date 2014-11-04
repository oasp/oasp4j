#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.common.api;

import ${package}.general.common.api.ApplicationEntity;
import ${package}.general.common.api.datatype.Money;
import ${package}.salesmanagement.common.api.datatype.OrderPositionState;

/**
 * This is the interface for an {@link OrderPosition}.
 *
 * @author hohwille
 */
public interface OrderPosition extends ApplicationEntity {

  /**
   * @return the {@link Order${symbol_pound}getId ID} of the {@link Order} this {@link OrderPosition} belongs to.
   */
  Long getOrderId();

  /**
   * @param orderId is the new {@link ${symbol_pound}getOrderId() orderId}.
   */
  void setOrderId(Long orderId);

  /**
   * @return the {@link ${package}.offermanagement.common.api.Offer${symbol_pound}getId() ID} of the
   *         {@link ${package}.offermanagement.common.api.Offer} associated. Not stored as foreign
   *         key and might not point to an existing
   *         {@link ${package}.offermanagement.common.api.Offer} for historic {@link OrderPosition}s.
   */
  Long getOfferId();

  /**
   * @param offerId is the new {@link ${symbol_pound}getOfferId() offerId}.
   */
  void setOfferId(Long offerId);

  /**
   * @return the {@link ${package}.offermanagement.common.api.Offer${symbol_pound}getName() name} of the
   *         {@link ${symbol_pound}getOfferId() associated} {@link ${package}.offermanagement.common.api.Offer}.
   *         This is stored redundant so that it is still available later if the
   *         {@link ${package}.offermanagement.common.api.Offer} has been changed.
   */
  String getOfferName();

  /**
   * @param offerName is the new {@link ${symbol_pound}getOfferName() offerName}.
   */
  void setOfferName(String offerName);

  /**
   * @return the current {@link OrderPositionState state}.
   */
  OrderPositionState getState();

  /**
   * @param state the new {@link ${symbol_pound}getState() state}.
   */
  void setState(OrderPositionState state);

  /**
   * @return the price of the {@link ${symbol_pound}getOfferId() associated}
   *         {@link ${package}.offermanagement.common.api.Offer}. This is stored redundant so that it
   *         is still available later if the {@link ${package}.offermanagement.common.api.Offer} has
   *         been changed.
   */
  Money getPrice();

  /**
   * @param price is the new {@link ${symbol_pound}getPrice() price}.
   */
  void setPrice(Money price);

  /**
   * @return the comment for this {@link OrderPosition} (e.g. "extra spicy").
   */
  String getComment();

  /**
   * @param comment is the new {@link ${symbol_pound}getComment() comment}.
   */
  void setComment(String comment);

}
