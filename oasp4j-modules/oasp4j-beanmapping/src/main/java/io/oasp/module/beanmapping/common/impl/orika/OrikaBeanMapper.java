package io.oasp.module.beanmapping.common.impl.orika;

import io.oasp.module.beanmapping.common.base.AbstractBeanMapper;

import javax.inject.Inject;

import ma.glasnost.orika.MapperFacade;

/**
 * TODO This is the implementation of {@link io.oasp.module.beanmapping.common.api.BeanMapper} using orika
 * {@link ma.glasnost.orika.MapperFacade}.
 *
 * @author oelsabba
 */
public class OrikaBeanMapper extends AbstractBeanMapper {

  /** The orika instance to use. */
  private MapperFacade orika;

  /**
   * The constructor.
   */
  public OrikaBeanMapper() {

    super();

  }

  /**
   * @param orika is the {@link ma.glasnost.orika.Mapper} to {@link Inject}.
   */
  @Inject
  public void setOrika(MapperFacade orika) {

    this.orika = orika;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T map(Object source, Class<T> targetType) {

    if (source == null) {
      return null;
    }

    return this.orika.map(source, targetType);
  }
}
