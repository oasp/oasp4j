package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;

/**
 * Interface of the adapter for external payment.
 * 
 */
public interface PaymentAdapter {

  /**
   * @param paymentTransactionData paymentTransactionData
   * @return PaymentStatus
   */
  PaymentStatus pay(PaymentTransactionData paymentTransactionData);
}
