package io.oasp.gastronomy.restaurant.staffmanagement.logic.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.dao.StaffMemberDao;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.test.common.base.ModuleTest;

/**
 * This class provides a basic implementation of a {@code ModuleTest} which employs the Mockito framework to provide
 * fake objects to the SUT.
 *
 * @author sroeger
 */

@RunWith(MockitoJUnitRunner.class)
public class StaffmanagementTest extends ModuleTest {

  @Mock
  private BeanMapper beanMapper;

  @Mock
  private StaffMemberEntity entity;

  @Mock
  private StaffMemberEto eto;

  @Mock
  private StaffMemberDao staffMemberDao;

  @InjectMocks
  StaffmanagementImpl staffmanagementImpl = new StaffmanagementImpl();

  /**
   * This test method tests the {@code findStaffMember} method of {@link StaffmanagementImpl} by mocking needed
   * dependencies.
   */
  @Test
  public void testfindStaffMember() {

    // setup: availability of mocked dependencies is verified
    assertThat(this.entity).isNotNull();
    assertThat(this.eto).isNotNull();
    assertThat(this.staffMemberDao).isNotNull();
    assertThat(this.beanMapper).isNotNull();

    // given
    long id = 1L;
    Class<StaffMemberEto> targetClass = StaffMemberEto.class;

    Mockito.when(this.staffMemberDao.find(id)).thenReturn(this.entity);
    Mockito.when(this.beanMapper.map(this.entity, targetClass)).thenReturn(this.eto);

    // when

    StaffMemberEto resultEto = this.staffmanagementImpl.findStaffMember(id);

    // then
    Mockito.verify(this.staffMemberDao).find(id);
    Mockito.verify(this.beanMapper).map(this.entity, targetClass);

    assertThat(resultEto).isNotNull();
    assertThat(resultEto).isEqualTo(this.eto);

    // teardown
    // clear all used fields

    this.entity = null;
    this.eto = null;
    this.staffMemberDao = null;
    this.beanMapper = null;
    this.staffmanagementImpl = null;
  }
}