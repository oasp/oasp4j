package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalPropertyChangeException;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractOrderPositionUc;

/**
 * Implementation of {@link UcManageOrderPosition}.
 *
 */
@Named
@UseCase
@Validated
public class UcManageOrderPositionImpl extends AbstractOrderPositionUc implements UcManageOrderPosition {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageOrderPositionImpl.class);

  private Salesmanagement salesManagement;

  private Offermanagement offerManagement;

  /**
   * The constructor.
   */
  public UcManageOrderPositionImpl() {

    super();
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_ORDER_POSITION)
  public OrderPositionEto createOrderPosition(OfferEto offer, OrderEto order, String comment) {

    Objects.requireNonNull(offer, "offer");
    Long offerId = offer.getId();
    OfferEto offerFromDb = this.offerManagement.findOffer(offerId);
    if (offerFromDb == null) {
      throw new ObjectNotFoundUserException(Offer.class, offerId);
    }
    OrderPositionEntity orderPosition = new OrderPositionEntity();

    order.setState(OrderState.OPEN);
    // Declare the order position.
    orderPosition.setOfferId(offerId);
    orderPosition.setOfferName(offerFromDb.getDescription());
    orderPosition.setPrice(offerFromDb.getPrice());
    orderPosition.setOrder(getBeanMapper().map(this.salesManagement.saveOrder(order), OrderEntity.class));
    orderPosition.setComment(comment);

    // Save the order position and return it.
    getOrderPositionDao().save(orderPosition);

    LOG.debug("The order position with id '" + orderPosition.getId() + "' has been created. It's linked with order id '"
        + order.getId() + "' and offer id '" + offerId + "'.");

    return getBeanMapper().map(orderPosition, OrderPositionEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_ORDER_POSITION)
  public OrderPositionEto saveOrderPosition(OrderPositionEto orderPosition) {

    Objects.requireNonNull(orderPosition, "orderPosition");

    Long orderPositionId = orderPosition.getId();
    String action;
    if (orderPositionId == null) {
      action = "saved";
      Long offerId = orderPosition.getOfferId();
      OfferEto offer = this.offerManagement.findOffer(offerId);
      Objects.requireNonNull(offer, "Offer@" + offerId);
      orderPosition.setPrice(offer.getPrice());
      orderPosition.setOfferName(offer.getName());
    } else {
      OrderPositionEntity currentOrderPosition = getOrderPositionDao().find(orderPositionId);
      verifyUpdate(currentOrderPosition, orderPosition);
      action = "updated";
    }

    OrderPositionEntity orderPositionEntity = getBeanMapper().map(orderPosition, OrderPositionEntity.class);
    orderPositionEntity = getOrderPositionDao().save(orderPositionEntity);
    LOG.debug("The order position with id {} has been {}.", orderPositionEntity.getId(), action);
    return getBeanMapper().map(orderPositionEntity, OrderPositionEto.class);
  }

  /**
   * @param currentOrderPosition is the current {@link OrderPosition} from the persistence.
   * @param updateOrderPosition is the new {@link OrderPosition} to update to.
   */
  private void verifyUpdate(OrderPosition currentOrderPosition, OrderPosition updateOrderPosition) {

    if (!Objects.equals(currentOrderPosition.getOrderId(), currentOrderPosition.getOrderId())) {
      throw new IllegalPropertyChangeException(updateOrderPosition, "orderId");
    }
    if (!Objects.equals(currentOrderPosition.getOfferId(), currentOrderPosition.getOfferId())) {
      throw new IllegalPropertyChangeException(updateOrderPosition, "offerId");
    }
    if (!Objects.equals(currentOrderPosition.getPrice(), currentOrderPosition.getPrice())) {
      throw new IllegalPropertyChangeException(updateOrderPosition, "price");
    }
    if (!Objects.equals(currentOrderPosition.getOfferName(), currentOrderPosition.getOfferName())) {
      throw new IllegalPropertyChangeException(updateOrderPosition, "offerName");
    }
    OrderPositionState currentState = currentOrderPosition.getState();
    OrderPositionState newState = updateOrderPosition.getState();
    ProductOrderState newDrinkState = updateOrderPosition.getDrinkState();

    verifyOrderPositionStateChange(updateOrderPosition, currentState, newState);

    // TODO add verification methods of other sub-states of OrderPosition (i.e. Meal and SideDish)
    verifyDrinkStateChange(updateOrderPosition, currentState, newState, newDrinkState);

  }

  /**
   * Verifies if an update of the {@link OrderPositionState} is legal.
   *
   * @param updateOrderPosition the new {@link OrderPosition} to update to.
   * @param currentState the old/current {@link OrderPositionState} of the {@link OrderPosition}.
   * @param newState new {@link OrderPositionState} of the {@link OrderPosition} to be updated to.
   */
  private void verifyOrderPositionStateChange(OrderPosition updateOrderPosition, OrderPositionState currentState,
      OrderPositionState newState) {

    switch (currentState) {
    case CANCELLED:
      if ((newState != OrderPositionState.CANCELLED) && (newState != OrderPositionState.ORDERED)) {
        throw new IllegalEntityStateException(updateOrderPosition, currentState, newState);
      }
      break;
    case ORDERED:
      if ((newState != OrderPositionState.ORDERED) && (newState != OrderPositionState.CANCELLED)
          && (newState != OrderPositionState.PREPARED)) {
        throw new IllegalEntityStateException(updateOrderPosition, currentState, newState);
      }
      break;
    case PREPARED:
      // from here we can go to any other state (back to ORDERED in case that the kitchen has to rework)
      break;
    case DELIVERED:
      if ((newState == OrderPositionState.PREPARED) || (newState == OrderPositionState.ORDERED)) {
        throw new IllegalEntityStateException(updateOrderPosition, currentState, newState);
      }
      break;
    case PAYED:
      if (newState != OrderPositionState.PAYED) {
        throw new IllegalEntityStateException(updateOrderPosition, currentState, newState);
      }
      break;
    default:
      LOG.error("Illegal state {}", currentState);
      break;
    }

  }

  /**
   * Verifies if an update of the {@link ProductOrderState} is legal. This verification is based on both the states of
   * {@link ProductOrderState} and {@link OrderPositionState}.
   *
   * @param updateOrderPosition the new {@link OrderPosition} to update to.
   * @param currentState the old/current {@link OrderPositionState} of the {@link OrderPosition}.
   * @param newState new {@link OrderPositionState} of the {@link OrderPosition} to be updated to.
   * @param newDrinkState new {@link ProductOrderState} of the drink of the {@link OrderPosition} to be updated to.
   */
  private void verifyDrinkStateChange(OrderPosition updateOrderPosition, OrderPositionState currentState,
      OrderPositionState newState, ProductOrderState newDrinkState) {

    switch (currentState) {
    case CANCELLED:
      if ((newState != OrderPositionState.CANCELLED) && (newState != OrderPositionState.ORDERED)) {
        throw new IllegalEntityStateException(updateOrderPosition, currentState, newDrinkState);
      }
      break;
    case ORDERED:
      if ((newState != OrderPositionState.ORDERED) && (newState != OrderPositionState.CANCELLED)
          && (newState != OrderPositionState.PREPARED) && (newDrinkState != ProductOrderState.ORDERED)
          && (newDrinkState != ProductOrderState.PREPARED)) {
        throw new IllegalEntityStateException(updateOrderPosition, currentState, newDrinkState);
      }
      break;
    case PREPARED:
      // from here we can go to any other state (back to ORDERED in case that the kitchen has to rework)
      break;
    case DELIVERED:
      if ((newState == OrderPositionState.PREPARED) || (newState == OrderPositionState.ORDERED)
          || (newDrinkState == ProductOrderState.PREPARED) || (newDrinkState == ProductOrderState.ORDERED)) {
        throw new IllegalEntityStateException(updateOrderPosition, currentState, newDrinkState);
      }
      break;
    case PAYED:
      if (newState != OrderPositionState.PAYED) {
        throw new IllegalEntityStateException(updateOrderPosition, currentState, newDrinkState);
      }
      break;
    default:
      LOG.error("Illegal state {}", currentState);
      break;
    }

  }

  /**
   * @param salesManagement the {@link Salesmanagement} to {@link Inject}.
   */
  @Inject
  public void setSalesManagement(Salesmanagement salesManagement) {

    this.salesManagement = salesManagement;
  }

  /**
   * @param offerManagement the {@link Offermanagement} to {@link Inject}.
   */
  @Inject
  public void setOfferManagement(Offermanagement offerManagement) {

    this.offerManagement = offerManagement;
  }

}
