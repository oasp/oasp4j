package io.oasp.module.beanmapping.common.impl;

import org.dozer.CustomConverter;

/**
 * Dozer {@link CustomConverter} that returns the original source object reference (identity conversion).
 *
 * @deprecated - use {@link io.oasp.module.beanmapping.common.impl.dozer.IdentityConverter}
 */
@Deprecated
public class IdentityConverter implements CustomConverter {

  /**
   * The constructor.
   */
  public IdentityConverter() {

    super();
    System.err.println("This class is deprecated. Please use "
        + io.oasp.module.beanmapping.common.impl.dozer.IdentityConverter.class.getName());
  }

  @Override
  public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {

    return source;
  }

}
