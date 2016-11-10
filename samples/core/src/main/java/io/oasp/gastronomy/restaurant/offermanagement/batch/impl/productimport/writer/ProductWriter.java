package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport.writer;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

/**
 * ProductWriter is responsible for writing ProductEto to database.
 *
 */
public class ProductWriter implements ItemWriter<ProductEto> {

  private static final Logger LOG = LoggerFactory.getLogger(ProductWriter.class);

  private Offermanagement offerManagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(List<? extends ProductEto> items) throws Exception {

    LOG.debug("Writing " + items.size() + " products");

    for (ProductEto item : items) {
      LOG.debug("Saving product: " + item.getName());
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
