package io.oasp.module.beanmapping.common.impl;

import io.oasp.module.beanmapping.common.impl.dozer.BeanMapperImplDozer;

import org.dozer.Mapper;

/**
 * This is the implementation of {@link io.oasp.module.beanmapping.common.api.BeanMapper} using dozer {@link Mapper}.
 *
 * @deprecated - use {@link BeanMapperImplDozer} instead as this class name clashes with
 *             {@link org.dozer.DozerBeanMapper}.
 */
@Deprecated
public class DozerBeanMapper extends BeanMapperImplDozer {

  /**
   * The constructor.
   */
  public DozerBeanMapper() {

    super();
  }

}
