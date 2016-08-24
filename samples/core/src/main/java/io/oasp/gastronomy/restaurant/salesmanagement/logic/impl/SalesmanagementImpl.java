package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.general.logic.base.AbstractComponentFacade;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcChangeTable;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageOrderPosition;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * This is the implementation of {@link Salesmanagement}.
 *
 */
@Named
@Transactional
public class SalesmanagementImpl extends AbstractComponentFacade implements Salesmanagement {

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
  @UseCase
  public void setUcFindOrderPosition(UcFindOrderPosition ucFindOrderPosition) {

    this.ucFindOrderPosition = ucFindOrderPosition;
  }

  /**
   * @param ucManageOrderPosition the {@link UcManageOrderPosition} to {@link Inject}.
   */
  @Inject
  @UseCase
  public void setUcManageOrderPosition(UcManageOrderPosition ucManageOrderPosition) {

    this.ucManageOrderPosition = ucManageOrderPosition;
  }

  /**
   * @param ucFindBill the {@link UcFindBill} to {@link Inject}.
   */
  @Inject
  @UseCase
  public void setUcFindBill(UcFindBill ucFindBill) {

    this.ucFindBill = ucFindBill;
  }

  /**
   * @param ucFindOrder the {@link UcFindOrder} to {@link Inject}.
   */
  @Inject
  @UseCase
  public void setUcFindOrder(UcFindOrder ucFindOrder) {

    this.ucFindOrder = ucFindOrder;
  }

  /**
   * @param ucManageOrder the {@link UcManageOrder} to {@link Inject}.
   */
  @Inject
  @UseCase
  public void setUcManageOrder(UcManageOrder ucManageOrder) {

    this.ucManageOrder = ucManageOrder;
  }

  /**
   * @param ucManageBill the {@link UcManageBill} to {@link Inject}.
   */
  @Inject
  @UseCase
  public void setUcManageBill(UcManageBill ucManageBill) {

    this.ucManageBill = ucManageBill;
  }

  /**
   * @param ucChangeTable the {@link UcChangeTable} to {@link Inject}.
   */
  @Inject
  @UseCase
  public void setUcChangeTable(UcChangeTable ucChangeTable) {

    this.ucChangeTable = ucChangeTable;
  }

  @Override
  public OrderEto findOrder(long id) {

    return this.ucFindOrder.findOrder(id);
  }

  @Override
  public PaginatedListTo<OrderEto> findOrderEtos(OrderSearchCriteriaTo criteria) {

    return this.ucFindOrder.findOrderEtos(criteria);
  }

  @Override
  public PaginatedListTo<OrderCto> findOrderCtos(OrderSearchCriteriaTo criteria) {

    return this.ucFindOrder.findOrderCtos(criteria);
  }

  @Override
  public OrderCto findOrderCto(OrderEto order) {

    return this.ucFindOrder.findOrderCto(order);
  }

  @Override
  public OrderEto findOpenOrderForTable(long tableId) {

    return this.ucFindOrder.findOpenOrderForTable(tableId);
  }

  @Override
  public void changeTable(long orderId, long tableId) {

    this.ucChangeTable.changeTable(orderId, tableId);

  }

  @Override
  public OrderPositionEto createOrderPosition(OfferEto offer, OrderEto order, String comment) {

    return this.ucManageOrderPosition.createOrderPosition(offer, order, comment);
  }

  @Override
  public OrderPositionEto findOrderPosition(long orderPositionId) {

    return this.ucFindOrderPosition.findOrderPosition(orderPositionId);
  }

  @Override
  public List<OrderPositionEto> findOrderPositions(OrderPositionSearchCriteriaTo criteria) {

    return this.ucFindOrderPosition.findOrderPositions(criteria);
  }

  @Override
  public List<OrderPositionEto> findOrderPositionsByOrderId(long orderId) {

    return this.ucFindOrderPosition.findOrderPositionsByOrderId(orderId);
  }

  @Override
  public List<OrderPositionEto> findOpenOrderPositionsByOrderId(long orderId) {

    return this.ucFindOrderPosition.findOpenOrderPositionsByOrderId(orderId);
  }

  @Override
  public PaymentStatus doPayment(BillEto bill) {

    return this.ucManageBill.doPayment(bill);
  }

  @Override
  public PaymentStatus doPayment(BillEto bill, PaymentData paymentData) {

    return this.ucManageBill.doPayment(bill, paymentData);
  }

  @Override
  public BillCto findBill(long id) {

    return this.ucFindBill.findBill(id);

  }

  @Override
  public void deleteBill(long billId) {

    this.ucManageBill.deleteBill(billId);
  }

  @Override
  public void deleteOrder(long id) {

    this.ucManageOrder.deleteOrder(id);
  }

  @Override
  public OrderCto saveOrder(OrderCto order) {

    return this.ucManageOrder.saveOrder(order);
  }

  @Override
  public OrderEto saveOrder(OrderEto order) {

    return this.ucManageOrder.saveOrder(order);
  }

  @Override
  public OrderPositionEto saveOrderPosition(OrderPositionEto orderPosition) {

    return this.ucManageOrderPosition.saveOrderPosition(orderPosition);
  }

  @Override
  public BillEto createBill(BillEto bill) {

    return this.ucManageBill.createBill(bill);
  }

}
