package io.oasp.module.beanmapping.common.impl.dozer;

import org.dozer.CustomConverter;

/**
 * Dozer {@link CustomConverter} that returns the original source object reference (identity conversion).
 *
 * @author hohwille
 */
public class IdentityConverter implements CustomConverter {

  /**
   * The constructor.
   */
  public IdentityConverter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {

    return source;
  }

}
