package io.oasp.module.beanmapping.common.impl;

import org.dozer.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dozer {@link CustomConverter} that returns the original source object reference (identity conversion).
 *
 * @author hohwille
 * @deprecated As of release 1.0.0, removed to package io.oasp.module.beanmapping.common.impl.dozer
 */

@Deprecated
public class IdentityConverter extends io.oasp.module.beanmapping.common.impl.dozer.IdentityConverter {

  private static final Logger LOG = LoggerFactory.getLogger(IdentityConverter.class);

  /**
   * The constructor.
   */

  public IdentityConverter() {

    super();
    LOG.warn("Deprecated as of release 1.0.0, removed to package {}",
        io.oasp.module.beanmapping.common.impl.dozer.IdentityConverter.class.getPackage());
  }

  /**
   * {@inheritDoc}
   */

  @Override
  public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {

    return source;
  }

}
