package io.oasp.module.beanmapping.common.impl;

import static org.junit.Assert.assertEquals;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.beanmapping.common.impl.dozer.DozerBeanMapper;
import io.oasp.module.beanmapping.common.impl.orika.OrikaBeanMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.junit.Before;
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

  private TableEto source;

  private OrikaBeanMapper orika;

  private DozerBeanMapper dozer;

  private TableState sourceTableState;

  /**
   * Initializing both {@link OrikaBeanMapper} and {@link DozerBeanMapper} and instantiating the mapping source
   * {@link TableEto}
   */

  @Before
  public void initialize() {

    this.source = new TableEto();
    this.source.setState(this.sourceTableState);

    // initializing Orika bean mapper
    this.orika = new OrikaBeanMapper();
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    mapperFactory.classMap(TableEto.class, TableEntity.class).byDefault().register();
    this.orika.setOrika(mapperFactory.getMapperFacade());

    // initializing Dozer bean mapper
    this.dozer = new DozerBeanMapper();
    List<String> myMappingFiles = new ArrayList<>();
    myMappingFiles.add("./config/app/common/dozer-mapping.xml");
    org.dozer.DozerBeanMapper mapper = new org.dozer.DozerBeanMapper();
    mapper.setMappingFiles(myMappingFiles);
    this.dozer.setDozer(mapper);
  }

  /**
   * The constructor.
   *
   * @param sourceTableState the {@link TableState} of the source {@link TableEto}
   *
   */
  public OrikaDozerParametrizedTest(TableState sourceTableState) {

    this.sourceTableState = sourceTableState;

  }

  /**
   * @return the different {@link Parameters} to run this test with.
   */
  @Parameters
  public static Collection<Object[]> parameters() {

    return Arrays
        .asList(new Object[][] { new Object[] { TableState.RESERVED }, new Object[] { TableState.OCCUPIED }, });
  }

  /**
   * A special test case that verifies the correct mapping of source and target using {@link OrikaBeanMapper}
   */
  @Test
  public void testOrika() {

    assertEquals(this.sourceTableState, this.orika.map(this.source, TableEntity.class).getState());

  }

  /**
   * A special test case that verifies the correct mapping of source and target using {@link DozerBeanMapper}
   */
  @Test
  public void testDozer() {

    assertEquals(this.sourceTableState, this.orika.map(this.source, TableEntity.class).getState());

  }
}
