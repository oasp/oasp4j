#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.impl;

import ${package}.general.common.api.datatype.Money;
import ${package}.general.common.base.AbstractBeanMapperSupport;
import ${package}.offermanagement.logic.api.to.OfferEto;
import ${package}.salesmanagement.common.api.datatype.OrderPositionState;
import ${package}.salesmanagement.common.api.datatype.PaymentStatus;
import ${package}.salesmanagement.logic.api.Salesmanagement;
import ${package}.salesmanagement.logic.api.to.BillEto;
import ${package}.salesmanagement.logic.api.to.OrderCto;
import ${package}.salesmanagement.logic.api.to.OrderEto;
import ${package}.salesmanagement.logic.api.to.OrderPositionEto;
import ${package}.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import ${package}.salesmanagement.logic.api.to.PaymentData;
import ${package}.salesmanagement.logic.api.usecase.UcChangeTable;
import ${package}.salesmanagement.logic.api.usecase.UcFindBill;
import ${package}.salesmanagement.logic.api.usecase.UcFindOrder;
import ${package}.salesmanagement.logic.api.usecase.UcFindOrderPosition;
import ${package}.salesmanagement.logic.api.usecase.UcManageBill;
import ${package}.salesmanagement.logic.api.usecase.UcManageOrder;
import ${package}.salesmanagement.logic.api.usecase.UcManageOrderPosition;
import ${package}.salesmanagement.logic.impl.usecase.UcFindOrderPositionImpl;
import ${package}.tablemanagement.logic.api.to.TableEto;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * This is the implementation of {@link Salesmanagement}.
 *
 * @author hohwille
 */
@Named
public class SalesmanagementImpl extends AbstractBeanMapperSupport implements Salesmanagement {

  private UcFindOrderPosition ucFindOrderPosition;

  private UcManageOrderPosition ucManageOrderPosition;

  private UcFindOrder ucFindOrder;

  private UcManageOrder ucManageOrder;

  private UcFindBill ucFindBill;

  private UcManageBill ucManageBill;

  private UcChangeTable ucChangeTable;

  /**
   * The constructor.
   */
  public SalesmanagementImpl() {

    super();
  }

  /**
   * @param ucFindOrderPosition the {@link UcFindOrderPosition} to {@link Inject}.
   */
  @Inject
  public void setUcFindOrderPosition(UcFindOrderPositionImpl ucFindOrderPosition) {

    this.ucFindOrderPosition = ucFindOrderPosition;
  }

  /**
   * @param ucManageOrderPosition the {@link UcManageOrderPosition} to {@link Inject}.
   */
  @Inject
  public void setUcManageOrderPosition(UcManageOrderPosition ucManageOrderPosition) {

    this.ucManageOrderPosition = ucManageOrderPosition;
  }

  /**
   * @param ucFindBill the {@link UcFindBill} to {@link Inject}.
   */
  @Inject
  public void setUcFindBill(UcFindBill ucFindBill) {

    this.ucFindBill = ucFindBill;
  }

  /**
   * @param ucFindOrder the {@link UcFindOrder} to {@link Inject}.
   */
  @Inject
  public void setUcFindOrder(UcFindOrder ucFindOrder) {

    this.ucFindOrder = ucFindOrder;
  }

  /**
   * @param ucManageOrder the {@link UcManageOrder} to {@link Inject}.
   */
  @Inject
  public void setUcManageOrder(UcManageOrder ucManageOrder) {

    this.ucManageOrder = ucManageOrder;
  }

  /**
   * @param ucManageBill the {@link UcManageBill} to {@link Inject}.
   */
  @Inject
  public void setUcManageBill(UcManageBill ucManageBill) {

    this.ucManageBill = ucManageBill;
  }

  /**
   * @param ucChangeTable the {@link UcChangeTable} to {@link Inject}.
   */
  @Inject
  public void setUcChangeTable(UcChangeTable ucChangeTable) {

    this.ucChangeTable = ucChangeTable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto createOrder(TableEto table) {

    return this.ucManageOrder.createOrder(table);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto findOrder(Long id) {

    return this.ucFindOrder.findOrder(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderEto> findOrderEtos(OrderSearchCriteriaTo criteria) {

    return this.ucFindOrder.findOrderEtos(criteria);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderCto> findOrderCtos(OrderSearchCriteriaTo criteria) {

    return this.ucFindOrder.findOrderCtos(criteria);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderCto findOrderCto(OrderEto order) {

    return this.ucFindOrder.findOrderCto(order);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto findOpenOrderForTable(Long tableId) {

    return this.ucFindOrder.findOpenOrderForTable(tableId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto updateOrder(OrderEto order) {

    return this.ucManageOrder.updateOrder(order);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderCto updateOrder(OrderCto order) {

    return this.ucManageOrder.updateOrder(order);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changeTable(OrderEto order, TableEto table) {

    this.ucChangeTable.changeTable(order, table);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderPositionEto createOrderPosition(OfferEto offer, OrderEto order, String comment) {

    return this.ucManageOrderPosition.createOrderPosition(offer, order, comment);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderPositionEto findOrderPosition(Long orderPositionId) {

    return this.ucFindOrderPosition.findOrderPosition(orderPositionId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderPositionEto> findOrderPositionsByOrderId(Long orderId) {

    return this.ucFindOrderPosition.findOrderPositionsByOrderId(orderId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderPositionEto> findOpenOrderPositionsByOrderId(Long orderId) {

    return this.ucFindOrderPosition.findOpenOrderPositionsByOrderId(orderId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderPositionEto updateOrderPosition(OrderPositionEto order) {

    return this.ucManageOrderPosition.updateOrderPosition(order);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void markOrderPositionAs(OrderPositionEto orderPosition, OrderPositionState newState) {

    this.ucManageOrderPosition.markOrderPositionAs(orderPosition, newState);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PaymentStatus doPayment(BillEto bill) {

    return this.ucManageBill.doPayment(bill);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PaymentStatus doPayment(BillEto bill, PaymentData paymentData) {

    return this.ucManageBill.doPayment(bill, paymentData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BillEto createBill(List<OrderPositionEto> orderPositions, Money tip) {

    return this.ucManageBill.createBill(orderPositions, tip);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BillEto findBill(Long id) {

    return this.ucFindBill.findBill(id);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteBill(Long billId) {

    this.ucManageBill.deleteBill(billId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEto createOrder(OrderEto order) {

    return this.ucManageOrder.createOrder(order);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteOrder(Long id) {

    this.ucManageOrder.deleteOrder(id);
  }

}
