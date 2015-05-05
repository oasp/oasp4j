package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.writer;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

/**
 * Implementation of ItemWriter. It writes data in database
 *
 * @author jczas
 */
public class OfferToDbWriter implements ItemWriter<OfferEto> {

  private static final Logger LOG = LoggerFactory.getLogger(OfferToDbWriter.class);

  // @Inject
  private Offermanagement offerManagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(List<? extends OfferEto> items) throws Exception {

    LOG.debug("writing items: " + items);

    for (OfferEto item : items) {
      LOG.debug("writing item: " + item.getNumber());
      // this.offerManagement.saveOffer(item);
    }

  }

}
