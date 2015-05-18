package io.oasp.module.jpa.dataaccess.base;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * This class contains all configuration needed to prepare jdbc cursor and to read data from it.
 *
 * @author jczas
 * @param <T> data read from cursor will be mapped to
 */
public class JdbcCursorConfiguration<T> {

  PreparedStatementSetter preparedStatementSetter;

  String sql;

  RowMapper<T> rowMapper;

  /**
   * Set the RowMapper to be used for all calls to read().
   *
   * @param rowMapper row mapper
   */
  public void setRowMapper(RowMapper<T> rowMapper) {

    this.rowMapper = rowMapper;
  }

  /**
   * Set the SQL statement to be used when creating the cursor. This statement should be a complete and valid SQL
   * statement, as it will be run directly without any modification.
   *
   * @param sql SQL statement
   */
  public void setSql(String sql) {

    this.sql = sql;
  }

  /**
   * Set the PreparedStatementSetter to use if any parameter values that need to be set in the supplied query.
   *
   * @param preparedStatementSetter prepared statement setter
   */
  public void setPreparedStatementSetter(PreparedStatementSetter preparedStatementSetter) {

    this.preparedStatementSetter = preparedStatementSetter;
  }

  /**
   * @see #setPreparedStatementSetter(PreparedStatementSetter)
   * @return the {@link PreparedStatementSetter} instance.
   */
  public PreparedStatementSetter getPreparedStatementSetter() {

    return this.preparedStatementSetter;
  }

  /**
   * @see #setRowMapper(RowMapper)
   * @return the {@link RowMapper} instance.
   */
  public RowMapper<T> getRowMapper() {

    return this.rowMapper;
  }

  /**
   * @see #setSql(String)
   * @return the SQL statement to be used when creating the cursor.
   */
  public String getSql() {

    return this.sql;
  }
}
