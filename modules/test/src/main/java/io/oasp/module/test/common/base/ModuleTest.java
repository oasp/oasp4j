package io.oasp.module.test.common.base;

import org.junit.experimental.categories.Category;

import io.oasp.module.test.common.api.category.CategoryModuleTest;

/**
 * This is the abstract base class for a module test. You are free to create your module tests as you like just by
 * annotating {@link CategoryModuleTest} using {@link Category}. However, in most cases it will be convenient just to
 * extend this class.
 *
 * @see CategoryModuleTest
 *
 * @author hohwille
 */
@Category(CategoryModuleTest.class)
public abstract class ModuleTest extends BaseTest {

}
