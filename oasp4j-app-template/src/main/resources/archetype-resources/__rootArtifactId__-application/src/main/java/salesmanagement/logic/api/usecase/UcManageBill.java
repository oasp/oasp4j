#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api.usecase;

import ${package}.general.common.api.datatype.Money;
import ${package}.salesmanagement.common.api.datatype.PaymentStatus;
import ${package}.salesmanagement.logic.api.to.BillEto;
import ${package}.salesmanagement.logic.api.to.OrderPositionEto;
import ${package}.salesmanagement.logic.api.to.PaymentData;

import java.util.List;

/**
 * Interface of UcManageBill to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageBill {
  /**
   * Create a {@link BillEto} and save it in the database by linking the billed {@link OrderPositionEto order positions}
   * with it. A bill also consists of the {@link Money total amount} as well as the {@link Money tip}, the waiter could
   * fill in. The tip is at default 0.0.
   *
   * @param orderPositions is the {@link List} of {@link OrderPositionEto}, the billing process places on.
   * @param tip is the waiter's {@link Money}
   *
   * @return the created {@link BillEto}
   */
  BillEto createBill(List<OrderPositionEto> orderPositions, Money tip);

  /**
   * This method deletes a {@link BillEto} from the database by its given id.
   *
   * @param billId the ID of the {@link BillEto} that has to be deleted.
   */
  void deleteBill(Long billId);

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
