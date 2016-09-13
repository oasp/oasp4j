package io.oasp.module.test.common.base;

import io.oasp.module.test.common.api.category.CategorySubsystemTest;

import org.assertj.core.api.Assertions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * This is the abstract base class for an integration test. You are free to create your integration tests as you like
 * just by annotating {@link CategorySubsystemTest} using {@link Category}. However, in most cases it will be convenient
 * just to extend this class.
 *
 * @see CategorySubsystemTest
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@Category(CategorySubsystemTest.class)
public abstract class SubsystemTest extends Assertions {

}
