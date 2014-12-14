package io.oasp;

import io.oasp.gastronomy.restaurant.test.general.TestData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Abstract class that rolls back the Database after each test
 *
 * @author arklos
 */
public class AbstractDBRollbackTest {

  /**
   * Connection to the Database
   */
  protected static Connection conn;

  /**
   * The constructor.
   */
  public AbstractDBRollbackTest() {

    TestData.init();
    TestData.DB.exportData("testdataexport.sql");
  }

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void setUp() throws ClassNotFoundException, SQLException {

    Class.forName("org.h2.Driver");
    Class.forName(TestData.class.getName());
    conn =
        DriverManager.getConnection(
            "jdbc:h2:tcp://localhost:8043/restaurant-db;INIT=create schema if not exists public", "sa", "");
  }

  @SuppressWarnings("javadoc")
  @Before
  public void reinit() throws SQLException, FileNotFoundException {

    RunScript.execute(conn, new FileReader("testdataexport.sql"));
  }

  @SuppressWarnings("javadoc")
  @AfterClass
  public static void close() throws SQLException {

    conn.close();
  }
}
