#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.impl.usecase;

import ${package}.general.common.api.exception.IllegalEntityStateException;
import ${package}.offermanagement.common.api.Offer;
import ${package}.offermanagement.logic.api.Offermanagement;
import ${package}.offermanagement.logic.api.to.OfferEto;
import ${package}.salesmanagement.common.api.OrderPosition;
import ${package}.salesmanagement.common.api.datatype.OrderPositionState;
import ${package}.salesmanagement.common.api.datatype.OrderState;
import ${package}.salesmanagement.logic.api.Salesmanagement;
import ${package}.salesmanagement.logic.api.to.OrderEto;
import ${package}.salesmanagement.logic.api.to.OrderPositionEto;
import ${package}.salesmanagement.logic.api.usecase.UcManageOrderPosition;
import ${package}.salesmanagement.logic.base.usecase.AbstractOrderPositionUc;
import ${package}.salesmanagement.persistence.api.OrderEntity;
import ${package}.salesmanagement.persistence.api.OrderPositionEntity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcManageOrderPosition}.
 *
 * @author jozitz
 */
@Named
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

  /**
   * {@inheritDoc}
   */
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
    orderPosition.setPrice(offerFromDb.getCurrentPrice());
    orderPosition.setOrder(getBeanMapper().map(this.salesManagement.updateOrder(order), OrderEntity.class));
    orderPosition.setComment(comment);

    // Save the order position and return it.
    getOrderPositionDao().save(orderPosition);

    LOG.debug("The order position with id '" + orderPosition.getId()
        + "' has been created. It's linked with order id '" + order.getId() + "' and offer id '" + offerId + "'.");

    return getBeanMapper().map(orderPosition, OrderPositionEto.class);
  }

  /**
   * {@inheritDoc}
   */
  public OrderPositionEto updateOrderPosition(OrderPositionEto orderPosition) {

    Objects.requireNonNull(orderPosition, "orderPosition");

    Long orderPositionId = orderPosition.getId();
    OrderPositionEntity targetOrderPosition = null;
    if (orderPositionId != null) {
      targetOrderPosition = getOrderPositionDao().findOne(orderPositionId);
    }

    if (targetOrderPosition == null) {
      /*
       * OrderPosition does not yet exist. -> new OrderPosition
       */
      targetOrderPosition = getBeanMapper().map(orderPosition, OrderPositionEntity.class);
      getOrderPositionDao().save(targetOrderPosition);
      LOG.debug("The order position with id '" + targetOrderPosition.getId() + "' saved.");

    } else {
      /*
       * OrderPosition already exists. -> Update OrderPosition
       */
      targetOrderPosition.setComment(orderPosition.getComment());

      OrderPositionState currentState = targetOrderPosition.getState();
      OrderPositionState newState = orderPosition.getState();
      switch (currentState) {
      case CANCELLED:
        if ((newState != OrderPositionState.CANCELLED) && (newState != OrderPositionState.ORDERED)) {
          throw new IllegalEntityStateException(orderPosition, currentState, newState);
        }
        break;
      case ORDERED:
        if ((newState != OrderPositionState.ORDERED) && (newState != OrderPositionState.CANCELLED)
            && (newState != OrderPositionState.PREPARED)) {
          throw new IllegalEntityStateException(orderPosition, currentState, newState);
        }
        break;
      case PREPARED:
        // from here we can go to any other state (back to ORDERED in case that the kitchen has to rework)
        break;
      case DELIVERED:
        if ((newState == OrderPositionState.PREPARED) || (newState == OrderPositionState.ORDERED)) {
          throw new IllegalEntityStateException(orderPosition, currentState, newState);
        }
        break;
      case PAYED:
        if (newState != OrderPositionState.PAYED) {
          throw new IllegalEntityStateException(orderPosition, currentState, newState);
        }
        break;
      default:
        LOG.error("Illegal state {}", currentState);
        break;
      }
      targetOrderPosition.setState(newState);
      // not allowed to be changed afterwards (manipulation)
      // targetOrderPosition.setOfferName(orderPosition.getOfferName());
      // targetOrderPosition.setPrice(orderPosition.getPrice());
      // targetOrderPosition.setOrderId(orderPosition.getOrderId());

      getOrderPositionDao().save(targetOrderPosition);
      LOG.debug("The order position with id '" + targetOrderPosition.getId() + "' has been updated.");
    }
    return getBeanMapper().map(targetOrderPosition, OrderPositionEto.class);
  }

  /**
   * {@inheritDoc}
   */
  public void markOrderPositionAs(OrderPositionEto orderPosition, OrderPositionState newState) {

    Objects.requireNonNull(orderPosition, "orderPosition");

    long orderPositionId = orderPosition.getId();
    OrderPositionEntity targetOrderPosition = getOrderPositionDao().findOne(orderPositionId);

    if (targetOrderPosition == null) {
      throw new ObjectNotFoundUserException(OrderPosition.class, orderPositionId);
    }

    OrderPositionState currentState = targetOrderPosition.getState();
    if ((newState == OrderPositionState.PREPARED) && (currentState == OrderPositionState.ORDERED)
        || (newState == OrderPositionState.DELIVERED) && (currentState == OrderPositionState.PREPARED)
        || (newState == OrderPositionState.PAYED) && (currentState == OrderPositionState.DELIVERED)
        || (newState == OrderPositionState.CANCELLED) && (currentState != OrderPositionState.PAYED)) {
      targetOrderPosition.setState(newState);
    } else {
      throw new IllegalEntityStateException(targetOrderPosition, currentState, newState);
    }

    // Marks related order as closed
    if (newState == OrderPositionState.CANCELLED || newState == OrderPositionState.PAYED) {
      List<OrderPositionEto> orderpositions =
          this.salesManagement.findOpenOrderPositionsByOrderId(orderPosition.getOrderId());
      if (orderpositions == null || orderpositions.isEmpty()) {
        targetOrderPosition.getOrder().setState(OrderState.CLOSED);
      }
    }
    getOrderPositionDao().save(targetOrderPosition);
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
