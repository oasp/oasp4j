package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter.impl;

import io.oasp.gastronomy.restaurant.general.common.base.AbstractBeanMapperSupport;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter.PaymentAdapter;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.paymentadapter.PaymentTransactionData;

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
