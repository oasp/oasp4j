package io.oasp.gastronomy.restaurant.staffmanagement.logic.impl;

import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
 */

public class StaffmanagementImplTest extends ModuleTest {

  /**
   * Initializes mocks before each test method.
   */
  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

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

  /**
   * This test method tests the {@code findStaffMember} method of {@link StaffmanagementImpl} by mocking needed
   * dependencies.
   */
  @Test
  public void testFindStaffMember() {

    // given
    long id = 1L;
    Class<StaffMemberEto> targetClass = StaffMemberEto.class;

    when(this.staffMemberDao.find(id)).thenReturn(this.staffMemberEntity);
    when(this.beanMapper.map(this.staffMemberEntity, targetClass)).thenReturn(this.staffMemberEto);

    // when
    StaffMemberEto resultEto = this.staffmanagementImpl.findStaffMember(id);

    // then
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
    long id = 1L;

    when(this.staffMemberEto.getId()).thenReturn(id);
    when(this.staffMemberDao.find(id)).thenReturn(this.staffMemberEntity);
    when(this.staffMemberEto.getName()).thenReturn("new Name");
    when(this.staffMemberEntity.getName()).thenReturn("old Name");
    when(this.beanMapper.map(this.staffMemberEto, StaffMemberEntity.class)).thenReturn(this.staffMemberEntity);
    when(this.staffMemberDao.save(this.staffMemberEntity)).thenReturn(this.staffMemberEntity);
    when(this.beanMapper.map(this.staffMemberEntity, StaffMemberEto.class)).thenReturn(this.staffMemberEto);

    // when
    this.staffmanagementImpl.setStaffMemberDao(this.staffMemberDao);
    StaffMemberEto resultEto = this.staffmanagementImpl.saveStaffMember(this.staffMemberEto);

    // then
    assertThat(resultEto.getId()).isEqualTo(this.staffMemberEto.getId());
  }
}