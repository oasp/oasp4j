package io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.billexport.processor;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillCto;

import javax.inject.Inject;

import org.springframework.batch.item.ItemProcessor;

/**
 * Implementation of ItemProcessor. It finds BillCto by id and returns it.
 *
 */
public class BillProcessor implements ItemProcessor<Long, BillCto> {

  private Salesmanagement salesmanagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public BillCto process(Long item) throws Exception {

    return this.salesmanagement.findBill(item);
  }

  /**
   * @param salesmanagement the salesmanagement to set
   */
  @Inject
  public void setSalesmanagement(Salesmanagement salesmanagement) {

    this.salesmanagement = salesmanagement;
  }

}
