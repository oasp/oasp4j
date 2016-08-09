package io.oasp.module.test.common.base;

import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import io.oasp.module.test.common.api.category.CategoryComponentTest;
import io.oasp.module.test.common.helper.api.DbTestHelper;

/**
 * This is the abstract base class for a component test. You are free to create your component tests as you like just by
 * annotating {@link CategoryComponentTest} using {@link Category}. However, in most cases it will be convenient just to
 * extend this class.
 *
 * @see CategoryComponentTest
 *
 * @author hohwille
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@Category(CategoryComponentTest.class)
public abstract class ComponentTest extends BaseTest {

  protected DbTestHelper dbTestHelper;

  private static boolean DB_NEEDS_RESET = true;

  @Override
  protected void doSetUp() {

    doDatabaseSetUp();
  }

  @Override
  protected void doTearDown() {

    doDatabaseTearDown();
  }

  protected void doDatabaseSetUp() {

    if (dbNeedsReset()) {
      getDbTestHelper().resetDatabase(null);
    }
    setDbNeedsReset(true);
  }

  protected void doDatabaseTearDown() {

  }

  /**
   * @return isModyfyingTest
   */
  private static boolean dbNeedsReset() {

    return DB_NEEDS_RESET;
  }

  /**
   * @param IS_MODIFYING_TEST new value of {@link #getisModyfyingTest}.
   */
  protected void setDbNeedsReset(boolean dbNeedsReset) {

    DB_NEEDS_RESET = dbNeedsReset;
  }

  /**
   * @return the {@link DbTestHelper}
   */
  protected DbTestHelper getDbTestHelper() {

    return this.dbTestHelper;
  }
}
