package io.oasp.module.beanmapping.common.impl;

import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.beanmapping.common.impl.orika.BeanMapperImplOrika;
import io.oasp.module.beanmapping.common.impl.orika.CustomMapperEto;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.transferobject.api.EntityTo;

/**
 * Test of {@link BeanMapperImplOrika} based on {@link AbstractBeanMapperTest}.
 *
 * @author hohwille
 */
public class BeanMapperImplOrikaTest extends AbstractBeanMapperTest {

  @Override
  protected BeanMapper getBeanMapper() {

    BeanMapperImplOrika mapper = new BeanMapperImplOrika();
    MapperFactory factory = new DefaultMapperFactory.Builder().build();
    CustomMapperEto customMapper = new CustomMapperEto();
    customMapper.initialize();
    factory.classMap(GenericEntity.class, EntityTo.class).customize(customMapper).byDefault().favorExtension(true)
        .register();
    MapperFacade orika = factory.getMapperFacade();
    mapper.setOrika(orika);
    return mapper;
  }
}
