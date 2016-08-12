package io.oasp.gastronomy.restaurant.general.common;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;

import io.oasp.module.test.common.helper.api.DbTestHelper;

/**
 * This class provides methods for handling the database during testing where resets (and other operations) may be
 * necessary.
 *
 * @author jmolinar, shuber
 */
public class DbTestHelperImpl implements DbTestHelper {
  private Flyway flyway;

  private MigrationVersion migrationVersion;

  /**
   * The constructor.
   *
   * @param flyway an instance of type {@link Flyway}.
   * @param migrationVersion.
   */
  public DbTestHelperImpl(Flyway flyway, String migrationVersion) {

    this.flyway = flyway;
    this.migrationVersion = MigrationVersion.fromVersion(migrationVersion);
  }

  @Override
  public void dropDatabase() {

    this.flyway.clean();
  }

  /**
   * Calls {@link #dropDatabase()} internally, and migrates to the highest available migration (default) or to the
   * {@code migrationVersion}.
   */
  @Override
  public void resetDatabase(String migrationVersion) {

    dropDatabase();

    if (migrationVersion != null) {
      this.migrationVersion = MigrationVersion.fromVersion(migrationVersion);
    }
    if (this.migrationVersion != null) {
      this.flyway.setTarget(this.migrationVersion);
    }
    this.flyway.migrate();
  }
}
