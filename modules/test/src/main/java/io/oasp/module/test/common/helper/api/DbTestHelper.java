package io.oasp.module.test.common.helper.api;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since 2.2.0
 */
public interface DbTestHelper {
  public void dropDatabase();

  public void resetDatabase(String migrationVersion);
}
