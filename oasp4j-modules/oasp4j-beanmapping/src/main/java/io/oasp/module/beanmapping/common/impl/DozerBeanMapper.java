package io.oasp.module.beanmapping.common.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the implementation of {@link io.oasp.module.beanmapping.common.api.BeanMapper} using dozer {@link Mapper}.
 *
 * @author hohwille
 * @deprecated As of release 1.0.0, removed to package io.oasp.module.beanmapping.common.impl.dozer
 */
@Named
@Deprecated
public class DozerBeanMapper extends io.oasp.module.beanmapping.common.impl.dozer.DozerBeanMapper {

  private static final Logger LOG = LoggerFactory.getLogger(DozerBeanMapper.class);

  /** The dozer instance to use. */
  private Mapper dozer;

  /**
   * The constructor.
   */
  public DozerBeanMapper() {

    super();
    LOG.warn("Deprecated as of release 1.0.0, removed to package {}",
        io.oasp.module.beanmapping.common.impl.dozer.DozerBeanMapper.class.getPackage());
  }

  /**
   * @param dozer is the {@link Mapper} to {@link Inject}.
   */
  @Override
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
