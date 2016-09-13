package io.oasp.gastronomy.restaurant.general.dataaccess.base;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

/**
 * Type to migrate the database. Gets initialized before hibernate.
 *
 */
public class DatabaseMigrator {

  /** Path of test data location. */
  private static final String testDataPath = "classpath:db/test-data";

  /** Path of master data location. */
  private static final String masterDataPath = "classpath:db/migration";

  /** Property is true if database migration is enabled. */
  private boolean enabled;

  /** The JDBC data source. */
  private DataSource dataSource;

  /** Property is true if test data should be included in migration. */
  private boolean testdata;

  /** Property is true if database should be cleaned before migration. */
  private boolean clean;

  /**
   * Migrate the database to the latest available migration.
   */
  public void migrate() {

    if (this.enabled) {
      final Flyway flyway = new Flyway();
      flyway.setDataSource(this.dataSource);
      if (this.testdata) {
        flyway.setLocations(masterDataPath, testDataPath);
      } else {
        flyway.setLocations(masterDataPath);
      }
      if (this.clean) {
        flyway.clean();
      }
      flyway.migrate();
    }
  }

  /**
   * Import test data.
   *
   * @param importTestDataPath path to directory with files with test data
   */
  public void importTestData(String importTestDataPath) {

    final Flyway flyway = new Flyway();
    flyway.setDataSource(this.dataSource);
    flyway.setLocations(importTestDataPath);
    flyway.migrate();
  }

  /**
   * @return enabled
   */
  public boolean isEnabled() {

    return this.enabled;
  }

  /**
   * @param enabled the enabled to set
   */
  public void setEnabled(boolean enabled) {

    this.enabled = enabled;
  }

  /**
   * @return datasource
   */
  public DataSource getDataSource() {

    return this.dataSource;
  }

  /**
   * @param datasource the datasource to set
   */
  public void setDataSource(DataSource datasource) {

    this.dataSource = datasource;
  }

  /**
   * @param testdata the testdata to set
   */
  public void setTestdata(boolean testdata) {

    this.testdata = testdata;
  }

  /**
   * @param clean the clean to set
   */
  public void setClean(boolean clean) {

    this.clean = clean;
  }

}
