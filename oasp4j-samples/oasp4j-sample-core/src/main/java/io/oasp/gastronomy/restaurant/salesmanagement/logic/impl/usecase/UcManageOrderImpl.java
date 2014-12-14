package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractOrderUc;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.exception.api.ObjectMismatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rjoeris
 */
@Named
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

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto saveOrder(TableEto table) {

    Objects.requireNonNull(table, "table");

    OrderEntity order = new OrderEntity();
    order.setTableId(table.getId());
    getOrderDao().save(order);

    LOG.debug("The order with id '" + order.getId() + "' has been created. It's linked with table id '" + table.getId()
        + "'.");

    return getBeanMapper().map(order, OrderEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto saveOrder(OrderEto order) {

    Objects.requireNonNull(order, "order");

    OrderEntity orderEntity = getBeanMapper().map(order, OrderEntity.class);
    orderEntity = getOrderDao().save(orderEntity);
    LOG.debug("Saved order with id {}.", orderEntity.getId());
    return getBeanMapper().map(orderEntity, OrderEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderCto saveOrder(OrderCto order) {

    Objects.requireNonNull(order, "order");

    OrderEto orderEto = order.getOrder();
    Long orderId = orderEto.getId();
    OrderEntity currentOrder = null;
    List<OrderPositionEto> currentOrderPositionList;
    if (orderId == null) {
      currentOrderPositionList = Collections.emptyList();
    } else {
      currentOrder = getOrderDao().find(orderId);
      // we could add a relation OrderEntity.positions of type List<OrderPositionEntity>...
      currentOrderPositionList = this.salesmanagement.findOrderPositionsByOrderId(orderId);
    }
    validateOrderState(order, currentOrder);

    OrderEntity orderEntity = getBeanMapper().map(orderEto, OrderEntity.class);
    getOrderDao().save(orderEntity);
    if (orderId == null) {
      orderId = orderEntity.getId();
    }
    OrderEto savedOrder = getBeanMapper().map(orderEntity, OrderEto.class);
    OrderCto result = new OrderCto();
    result.setOrder(savedOrder);

    // we can not use hibernate (Cascade and Delete orphaned) as we need to validate and react on them...

    List<OrderPositionEto> positionList = order.getPositions();

    List<OrderPositionEto> positions2DeleteList = getEntities2Delete(currentOrderPositionList, positionList);
    List<OrderPositionEto> savedPositionList = result.getPositions();
    if (positions2DeleteList.size() > 0) {
      LOG.warn("Marking {} number of order position(s) as cancelled that are missing in update of order with id {}",
          positions2DeleteList.size(), orderId);
      for (OrderPositionEto position : positions2DeleteList) {
        // only logically delete, actually the client should still send the cancelled positions...
        position.setState(OrderPositionState.CANCELLED);
        this.salesmanagement.saveOrderPosition(position);
      }
      savedPositionList.addAll(positions2DeleteList);
    }

    for (OrderPositionEto position : positionList) {
      Long positionOrderId = position.getOrderId();
      if (positionOrderId == null) {
        position.setOrderId(orderId);
      } else if (!positionOrderId.equals(orderId)) {
        throw new ObjectMismatchException(positionOrderId, orderId, "position.orderId");
      }
      savedPositionList.add(this.salesmanagement.saveOrderPosition(position));
    }
    return result;
  }

  /**
   * @param order is the {@link OrderCto} to save or update.
   * @param targetOrder is the existing {@link OrderEntity} or <code>null</code> in case of a new {@link OrderCto} to
   *        save.
   */
  private void validateOrderState(OrderCto order, OrderEntity targetOrder) {

    OrderEto orderEto = order.getOrder();
    List<OrderPositionEto> positionList = order.getPositions();
    // int positionCount = positionList.size();
    OrderState newState = orderEto.getState();
    if (targetOrder == null) {
      // new order
      if (newState != OrderState.OPEN) {
        throw new IllegalEntityStateException(order, newState);
      }
    } else {
      // update existing targetOrder
      if (newState == OrderState.CLOSED) {
        // we can only close an order if all its positions are closed...
        for (OrderPositionEto position : positionList) {
          OrderPositionState positionState = position.getState();
          if ((positionState == null) || !positionState.isClosed()) {
            IllegalEntityStateException cause = new IllegalEntityStateException(position, positionState);
            throw new IllegalEntityStateException(cause, order, OrderState.CLOSED);
          }
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteOrder(Long id) {

    getOrderDao().delete(id);
  }

}
