package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindBill;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractBillUc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcFindBill}.
 *
 */
@Named
@UseCase
public class UcFindBillImpl extends AbstractBillUc implements UcFindBill {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindBillImpl.class);

  private Salesmanagement salesmanagement;

  @Override
  @RolesAllowed(PermissionConstants.FIND_BILL)
  public BillCto findBill(long id) {

    LOG.debug("Get Bill with id '" + id + "' from database.");
    BillCto billCto = new BillCto();
    BillEto bill = getBeanMapper().map(getBillDao().findOne(id), BillEto.class);
    if (bill == null) {
      return null;
    }
    billCto.setBill(bill);

    List<OrderPositionEto> orderPositions = new ArrayList<>();
    for (Long orderPositionId : bill.getOrderPositionIds()) {
      OrderPositionEto position = this.salesmanagement.findOrderPosition(orderPositionId);
      orderPositions.add(position);
    }
    billCto.setPositions(orderPositions);

    return billCto;
  }

  /**
   * @param salesmanagement the salesmanagement to set
   */
  @Inject
  public void setSalesmanagement(Salesmanagement salesmanagement) {

    this.salesmanagement = salesmanagement;
  }

}
