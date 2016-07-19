package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.dao.TableDao;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * This class provides a basic implementation of a {@code ComponentTest} which employs the Mockito framework to provide
 * fake objects to the SUT.
 *
 * @author jmolinar
 */
@RunWith(MockitoJUnitRunner.class)
public class TablemanagementImplTest extends ComponentTest {

  /**
   * The System Under Test (SUT)
   */
  private TablemanagementImpl tableManagementImpl;

  @Mock
  private Salesmanagement salesManagement;

  @Mock
  private Staffmanagement staffManagement;

  @Mock
  private TableDao tableDao;

  @Mock
  private BeanMapper beanMapper;

  @Before
  public void setup() {

    // setup: availability of mocked dependencies is verified and mocks are injected into the SUT
    assertThat(this.salesManagement).isNotNull();
    assertThat(this.staffManagement).isNotNull();
    assertThat(this.tableDao).isNotNull();
    assertThat(this.beanMapper).isNotNull();
    this.tableManagementImpl = new TablemanagementImpl();
    this.tableManagementImpl.setSalesmanagement(this.salesManagement);
    this.tableManagementImpl.setStaffmanagement(this.staffManagement);
    this.tableManagementImpl.setTableDao(this.tableDao);
    this.tableManagementImpl.setBeanMapper(this.beanMapper);

  }

  @After
  public void tearDown() {

    this.salesManagement = null;
    this.staffManagement = null;
    this.beanMapper = null;
    this.tableDao = null;
    this.tableManagementImpl = null;
  }

  @Test
  public void findTable() {

    // setup: here test specific expectations are specified
    // given
    TableEntity entity = Mockito.mock(TableEntity.class);
    TableEto eto = new TableEto();

    Mockito.when(this.tableDao.findOne(1L)).thenReturn(entity);
    Mockito.when(this.beanMapper.map(entity, TableEto.class)).thenReturn(eto);

    // exercise
    // when
    TableEto resultEto = this.tableManagementImpl.findTable(1L);

    // verify
    // then
    Mockito.verify(this.tableDao).findOne(1L);
    Mockito.verify(this.beanMapper).map(entity, TableEto.class);

    assertThat(resultEto).isNotNull();
    assertThat(resultEto).isEqualTo(eto);

    // tear down (nothing to do here)
  }
}
