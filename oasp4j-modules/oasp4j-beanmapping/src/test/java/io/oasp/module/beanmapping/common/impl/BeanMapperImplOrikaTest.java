package io.oasp.module.beanmapping.common.impl;

import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.beanmapping.common.impl.orika.BeanMapperImplOrika;
import io.oasp.module.beanmapping.common.impl.orika.CustomMapperEto;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import net.sf.mmm.util.entity.base.AbstractRevisionedEntity;
import net.sf.mmm.util.transferobject.api.EntityTo;

import org.junit.Ignore;

/**
 * Test of {@link BeanMapperImplOrika} based on {@link AbstractBeanMapperTest}.
 *
 * @author hohwille
 */
@Ignore
public class BeanMapperImplOrikaTest extends AbstractBeanMapperTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected BeanMapper getBeanMapper() {

    BeanMapperImplOrika mapper = new BeanMapperImplOrika();
    MapperFactory factory = new DefaultMapperFactory.Builder().build();
    CustomMapperEto customMapper = new CustomMapperEto();
    customMapper.initialize();
    // mapping via interface seems not to work in orika...
    // factory.classMap(PersistenceEntity.class, EntityTo.class).customize(customMapper).byDefault().register();

    // already this breaks the default mapping compared to not configuring a classMap.
    // factory.classMap(AbstractRevisionedEntity.class, EntityTo.class).byDefault().register();
    factory.classMap(AbstractRevisionedEntity.class, EntityTo.class).customize((Mapper) customMapper).byDefault()
        .register();
    MapperFacade orika = factory.getMapperFacade();
    mapper.setOrika(orika);
    return mapper;
  }
}
