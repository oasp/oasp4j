package io.oasp.module.beanmapping.common.impl;

import static org.junit.Assert.assertEquals;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.beanmapping.common.base.AbstractBeanMapper;
import io.oasp.module.beanmapping.common.impl.dozer.DozerBeanMapper;
import io.oasp.module.beanmapping.common.impl.orika.OrikaBeanMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests both {@link OrikaBeanMapper} and {@link DozerBeanMapper} using parameterized tests that succeed on success of
 * both test-cases
 *
 * @author oelsabba
 */

@RunWith(Parameterized.class)
public class OrikaDozerParametrizedTest {

  private AbstractBeanMapper beanMapper;

  /**
   * The constructor.
   *
   * @param beanMapper the abstract instance of the bean mapper
   *
   */
  public OrikaDozerParametrizedTest(AbstractBeanMapper beanMapper) {

    this.beanMapper = beanMapper;

  }

  /**
   * @return the different {@link Parameters} to run this test with.
   */
  @Parameters
  public static Collection<Object[]> parameters() {

    OrikaBeanMapper orika = new OrikaBeanMapper();
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    mapperFactory.classMap(TableEto.class, TableEntity.class).byDefault().register();
    orika.setOrika(mapperFactory.getMapperFacade());

    DozerBeanMapper dozer = new DozerBeanMapper();
    List<String> myMappingFiles = new ArrayList<>();
    myMappingFiles.add("./config/app/common/dozer-mapping.xml");
    org.dozer.DozerBeanMapper mapper = new org.dozer.DozerBeanMapper();
    mapper.setMappingFiles(myMappingFiles);
    dozer.setDozer(mapper);

    return Arrays.asList(new Object[] { orika }, new Object[] { dozer });
  }

  /**
   * A special test case that verifies the correct mapping of source and target entities using different kinds of bean
   * mappers {@link AbstractBeanMapper} as parameters
   */

  @Test
  public void testBeanMapper() {

    TableEto source = new TableEto();
    source.setState(TableState.RESERVED);

    assertEquals(source.getState(), this.beanMapper.map(source, TableEntity.class).getState());

  }

}
