package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport.writer;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;

import java.util.List;

import javax.inject.Inject;

import org.springframework.batch.item.ItemWriter;

/**
 * ProductWriter is responsible for writing ProductEto to database.
 *
 * @author abielewi
 */
public class ProductWriter implements ItemWriter<ProductEto> {

  private Offermanagement offerManagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(List<? extends ProductEto> items) throws Exception {

    for (ProductEto item : items) {
      this.offerManagement.saveProduct(item);
    }

  }

  /**
   * @param offerManagement the offerManagement to set
   */
  @Inject
  public void setOfferManagement(Offermanagement offerManagement) {

    this.offerManagement = offerManagement;
  }
}
