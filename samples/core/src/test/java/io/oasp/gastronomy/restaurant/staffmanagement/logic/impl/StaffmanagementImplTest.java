package io.oasp.gastronomy.restaurant.staffmanagement.logic.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.dao.StaffMemberDao;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.test.common.base.ModuleTest;

/**
 * This class provides a basic implementation of a {@code ModuleTest} which employs the Mockito framework to provide
 * fake objects to the SUT. For a limited number of test cases per class it can be convenient to use the annotation
 * {@code @InjectMocks} to inject all needed mocks in the class under test.
 *
 * @author sroeger
 */

@RunWith(MockitoJUnitRunner.class)
public class StaffmanagementImplTest extends ModuleTest {

  @Mock
  private BeanMapper beanMapper;

  @Mock
  private StaffMemberEntity staffMemberEntity;

  @Mock
  private StaffMemberEto staffMemberEto;

  @Mock
  private StaffMemberDao staffMemberDao;

  @InjectMocks
  StaffmanagementImpl staffmanagementImpl = new StaffmanagementImpl();

  @Before
  public void initMocks() {

    MockitoAnnotations.initMocks(this);
  }

  /**
   * This test method tests the {@code findStaffMember} method of {@link StaffmanagementImpl} by mocking needed
   * dependencies.
   */
  @Test
  public void testfindStaffMember() {

    // setup: availability of mocked dependencies is verified
    assertThat(this.staffMemberEntity).isNotNull();
    assertThat(this.staffMemberEto).isNotNull();
    assertThat(this.staffMemberDao).isNotNull();
    assertThat(this.beanMapper).isNotNull();

    // given
    long id = 1L;
    Class<StaffMemberEto> targetClass = StaffMemberEto.class;

    Mockito.when(this.staffMemberDao.find(id)).thenReturn(this.staffMemberEntity);
    Mockito.when(this.beanMapper.map(this.staffMemberEntity, targetClass)).thenReturn(this.staffMemberEto);

    // when

    StaffMemberEto resultEto = this.staffmanagementImpl.findStaffMember(id);

    // then
    Mockito.verify(this.staffMemberDao).find(id);
    Mockito.verify(this.beanMapper).map(this.staffMemberEntity, targetClass);

    assertThat(resultEto).isNotNull();
    assertThat(resultEto).isEqualTo(this.staffMemberEto);

  }

  /**
   * This test method updates a saved staffMemberEntity. It demonstrates the approach when there is a method call in the
   * method under test. To solve this in the given case you need to override the {@code @Inject} of the tested method by
   * using the appropriate setter {@code setStaffMemberDao()}.
   */
  @Test
  public void testSaveStaffMember() {

    // given
    StaffMemberEto staffMemberEto = Mockito.mock(StaffMemberEto.class);

    StaffMemberDao staffMemberDao = Mockito.mock(StaffMemberDao.class);
    StaffMemberEntity staffMemberEntity = Mockito.mock(StaffMemberEntity.class);

    long id = 1L;

    Mockito.when(staffMemberEto.getId()).thenReturn(id);
    Mockito.when(staffMemberDao.find(id)).thenReturn(staffMemberEntity);
    Mockito.when(staffMemberEto.getName()).thenReturn("new Name");
    Mockito.when(staffMemberEntity.getName()).thenReturn("old Name");

    Mockito.when(this.beanMapper.map(staffMemberEto, StaffMemberEntity.class)).thenReturn(staffMemberEntity);
    Mockito.when(staffMemberDao.save(staffMemberEntity)).thenReturn(staffMemberEntity);
    Mockito.when(this.beanMapper.map(staffMemberEntity, StaffMemberEto.class)).thenReturn(staffMemberEto);

    // exercise
    // when
    this.staffmanagementImpl.setStaffMemberDao(staffMemberDao);
    StaffMemberEto resultEto = this.staffmanagementImpl.saveStaffMember(staffMemberEto);

    // verify
    // then
    Mockito.verify(staffMemberEto).getId();
    Mockito.verify(staffMemberDao).find(id);
    Mockito.verify(staffMemberEto, Mockito.atLeastOnce()).getName();
    Mockito.verify(staffMemberEntity, Mockito.atLeastOnce()).getName();
    Mockito.verify(this.beanMapper).map(staffMemberEto, StaffMemberEntity.class);
    Mockito.verify(staffMemberDao).save(staffMemberEntity);
    Mockito.verify(this.beanMapper).map(staffMemberEntity, StaffMemberEto.class);
    assertThat(resultEto.getId()).isEqualTo(staffMemberEto.getId());

  }

}