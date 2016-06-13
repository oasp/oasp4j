package io.oasp.gastronomy.restaurant.general.common;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;

/**
 * TODO jmolinar This type ...
 *
 * @author jmolinar
 */

public class RestraurantTestHelper {

  private Flyway flyway;

  private DataSource dataSource;

  private MigrationVersion migrationVersion;

  private MigrationVersion baselineVersion;

  private MigrationVersion emptyDatabaseVersion;

  /**
   * The constructor.
   */
  public RestraurantTestHelper() {
    super();
  }

  /**
   * The constructor.
   *
   * @param flyway
   * @param dataSource
   */
  public RestraurantTestHelper(Flyway flyway, DataSource dataSource) {
    super();
    this.flyway = flyway;
    this.dataSource = dataSource;
  }

  public void login(String name) {

    // TODO implement in later iteration
  }

  public void logout() {

    // TODO implement in later iteration
  }

  /**
   * At first, this method cleans H2 database using {@link Flyway}. Next, it calls {@link #baseline()} to restore.
   *
   */
  public void dropAllH2Tables() {

    dropH2();
    baseline();
  }

  /**
   * This method simply uses {@link Flyway#clean()} to drop the whole database schema.
   */
  public void dropH2() {

    this.flyway.clean();
  }

  /**
   * This method uses {@link #dropH2()}, and then calls {@link #migrate()}.
   */
  public void resetH2() {

    dropH2();
    migrate();
  }

  /**
   * This method sets the target for migration as specified by {@link #setBaselineVersion(MigrationVersion)}, and
   * executes the migration.
   */
  public void migrate() {

    this.flyway.setTarget(this.migrationVersion);
    this.flyway.migrate();
  }

  /**
   * This method baselines according to the version specified by {@link #setBaselineVersion(MigrationVersion)}.
   */
  public void baseline() {

    this.flyway.setBaselineVersion(this.baselineVersion);
    this.flyway.baseline();
  }

  /**
   * @param flyway new value of {@link #getflyway}.
   */
  public void setFlyway(Flyway flyway) {

    this.flyway = flyway;
  }

  /**
   * @param dataSource new value of {@link #getdataSource}.
   */
  public void setDataSource(DataSource dataSource) {

    this.dataSource = dataSource;
  }

  /**
   * @param migrationVersion new value of {@link #getmigrationVersion}.
   */
  public void setMigrationVersion(MigrationVersion migrationVersion) {

    this.migrationVersion = migrationVersion;
  }

  /**
   * @param baselineVersion new value of {@link #getbaselineVersion}.
   */
  public void setBaselineVersion(MigrationVersion baselineVersion) {

    this.baselineVersion = baselineVersion;
  }

  /**
   * @param emptyDatabaseVersion new value of {@link #getemptyDatabaseVersion}.
   */
  public void setEmptyDatabaseVersion(MigrationVersion emptyDatabaseVersion) {

    this.emptyDatabaseVersion = emptyDatabaseVersion;
  }

}
