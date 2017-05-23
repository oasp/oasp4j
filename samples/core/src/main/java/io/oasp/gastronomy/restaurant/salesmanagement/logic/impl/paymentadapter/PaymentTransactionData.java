package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.AbstractPaymentData;

import java.io.Serializable;

/**
 * This class acts as a simple POJO. The creditor acts as the receiver of the transfered total amount. The total amount
 * will be charged the debtor.
 *
 */
public class PaymentTransactionData implements Serializable {

  private static final long serialVersionUID = 1L;

  /** The creditor and his payment data of that payment transaction. */
  private AbstractPaymentData creditor;

  /** The debitor and his payment data of that payment transaction. */
  private AbstractPaymentData debitor;

  /** The total amount to transfer. */
  private Money totalAmount;

  /**
   * Returns the field 'creditor'.
   *
   * @return Value of creditor
   */
  public AbstractPaymentData getCreditor() {

    return this.creditor;
  }

  /**
   * Sets the field 'creditor'.
   *
   * @param creditor New value for creditor
   */
  public void setCreditor(AbstractPaymentData creditor) {

    this.creditor = creditor;
  }

  /**
   * Returns the field 'debitor'.
   *
   * @return Value of debitor
   */
  public AbstractPaymentData getDebitor() {

    return this.debitor;
  }

  /**
   * Sets the field 'debitor'.
   *
   * @param debitor New value for debitor
   */
  public void setDebitor(AbstractPaymentData debitor) {

    this.debitor = debitor;
  }

  /**
   * Returns the field 'totalAmount'.
   *
   * @return Value of totalAmount
   */
  public Money getTotalAmount() {

    return this.totalAmount;
  }

  /**
   * Sets the field 'totalAmount'.
   *
   * @param totalAmount New value for totalAmount
   */
  public void setTotalAmount(Money totalAmount) {

    this.totalAmount = totalAmount;
  }
}
