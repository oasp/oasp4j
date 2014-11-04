#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.impl.paymentadapter;

import ${package}.salesmanagement.common.api.datatype.PaymentStatus;

/**
 * Interface of the adapter for external payment.
 * 
 * @author etomety
 */
public interface PaymentAdapter {

  /**
   * @param paymentTransactionData paymentTransactionData
   * @return PaymentStatus
   */
  PaymentStatus pay(PaymentTransactionData paymentTransactionData);
}
