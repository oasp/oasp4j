package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BankPaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractBillUc;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter.PaymentAdapter;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter.PaymentTransactionData;

/**
 * Implementation of {@link UcManageBill}.
 *
 */
@Named
@UseCase
public class UcManageBillImpl extends AbstractBillUc implements UcManageBill {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageBillImpl.class);

  private Salesmanagement salesmanagement;

  private PaymentAdapter paymentAdapter;

  @Override
  @RolesAllowed(PermissionConstants.SAVE_BILL)
  public BillEto createBill(BillEto bill) {

    List<Long> orderPositions = bill.getOrderPositionIds();
    Objects.requireNonNull(bill, "bill must not be null");

    Money total = Money.ZERO;
    List<OrderPositionEntity> myOrderPositions = new ArrayList<>();

    for (Long id : orderPositions) {
      OrderPositionEto orderPosition = this.salesmanagement.findOrderPosition(id);
      verifyNotClosed(orderPosition);
      total = total.add(orderPosition.getPrice());

      myOrderPositions.add(getBeanMapper().map(orderPosition, OrderPositionEntity.class));
    }

    Money localTip = bill.getTip();
    if (localTip == null) {
      localTip = Money.ZERO;
    }

    // Declare the bill
    BillEntity billEntity = new BillEntity();

    billEntity.setOrderPositions(myOrderPositions);
    billEntity.setTip(localTip);
    billEntity.setPayed(false);
    billEntity.setTotal(total);

    BillEntity savedBill = getBillDao().save(billEntity);

    BillEto returnBill = getBeanMapper().map(savedBill, BillEto.class);

    LOG.debug("The bill with id '{}' has been created.", returnBill.getId());

    return returnBill;
  }

  private void verifyNotClosed(OrderPosition position) {

    OrderPositionState state = position.getState();
    if ((state == null) || state.isClosed()) {
      throw new IllegalEntityStateException(position, state);
    }
  }

  /**
   * This method updates a {@link BillEntity} by checking the existence of a bill with that {@link BillEntity#getId()
   * id} in the database. If no such {@link BillEntity} exists, a new bill will be created by calling
   * {@link UcManageBillImpl#createBill(BillEto)}. Otherwise, the existing {@link BillEntity} will be updated by
   * overriding the set {@link BillEntity#getOrderPositions() order positions}, {@link BillEntity#getTip() tip} and the
   * {@link BillEntity#getTotal() total amount}.
   *
   * @param bill The {@link BillEntity} to update.
   *
   * @return the updated {@link BillEntity}
   */
  private BillEto update(BillEto bill) {

    Objects.requireNonNull(bill, "bill");

    Long billId = bill.getId();
    BillEntity targetBill = getBillDao().findOne(billId);

    List<Long> orderPositionIds = bill.getOrderPositionIds();
    if (targetBill == null) {
      // Bill does not yet exist. -> new Bill
      List<OrderPositionEto> orderPositions = new ArrayList<>();
      for (Long id : orderPositionIds) {
        OrderPositionEto position = this.salesmanagement.findOrderPosition(id);
        orderPositions.add(position);
      }
      return createBill(bill);
    } else {
      // Bill already exists. -> Update bill
      targetBill.setOrderPositionIds(orderPositionIds);
      targetBill.setPayed(bill.isPayed());
      targetBill.setTip(bill.getTip());
      targetBill.setTotal(bill.getTotal());

      /*
       * Save updated bill
       */
      getBillDao().save(targetBill);
      LOG.debug("The bill with id '{}' has been updated.", billId);

      return getBeanMapper().map(targetBill, BillEto.class);
    }
  }

  @Override
  @RolesAllowed(PermissionConstants.DELETE_BILL)
  public void deleteBill(long billId) {

    getBillDao().delete(billId);
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_BILL)
  public PaymentStatus doPayment(BillEto bill) {

    LOG.debug("The bill with id '" + bill.getId() + "' will be marked as payed.");

    // Return a PaymentStatus
    LOG.debug("The bill with id '" + bill.getId() + "' is succesfuly payed.");
    return PaymentStatus.SUCCESS;
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_BILL)
  public PaymentStatus doPayment(BillEto bill, PaymentData paymentDataDebitor) {

    // REVIEW <who> (hohwille) Remove this hack or replace with something reasonable.
    PaymentStatus status = null;

    // Creditor data: constant
    BankPaymentData paymentDataCreditor = new BankPaymentData();
    paymentDataCreditor.setAccountOwnerName("Restaurant_BlockHouse");
    paymentDataCreditor.setAccountNumber(191919L);
    paymentDataCreditor.setBankCode(760087698L);
    paymentDataCreditor.setBankName("RestaurantBank");

    // Step1: Prepare the TransactionDataTo object
    PaymentTransactionData paymentTransactionData = new PaymentTransactionData();
    paymentTransactionData.setCreditor(paymentDataCreditor);
    paymentTransactionData.setDebitor(paymentDataDebitor);
    paymentTransactionData.setTotalAmount(bill.getTotal());

    // Step2: Call the external system with the transactionDataTo
    status = this.paymentAdapter.pay(paymentTransactionData);

    // Step3: Call the function markBillAndOrderPositionsAsPayed (Mark
    // orderPositions and the current bill
    // as payed and eventually free table) if Step2 was successful
    if (PaymentStatus.SUCCESS == status) {

      bill.setPayed(true);
      for (Long orderPositionId : bill.getOrderPositionIds()) {
        this.salesmanagement.findOrderPosition(orderPositionId).setState(OrderPositionState.PAYED);
      }
    }

    // Step4: Return a PaymentStatus
    LOG.debug("The payment of bill with id '" + bill.getId() + "' has given that status '" + status + "' back.");
    return status;
  }

  /**
   * Sets the field 'paymentAdapter'.
   *
   * @param paymentAdapter New value for paymentAdapter
   */
  @Inject
  public void setPaymentAdapter(PaymentAdapter paymentAdapter) {

    this.paymentAdapter = paymentAdapter;
  }

  /**
   * @param salesmanagement the salesmanagement to set
   */
  @Inject
  public void setSalesmanagement(Salesmanagement salesmanagement) {

    this.salesmanagement = salesmanagement;
  }
}
