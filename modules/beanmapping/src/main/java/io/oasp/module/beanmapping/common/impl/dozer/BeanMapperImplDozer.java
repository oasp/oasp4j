package io.oasp.module.beanmapping.common.impl.dozer;

import io.oasp.module.beanmapping.common.base.AbstractBeanMapper;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

/**
 * This is the implementation of {@link io.oasp.module.beanmapping.common.api.BeanMapper} using dozer {@link Mapper}.
 *
 * @author hohwille
 * @since 1.3.0
 */
@Named
public class BeanMapperImplDozer extends AbstractBeanMapper {

  /** The dozer instance to use. */
  private Mapper dozer;

  /**
   * The constructor.
   */
  public BeanMapperImplDozer() {

    super();
  }

  /**
   * @param dozer is the {@link Mapper} to {@link Inject}.
   */
  @Inject
  public void setDozer(Mapper dozer) {

    this.dozer = dozer;
  }

  @Override
  public <T> T map(Object source, Class<T> targetClass) {

    if (source == null) {
      return null;
    }
    return this.dozer.map(source, targetClass);
  }

}
