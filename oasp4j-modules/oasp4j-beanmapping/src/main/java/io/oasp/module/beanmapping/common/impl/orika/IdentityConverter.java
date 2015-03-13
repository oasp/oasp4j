package io.oasp.module.beanmapping.common.impl.orika;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * TODO oelsabba This type ...
 *
 * @author oelsabba
 */
public class IdentityConverter extends CustomConverter {

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
  public Object convert(Object source, Type destinationType) {

    return source;
  }

}
