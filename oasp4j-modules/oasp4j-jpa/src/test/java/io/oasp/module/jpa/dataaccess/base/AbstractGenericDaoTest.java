package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;
import io.oasp.module.jpa.dataaccess.api.GenericDao;
import io.oasp.module.test.common.base.ComponentTest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Test class to test the {@link GenericDao}.
 *
 * @author fawinter
 */
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_DATA_ACCESS })
public class AbstractGenericDaoTest extends ComponentTest {

  @Inject
  private GenericDaoForceIncrementModificationTestBean testBean;

  /**
   * The Modification Counter should have changed +1 when doing two transactions to the database
   *
   * @see GenericDao#forceIncrementModificationCounter(net.sf.mmm.util.entity.api.PersistenceEntity)
   */
  @Test
  public void testForceIncrementModificationCounter() {

    // given
    TestEntity entity = this.testBean.create();
    assertThat(entity.getId()).isNotNull();
    assertThat(entity.getModificationCounter()).isEqualTo(0);

    // when
    TestEntity updatedEntity = this.testBean.updateAndIncrementModificationCounter(entity.getId());

    // then
    assertThat(updatedEntity.getModificationCounter()).isEqualTo(1);
  }

  /**
   *
   * This type provides methods in a transactional environment for the containing test class. All methods, annotated
   * with the {@link Transactional} annotation, are executed in separate transaction, thus one test case can execute
   * multiple transactions.
   */
  @Named
  public static class GenericDaoForceIncrementModificationTestBean {

    @Inject
    private TestDao genericDao;

    /**
     * first transaction
     *
     * @return entity
     */
    @Transactional
    public TestEntity create() {

      TestEntity entity = new TestEntity();
      this.genericDao.save(entity);
      return entity;
    }

    /**
     * second transaction
     *
     * @param id of the TestEntity we just created
     * @return entity
     */
    @Transactional
    public TestEntity updateAndIncrementModificationCounter(long id) {

      TestEntity entity = this.genericDao.find(id);
      this.genericDao.forceIncrementModificationCounter(entity);
      return entity;
    }
  }

};
