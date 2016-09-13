package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.PaymentStatus;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;

/**
 * Interface of UcManageBill to centralize documentation and signatures of methods.
 *
 */
public interface UcManageBill {

  /**
   * Create a {@link BillEto} and save it in the database by linking the billed {@link OrderPositionEto order positions}
   * with it. A bill also consists of the {@link Money total amount} as well as the {@link Money tip}, the waiter could
   * fill in. The tip is at default 0.0.
   *
   * @param bill that gets created in the database.
   * @return the created {@link BillEto}
   */
  BillEto createBill(BillEto bill);

  /**
   * This method deletes a {@link BillEto} from the database by its given id.
   *
   * @param billId the ID of the {@link BillEto} that has to be deleted.
   */
  void deleteBill(long billId);

  /**
   * This method provides the payment process. It provides the cash-only functionality without any additional
   * {@link PaymentData}.
   *
   * @param bill is the identifier of the {@link BillEto} on which the payment process takes place.
   * @return the {@link PaymentStatus status code} of the payment process.
   */
  PaymentStatus doPayment(BillEto bill);

  /**
   * This method provides the payment process.
   *
   * @param paymentDataDebitor is the {@link PaymentData} which holds special data for the payment process.
   * @param bill is the identifier of the {@link BillEto} on which the payment process takes place.
   * @return the {@link PaymentStatus status code} of the payment process.
   */
  PaymentStatus doPayment(BillEto bill, PaymentData paymentDataDebitor);

}
