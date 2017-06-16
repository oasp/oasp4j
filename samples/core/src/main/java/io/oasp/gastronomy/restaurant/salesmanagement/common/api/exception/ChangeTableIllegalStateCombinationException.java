package io.oasp.gastronomy.restaurant.salesmanagement.common.api.exception;

import io.oasp.gastronomy.restaurant.general.common.api.NlsBundleApplicationRoot;
import io.oasp.gastronomy.restaurant.general.common.api.exception.ApplicationBusinessException;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * This exception is thrown if a table has a specific state(occupied) that is illegal for the UcChangeTableImpl and
 * caused it to fail.
 *
 */
public class ChangeTableIllegalStateCombinationException extends ApplicationBusinessException {

  /**
   * UID for serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   *
   * The constructor.
   *
   * @param order OrderEto which is going to be transfered to the targetTable
   * @param targetTable is the table which is getting the Order
   */
  public ChangeTableIllegalStateCombinationException(OrderEto order, TableEto targetTable) {

    super(createBundle(NlsBundleApplicationRoot.class).errorChangeTableIllegalStateCombination(order.getId(),
        targetTable.getNumber()));
  }

}
