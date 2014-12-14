package io.oasp.gastronomy.restaurant.general.dataaccess.base;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

/**
 * Type to migrate the database. Gets initialized before hibernate.
 *
 * @author jmetzler
 */
public class DatabaseMigrator {

  private boolean enabled;

  private JdbcDataSource dataSource;

  /**
   * Migrate the database to the latest available migration.
   */
  public void migrate() {

    if (this.enabled) {
      final Flyway flyway = new Flyway();
      flyway.setDataSource(this.dataSource);
      flyway.clean();
      flyway.migrate();
    }
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
  public JdbcDataSource getDataSource() {

    return this.dataSource;
  }

  /**
   * @param datasource the datasource to set
   */
  public void setDataSource(JdbcDataSource datasource) {

    this.dataSource = datasource;
  }

}
