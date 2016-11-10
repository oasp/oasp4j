package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.Order;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractOrderUc;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.exception.api.ObjectMismatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

/**
 * Implementation of {@link UcManageOrder}.
 *
 */
@Named
@UseCase
@Validated
public class UcManageOrderImpl extends AbstractOrderUc implements UcManageOrder {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageOrderImpl.class);

  private Salesmanagement salesmanagement;

  /**
   * The constructor.
   */
  public UcManageOrderImpl() {

    super();
  }

  /**
   * @param salesmanagement the {@link Salesmanagement} to {@link Inject}.
   */
  @Inject
  public void setSalesmanagement(Salesmanagement salesmanagement) {

    this.salesmanagement = salesmanagement;
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_ORDER)
  public OrderEto saveOrder(OrderEto order) {

    return saveOrder(order, null);
  }

  private OrderEto saveOrder(OrderEto order, List<OrderPositionEto> positions) {

    Objects.requireNonNull(order, "order");
    Long orderId = order.getId();
    if (orderId == null) {
      OrderState state = order.getState();
      if (state != OrderState.OPEN) {
        throw new IllegalEntityStateException(order, state);
      }
    } else {
      OrderEntity currentOrder = getOrderDao().find(orderId);
      verifyUpdate(currentOrder, order, positions);
    }
    OrderEntity orderEntity = getBeanMapper().map(order, OrderEntity.class);
    orderEntity = getOrderDao().save(orderEntity);
    LOG.debug("Saved order with id {}.", orderEntity.getId());
    return getBeanMapper().map(orderEntity, OrderEto.class);
  }

  /**
   * @param currentOrder is the current {@link Order} from the persistence.
   * @param updateOrder is the new {@link Order} to update to.
   * @param positions the {@link List} of {@link OrderPositionEto positions} or {@code null} if undefined.
   */
  private void verifyUpdate(Order currentOrder, Order updateOrder, List<OrderPositionEto> positions) {

    if (currentOrder.getTableId() != updateOrder.getTableId()) {
      LOG.info("Changing order from table-id {} to table-id {}", currentOrder.getTableId(), updateOrder.getTableId());
    }
    verifyOrderStateChange(updateOrder, currentOrder.getState(), updateOrder.getState(), positions);
  }

  /**
   * Verifies if an update of the {@link OrderPositionState} is legal.
   *
   * @param updateOrder the new {@link Order} to update to.
   * @param currentState the old/current {@link OrderState} of the {@link Order}.
   * @param newState new {@link OrderState} of the {@link Order} to be updated to.
   * @param positions the {@link List} of {@link OrderPositionEto positions} or {@code null} if undefined.
   */
  private void verifyOrderStateChange(Order updateOrder, OrderState currentState, OrderState newState,
      List<OrderPositionEto> positions) {

    if (currentState == newState) {
      return;
    }
    if (newState == OrderState.CLOSED) {
      if (positions == null) {
        throw new IllegalEntityStateException(updateOrder, currentState, newState);
      }
      // we can only close an order if all its positions are closed...
      for (OrderPositionEto position : positions) {
        OrderPositionState positionState = position.getState();
        if ((positionState == null) || !positionState.isClosed()) {
          IllegalEntityStateException cause = new IllegalEntityStateException(position, positionState);
          throw new IllegalEntityStateException(cause, updateOrder, currentState, newState);
        }
      }
    }
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_ORDER)
  public OrderCto saveOrder(OrderCto order) {

    Objects.requireNonNull(order, "order");

    OrderEto orderEto = order.getOrder();
    Long orderId = orderEto.getId();
    List<OrderPositionEto> currentOrderPositionList;
    if (orderId == null) {
      currentOrderPositionList = Collections.emptyList();
    } else {
      // we could add a relation OrderEntity.positions of type List<OrderPositionEntity>...
      currentOrderPositionList = this.salesmanagement.findOrderPositionsByOrderId(orderId);
    }
    List<OrderPositionEto> positions = order.getPositions();
    orderEto = saveOrder(orderEto, positions);
    if (orderId == null) {
      orderId = orderEto.getId();
    }
    OrderCto result = new OrderCto();
    result.setOrder(orderEto);

    // we can not use hibernate (Cascade and Delete orphaned) as we need to validate and react on them...

    List<OrderPositionEto> positions2DeleteList = getEntities2Delete(currentOrderPositionList, positions);
    List<OrderPositionEto> savedPositionList = result.getPositions();
    if (positions2DeleteList.size() > 0) {
      LOG.warn("Marking {} number of order position(s) as cancelled that are missing in update of order with id {}",
          positions2DeleteList.size(), orderId);
      for (OrderPositionEto position : positions2DeleteList) {
        // only logically delete, actually the client should still send the cancelled positions...
        position.setState(OrderPositionState.CANCELLED);
        OrderPositionEto updatedOrderPosition = this.salesmanagement.saveOrderPosition(position);
        savedPositionList.add(updatedOrderPosition);
      }
    }

    for (OrderPositionEto position : positions) {
      Long positionOrderId = position.getOrderId();
      if (positionOrderId == null) {
        position.setOrderId(orderId);
      } else if (!positionOrderId.equals(orderId)) {
        throw new ObjectMismatchException(positionOrderId, orderId, "position.orderId");
      }
      OrderPositionEto updatedOrderPosition = this.salesmanagement.saveOrderPosition(position);
      savedPositionList.add(updatedOrderPosition);
    }
    return result;
  }

  @Override
  @RolesAllowed(PermissionConstants.DELETE_ORDER)
  public void deleteOrder(long id) {

    getOrderDao().delete(id);
  }

}
