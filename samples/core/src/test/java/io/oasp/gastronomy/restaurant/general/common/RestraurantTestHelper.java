package io.oasp.gastronomy.restaurant.general.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;

/**
 * This class serves as a helper class for configuring test fixtures using {@link Flyway} for database migrations of H2
 * database.
 *
 * @author jmolinar
 */

public class RestraurantTestHelper {

  private Flyway flyway;

  private DataSource dataSource;

  private MigrationVersion migrationVersion;

  private MigrationVersion baselineVersion;

  /**
   * The constructor.
   */
  public RestraurantTestHelper() {
    super();
  }

  /**
   * The constructor.
   *
   * @param flyway an instance of type {@link Flyway}
   * @param dataSource an instance of type {@link DataSource}
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
   * This is an alternative implementation of {@link #dropAllH2Tables()}.
   */
  public void dropTablesAlternative() {

    TxTask<Void> task = new DropTablesTask(true);
    doInTx(task);
  }

  /**
   * @param <T> The generic type to be returned.
   * @param task an instance of type {@link TxTask<T>}
   * @return If {@code <T>} equals {@link Void}, then return nothing. Else return {@code <T>}
   */
  private <T> T doInTx(TxTask<T> task) {

    try (Connection connection = this.dataSource.getConnection()) {
      try {
        T result = task.invoke(connection);
        connection.commit();
        return result;
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (Exception e) {
      throw new IllegalStateException();
    }
  }

  /*
   * This interface serves as a strategy pattern to simulate functional programming capabilities.
   */
  private interface TxTask<T> {
    /**
     * This method servers as the basis for the strategy pattern.
     *
     * @param connection an instance of {@link DataSource}
     * @return The generic type {@code T} as specified.
     * @throws SQLException May throw an {@link SQLException}.
     */
    T invoke(Connection connection) throws SQLException;
  }

  /*
   * This class serves as an implementation of the strategy pattern. Its task is to drop all tables in the database, or
   * to simply delete all rows of all tables.
   */
  private class DropTablesTask implements TxTask<Void> {
    /*
     * Default value: true
     */
    private boolean drop = true;

    /**
     * The constructor.
     *
     * @param drop if {@code true}, drops all database tables. Else delete all rows from all tables. Default is
     *        {@code true}.
     */
    public DropTablesTask(boolean drop) {
      super();
      this.drop = drop;
    }

    @Override
    public Void invoke(Connection connection) throws SQLException {

      Statement statement = connection.createStatement();
      statement.execute("SET REFERENTIAL_INTEGRITY FALSE");
      statement.execute("SHOW TABLES");
      List<String> tables = new ArrayList<>();
      ResultSet resultSet = statement.getResultSet();
      while (resultSet.next()) {
        tables.add(resultSet.getString(1));
      }
      for (String table : tables) {
        String sql;
        if (this.drop) {
          sql = "DROP TABLE \"" + table + "\"";
        } else {
          sql = "TRUNCATE TABLE \"" + table + "\"";
        }
        statement.execute(sql);
      }
      statement.execute("SET REFERENTIAL_INTEGRITY TRUE");
      return null;
    }

  }

  /*
   * This class servers as an implementation of the strategy pattern. Its task is to drop all objects in the database.
   */
  private class DropObjectsTask implements TxTask<Void> {
    @Override
    public Void invoke(Connection connection) throws SQLException {

      Statement statement = connection.createStatement();
      statement.execute("DROP ALL OBJECTS");
      return null;
    }

  }

  /**
   * This method simply uses {@link Flyway#clean()} to drop the whole database schema.
   */
  public void dropH2() {

    this.flyway.clean();
  }

  /**
   * This is an alternative implementation of {@link #dropH2()}.
   */
  public void dropH2Alternative() {

    TxTask<Void> task = new DropObjectsTask();
    doInTx(task);
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
   * @param flyway new value of {@link #flyway}.
   */
  public void setFlyway(Flyway flyway) {

    this.flyway = flyway;
  }

  /**
   * @param dataSource new value of {@link #dataSource}.
   */
  public void setDataSource(DataSource dataSource) {

    this.dataSource = dataSource;
  }

  /**
   * @param migrationVersion new value of {@link #migrationVersion}.
   */
  public void setMigrationVersion(MigrationVersion migrationVersion) {

    this.migrationVersion = migrationVersion;
  }

  /**
   * @param baselineVersion new value of {@link #baselineVersion}.
   */
  public void setBaselineVersion(MigrationVersion baselineVersion) {

    this.baselineVersion = baselineVersion;
  }

}
