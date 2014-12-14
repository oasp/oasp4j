package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BankPaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcManageBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractBillUc;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter.PaymentAdapter;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter.PaymentTransactionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcManageBill}.
 *
 * @author jozitz
 */
@Named
public class UcManageBillImpl extends AbstractBillUc implements UcManageBill {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageBillImpl.class);

  private UcManageOrderPositionImpl ucManageOrderPosition;

  private UcFindOrderPositionImpl ucFindOrderPosition;

  private PaymentAdapter paymentAdapter;

  /**
   * {@inheritDoc}
   */
  @Override
  // REVIEW <who> (hohwille) we can never trust the client hence all we need here is a list of OrderPosition Ids as Long
  public BillEto createBill(List<OrderPositionEto> orderPositions, Money tip) {

    Objects.requireNonNull(orderPositions, "orderPositions");

    Money total = Money.ZERO;

    for (OrderPositionEto position : orderPositions) {
      OrderPositionEto orderPosition = this.ucFindOrderPosition.findOrderPosition(position.getId());
      verifyNotClosed(orderPosition);
      total = total.add(orderPosition.getPrice());
    }

    Money localTip = tip;
    if (localTip == null) {
      localTip = Money.ZERO;
    }

    // Declare the bill
    BillEntity bill = new BillEntity();
    List<OrderPositionEntity> myOrderPositions = new ArrayList<>();

    for (OrderPositionEto position : orderPositions) {
      myOrderPositions.add(getBeanMapper().map(position, OrderPositionEntity.class));
    }

    bill.setOrderPositions(myOrderPositions);
    bill.setTip(localTip);
    bill.setPayed(false);
    bill.setTotal(total);

    getBillDao().save(bill);

    BillEto returnBill = getBeanMapper().map(bill, BillEto.class);

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
   * {@link UcManageBillImpl#create(List, Money)}. Otherwise, the existing {@link BillEntity} will be updated by
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
        OrderPositionEto position = this.ucFindOrderPosition.findOrderPosition(id);
        orderPositions.add(position);
      }
      return createBill(orderPositions, bill.getTip());
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteBill(Long billId) {

    getBillDao().delete(billId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PaymentStatus doPayment(BillEto bill) {

    LOG.debug("The bill with id '" + bill.getId() + "' will be marked as payed.");
    markBillAndOrderPositionsAsPayed(bill);

    // Return a PaymentStatus
    LOG.debug("The bill with id '" + bill.getId() + "' is succesfuly payed.");
    return PaymentStatus.SUCCESS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
      markBillAndOrderPositionsAsPayed(bill);
    }

    // Step4: Return a PaymentStatus
    LOG.debug("The payment of bill with id '" + bill.getId() + "' has given that status '" + status + "' back.");
    return status;
  }

  /**
   * Marks the {@link BillEntity} and its contained {@link OrderPositionEntity}s as payed.
   *
   * @param bill {@link BillEntity} to be payed
   */
  private void markBillAndOrderPositionsAsPayed(BillEto bill) {

    // Mark bill as payed
    bill.setPayed(true);
    LOG.debug("The bill with id '" + bill.getId() + "' is marked as payed.");

    // Updates bill
    update(bill);

    // Mark orders as payed
    for (Long orderPositionId : bill.getOrderPositionIds()) {
      // check for already closed orderPositions
      OrderPositionEto orderPosition = this.ucFindOrderPosition.findOrderPosition(orderPositionId);
      verifyNotClosed(orderPosition);
      this.ucManageOrderPosition.markOrderPositionAs(orderPosition, OrderPositionState.PAYED);
      LOG.debug("The OrderPosition with id '{}' containing in the bill with id '{}' is marked as payed.",
          orderPositionId, bill.getId());
    }
  }

  /**
   * Sets the field 'ucManageOrderPosition'.
   *
   * @param ucManageOrderPosition New value for ucManageOrderPosition
   */
  @Inject
  public void setUcManageOrderPosition(UcManageOrderPositionImpl ucManageOrderPosition) {

    this.ucManageOrderPosition = ucManageOrderPosition;
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
   * Setzt das Feld 'ucFindOrderPosition'.
   *
   * @param ucFindOrderPosition Neuer Wert für ucFindOrderPosition
   */
  @Inject
  public void setUcFindOrderPosition(UcFindOrderPositionImpl ucFindOrderPosition) {

    this.ucFindOrderPosition = ucFindOrderPosition;
  }
}
