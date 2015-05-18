package io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.salesexport.processor;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.salesexport.SalesItem;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;

import javax.inject.Inject;

import org.springframework.batch.item.ItemProcessor;

/**
 * TODO ABIELEWI This type ...
 *
 * @author ABIELEWI
 */
public class SalesProcessor implements ItemProcessor<Long, SalesItem> {

  private Salesmanagement salesmanagement;

  private Offermanagement offermanagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public SalesItem process(Long item) throws Exception {

    // TODO Auto-generated method stub
    this.salesmanagement.findBill(item);
    return null;
  }

  /**
   * @param salesmanagement the salesmanagement to set
   */
  @Inject
  public void setSalesmanagement(Salesmanagement salesmanagement) {

    this.salesmanagement = salesmanagement;
  }

  /**
   * @param offermanagement the offermanagement to set
   */
  @Inject
  public void setOffermanagement(Offermanagement offermanagement) {

    this.offermanagement = offermanagement;
  }
}
