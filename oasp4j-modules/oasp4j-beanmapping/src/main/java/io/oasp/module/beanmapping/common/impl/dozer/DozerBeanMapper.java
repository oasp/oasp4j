package io.oasp.module.beanmapping.common.impl.dozer;

import io.oasp.module.beanmapping.common.base.AbstractBeanMapper;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

/**
 * This is the implementation of {@link io.oasp.module.beanmapping.common.api.BeanMapper} using dozer {@link Mapper}.
 * 
 * @author hohwille
 */
@Named
public class DozerBeanMapper extends AbstractBeanMapper {

  /** The dozer instance to use. */
  private Mapper dozer;

  /**
   * The constructor.
   */
  public DozerBeanMapper() {

    super();
  }

  /**
   * @param dozer is the {@link Mapper} to {@link Inject}.
   */
  @Inject
  public void setDozer(Mapper dozer) {

    this.dozer = dozer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T map(Object source, Class<T> targetType) {

    if (source == null) {
      return null;
    }
    return this.dozer.map(source, targetType);
  }

}
