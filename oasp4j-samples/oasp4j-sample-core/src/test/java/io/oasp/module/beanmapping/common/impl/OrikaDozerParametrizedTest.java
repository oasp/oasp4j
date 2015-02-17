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

  private OrikaBeanMapper orika;

  private DozerBeanMapper dozer;

  private TableState orikaTableStateTarget;

  private TableState dozerTableStateTarget;

  /**
   * Initializing both {@link OrikaBeanMapper} and {@link DozerBeanMapper} with the concrete source Object and target
   * Class
   */
  @Before
  public void initialize() {

    this.orika = new OrikaBeanMapper();
    this.dozer = new DozerBeanMapper();
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    mapperFactory.classMap(TableEto.class, TableEntity.class).byDefault().register();
    this.orika.setOrika(mapperFactory.getMapperFacade());

    List<String> myMappingFiles = new ArrayList<>();
    myMappingFiles.add("./config/app/common/dozer-mapping.xml");
    org.dozer.DozerBeanMapper mapper = new org.dozer.DozerBeanMapper();
    mapper.setMappingFiles(myMappingFiles);
    this.dozer.setDozer(mapper);

  }

  /**
   * The constructor.
   *
   * @param source the {@link TableState} of the source {@link TableEto}
   * @param target the {@link TableState} of the target {@link TableEto}
   */
  public OrikaDozerParametrizedTest(TableState source, TableState target) {

    this.orikaTableStateTarget = source;
    this.dozerTableStateTarget = target;
  }

  /**
   * @return the different {@link Parameters} to run this test with.
   */
  @Parameters
  public static Collection<Object[]> parameters() {

    return Arrays.asList(new Object[][] { { TableState.RESERVED, TableState.RESERVED } });

  }

  /**
   * A special test case that verifies the correct mapping of source and target
   */
  @Test
  public void testOrikaAndDozer() {

    TableEto source = new TableEto();
    source.setState(TableState.RESERVED);

    this.orikaTableStateTarget = this.orika.map(source, TableEntity.class).getState();
    this.dozerTableStateTarget = this.dozer.map(source, TableEntity.class).getState();

    assertEquals(this.orikaTableStateTarget, this.dozerTableStateTarget);

  }
}
