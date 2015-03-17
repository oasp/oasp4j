package io.oasp.module.test.common.base;

import io.oasp.module.test.common.api.category.CategorySystemTest;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * This is the abstract base class for a system test. You are free to create your system tests as you like just by
 * annotating {@link CategorySystemTest} using {@link Category}. However, in most cases it will be convenient just to
 * extend this class.
 *
 * @see CategorySystemTest
 *
 * @author hohwille
 */
@RunWith(Arquillian.class)
@Category(CategorySystemTest.class)
public abstract class SystemTest extends Assertions {

}
