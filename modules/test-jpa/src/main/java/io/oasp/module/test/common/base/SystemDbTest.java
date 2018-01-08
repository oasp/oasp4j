package io.oasp.module.test.common.base;

import org.junit.experimental.categories.Category;

import io.oasp.module.test.common.api.category.CategorySystemTest;

/**
 * Combination of {@link DbTest} with {@link SystemTest}.
 */
@Category(CategorySystemTest.class)
public class SystemDbTest extends DbTest {

}
