package io.oasp.gastronomy.restaurant.general.common;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;

/**
 * This class serves as a helper for subsystem tests.
 *
 * @author jmolinar
 */

public class RestaurantTestHelper {

  private Flyway flyway;

  private MigrationVersion migrationVersion;

  private MigrationVersion baselineVersion;

  /**
   * The constructor.
   */
  public RestaurantTestHelper() {
    super();
  }

  /**
   * The constructor.
   *
   * @param flyway The instance of type {@link Flyway}.
   */
  public RestaurantTestHelper(Flyway flyway) {
    super();
    this.flyway = flyway;
  }

  public void login(String name) {

    // TODO implement in later iteration
  }

  public void logout() {

    // TODO implement in later iteration
  }

  /**
   * This method simply uses {@link Flyway#clean()} to drop the whole database schema.
   */
  public void dropDatabase() {

    this.flyway.clean();
  }

  /**
   * This method sets the target for migration as specified by {@link #setBaselineVersion(MigrationVersion)}, and
   * executes the migration.
   */
  public void migrate() {

    if (this.migrationVersion != null) { // FIXME review if this can become null

      this.flyway.setTarget(this.migrationVersion);
    }
    this.flyway.migrate();
  }

  /**
   * This method baselines according to the version specified by {@link #setBaselineVersion(MigrationVersion)}.
   */
  public void baseline() {

    if (this.baselineVersion != null) {
      this.flyway.setBaselineVersion(this.baselineVersion);
    }
    this.flyway.baseline();
  }

  /**
   * @param flyway new value of internal {@code Flyway} member.
   */
  public void setFlyway(Flyway flyway) {

    this.flyway = flyway;
  }

  /**
   * @param migrationVersion new value of internal {@code migrationVersion}.
   */
  public void setMigrationVersion(MigrationVersion migrationVersion) {

    this.migrationVersion = migrationVersion;
  }

  /**
   * @param baselineVersion new value of {@code baselineVersion}
   */
  public void setBaselineVersion(MigrationVersion baselineVersion) {

    this.baselineVersion = baselineVersion;
  }

}
