package io.oasp.module.beanmapping.common.impl;

import static org.junit.Assert.assertEquals;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.beanmapping.common.impl.orika.OrikaBeanMapper;

import javax.inject.Inject;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.junit.Test;

/**
 * TODO oelsabba This is a test-case for {@link OrikaBeanMapper}
 *
 * @author oelsabba
 */
public class OrikaBeanMapperTest {

  @Inject
  private OrikaBeanMapper orika;

  /**
   * Tests the bidirectional-mapping using Orika bean mapper {@link OrikaBeanMapper}
   */
  @Test
  public void testOrikaBeanMapper() {

    this.orika = new OrikaBeanMapper();
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    mapperFactory.classMap(TableEto.class, TableEntity.class).byDefault().register();
    this.orika.setOrika(mapperFactory.getMapperFacade());

    TableEto forwardSource = new TableEto();
    forwardSource.setState(TableState.OCCUPIED);

    TableEntity forwardTarget = this.orika.map(forwardSource, TableEntity.class);

    assertEquals(forwardSource.getState(), forwardTarget.getState());

    TableEntity backwardTarget = new TableEntity();
    backwardTarget.setState(TableState.RESERVED);

    TableEto backwardSource = this.orika.map(backwardTarget, TableEto.class);

    assertEquals(backwardSource.getState(), backwardTarget.getState());
  }
}
