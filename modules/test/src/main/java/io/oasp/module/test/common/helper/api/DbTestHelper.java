package io.oasp.module.test.common.helper.api;

/**
 * This interface offers methods to drop ({@link #dropDatabase()}) and reset (
 * {@link #resetDatabase(String migrationVersion)}) a database.
 *
 * @author shuber
 */
public interface DbTestHelper {

  /**
   * Drops the whole database.
   */
  public void dropDatabase();

  /**
   * Migrates the database to {@code migrationVersion}. If {@code migrationVersion} is null, the database is either set
   * to a default version or the migration functionality is left out.
   *
   * @param migrationVersion
   */
  public void resetDatabase(String migrationVersion);
}
