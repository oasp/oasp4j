package io.oasp.module.jpa.dataaccess.base;

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
 */
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration({ "classpath:config/app/dataaccess/beans-dataaccess.xml" })
public class AbstractGenericDaoTest extends ComponentTest {

  @Inject
  private GenericDaoForceIncrementModificationTestBean testBean;

  /**
   * Test of {@link GenericDao#forceIncrementModificationCounter(net.sf.mmm.util.entity.api.PersistenceEntity)}. Ensures
   * that the modification counter is updated after the call of that method when the transaction is closed.
   */
  @Test
  public void testForceIncrementModificationCounter() {

    // given
    TestEntity entity = this.testBean.create();
    assertThat(entity.getId()).isNotNull();
    assertThat(entity.getModificationCounter()).isEqualTo(0);

    // when
    TestEntity updatedEntity = this.testBean.incrementModificationCounter(entity.getId());

    // then
    assertThat(updatedEntity.getModificationCounter()).isEqualTo(1);
  }

  /**
   * This type provides methods in a transactional environment for the containing test class. All methods, annotated
   * with the {@link Transactional} annotation, are executed in separate transaction, thus one test case can execute
   * multiple transactions. Unfortunately this does not work when the transactional methods are directly in the
   * top-level class of the test-case itself.
   */
  @Named
  public static class GenericDaoForceIncrementModificationTestBean {

    @Inject
    private TestDao genericDao;

    /**
     * Creates a new {@link TestEntity}, persist it and surround everything with a transaction.
     *
     * @return entity the new {@link TestEntity}.
     */
    @Transactional
    public TestEntity create() {

      TestEntity entity = new TestEntity();
      this.genericDao.save(entity);
      return entity;
    }

    /**
     * Loads the {@link TestEntity} with the given {@code id} and
     * {@link GenericDao#forceIncrementModificationCounter(net.sf.mmm.util.entity.api.PersistenceEntity) increments the
     * modification counter}.
     *
     * @param id of the {@link TestEntity} to load and increment.
     * @return entity the updated {@link TestEntity}.
     */
    @Transactional
    public TestEntity incrementModificationCounter(long id) {

      TestEntity entity = this.genericDao.find(id);
      this.genericDao.forceIncrementModificationCounter(entity);
      return entity;
    }
  }

};
