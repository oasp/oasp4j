package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.processor;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Implementation of ItemProcessor. It simply passes data through
 *
 * @author jczas
 */
public class OfferPassThroughProcessor implements ItemProcessor<OfferEto, OfferEto> {

  private static final Logger LOG = LoggerFactory.getLogger(OfferPassThroughProcessor.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public OfferEto process(OfferEto item) throws Exception {

    LOG.debug("processing, item: " + item.getNumber());
    return item;
  }

}
