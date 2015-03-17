package io.oasp.module.test.common.base;

import io.oasp.module.test.common.api.category.CategoryIntegrationTest;

import org.assertj.core.api.Assertions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * This is the abstract base class for an integration test. You are free to create your integration tests as you like
 * just by annotating {@link CategoryIntegrationTest} using {@link Category}. However, in most cases it will be
 * convenient just to extend this class.
 *
 * @see CategoryIntegrationTest
 *
 * @author hohwille
 */
// TODO we need to either get local transport to work with CXF or we shall switch to @RunWith(Arquillian.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@Category(CategoryIntegrationTest.class)
public abstract class IntegrationTest extends Assertions {

}
