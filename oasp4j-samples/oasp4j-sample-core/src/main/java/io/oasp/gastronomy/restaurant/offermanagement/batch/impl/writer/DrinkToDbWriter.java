package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.writer;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

/**
 * Implementation of ItemWriter. It writes data in database
 *
 * @author jczas
 */
public class DrinkToDbWriter implements ItemWriter<DrinkEto> {

  private static final Logger LOG = LoggerFactory.getLogger(DrinkToDbWriter.class);

  // @Inject
  private Offermanagement offerManagement;

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(List<? extends DrinkEto> items) throws Exception {

    LOG.debug("writing items: " + items);

    for (DrinkEto item : items) {
      LOG.debug("writing item: " + item.getName());
      // this.offerManagement.saveProduct(item);
    }

  }

}
