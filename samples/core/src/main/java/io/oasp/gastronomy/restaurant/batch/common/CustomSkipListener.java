package io.oasp.gastronomy.restaurant.batch.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.listener.SkipListenerSupport;

/**
 * Implementation of a {@link SkipListener} for logging skipped items in batch processes.
 *
 * @author sroeger
 *
 */
public class CustomSkipListener<T, S> extends SkipListenerSupport<T, S> {

  private static final Logger LOG = LoggerFactory.getLogger(CustomSkipListener.class);

  @Override
  public void onSkipInRead(Throwable t) {

    LOG.warn("skipped item: {}", t.toString());
  }

}