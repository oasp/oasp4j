package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.dao.TableDao;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.test.common.base.ModuleTest;

/**
 * This class provides a basic implementation of a {@code ComponentTest} which employs the Mockito framework to provide
 * fake objects to the SUT.
 *
 */
public class TablemanagementImplTest extends ModuleTest {

  /**
   * Initializes mocks before each test method.
   */
  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

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

  /**
   * Injection of the mocked objects into the SUT.
   */
  @Override
  public void doSetUp() {

    super.doSetUp();
    this.tableManagementImpl = new TablemanagementImpl();
    this.tableManagementImpl.setSalesmanagement(this.salesManagement);
    this.tableManagementImpl.setStaffmanagement(this.staffManagement);
    this.tableManagementImpl.setTableDao(this.tableDao);
    this.tableManagementImpl.setBeanMapper(this.beanMapper);

  }

  /**
   * Delete the used mocks.
   */
  @Override
  public void doTearDown() {

    super.doTearDown();
    this.salesManagement = null;
    this.staffManagement = null;
    this.beanMapper = null;
    this.tableDao = null;
    this.tableManagementImpl = null;
  }

  /**
   * This method tests the findTable method.
   */
  @Test
  public void findTable() {

    // given
    TableEntity entity = mock(TableEntity.class);
    TableEto eto = new TableEto();

    when(this.tableDao.findOne(1L)).thenReturn(entity);
    when(this.beanMapper.map(entity, TableEto.class)).thenReturn(eto);

    // when
    TableEto resultEto = this.tableManagementImpl.findTable(1L);

    // then
    assertThat(resultEto).isNotNull();
    assertThat(resultEto).isEqualTo(eto);
  }
}
