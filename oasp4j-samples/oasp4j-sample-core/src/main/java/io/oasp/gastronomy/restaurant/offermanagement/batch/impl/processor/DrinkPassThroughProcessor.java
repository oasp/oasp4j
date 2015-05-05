package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.processor;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Implementation of ItemProcessor. It simply passes data through
 *
 * @author jczas
 */
public class DrinkPassThroughProcessor implements ItemProcessor<DrinkEto, DrinkEto> {

  private static final Logger LOG = LoggerFactory.getLogger(DrinkPassThroughProcessor.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public DrinkEto process(DrinkEto item) throws Exception {

    LOG.debug("processing, item: " + item.getName());
    return item;
  }

}
