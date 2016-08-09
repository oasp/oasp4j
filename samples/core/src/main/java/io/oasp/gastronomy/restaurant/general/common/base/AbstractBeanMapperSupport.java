package io.oasp.gastronomy.restaurant.general.common.base;

import javax.inject.Inject;

import io.oasp.module.beanmapping.common.api.BeanMapper;

/**
 * This abstract class provides {@link #getBeanMapper() access} to the {@link BeanMapper}.
 *
 * @author mbrunnli
 */
public abstract class AbstractBeanMapperSupport {

  /** @see #getBeanMapper() */
  private BeanMapper beanMapper;

  /**
   * @param beanMapper is the {@link BeanMapper} to {@link Inject}
   */
  @Inject
  public void setBeanMapper(BeanMapper beanMapper) {

    this.beanMapper = beanMapper;
  }

  /**
   * @return the {@link BeanMapper} instance.
   */
  protected BeanMapper getBeanMapper() {

    return this.beanMapper;
  }

}
