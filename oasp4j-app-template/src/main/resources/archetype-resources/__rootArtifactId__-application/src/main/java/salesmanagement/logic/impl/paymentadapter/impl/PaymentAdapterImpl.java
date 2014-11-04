#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.impl.paymentadapter.impl;

import ${package}.general.common.base.AbstractBeanMapperSupport;
import ${package}.salesmanagement.common.api.datatype.PaymentStatus;
import ${package}.salesmanagement.logic.impl.paymentadapter.PaymentAdapter;
import ${package}.salesmanagement.logic.impl.paymentadapter.PaymentTransactionData;

import javax.inject.Named;

/**
 * Implementation of the external payment functionalities.
 *
 * @author etomety
 */
@Named
public class PaymentAdapterImpl extends AbstractBeanMapperSupport implements PaymentAdapter {

  /**
   * {@inheritDoc}
   */
  @Override
  public PaymentStatus pay(PaymentTransactionData paymentTransactionData) {

    return PaymentStatus.SUCCESS;

    // return dozer.map(statusTo, PaymentStatus.class);
  }

}
