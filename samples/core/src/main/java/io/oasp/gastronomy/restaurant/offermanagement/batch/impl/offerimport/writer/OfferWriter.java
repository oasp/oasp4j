package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.offerimport.writer;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

/**
 * OfferWriter is responsible for writing OfferEto to database.
 * 
 * @author sroeger
 */
public class OfferWriter implements ItemWriter<OfferEto> {

  private static final Logger LOG = LoggerFactory.getLogger(OfferWriter.class);

  private Offermanagement offerManagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(List<? extends OfferEto> items) throws Exception {

    LOG.debug("Writing " + items.size() + " offers");

    for (OfferEto item : items) {
      LOG.debug("Saving offer: " + item.getName());
      this.offerManagement.saveOffer(item);
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
