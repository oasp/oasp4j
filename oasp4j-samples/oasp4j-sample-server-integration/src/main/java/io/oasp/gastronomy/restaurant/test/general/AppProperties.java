package io.oasp.gastronomy.restaurant.test.general;

import java.util.HashMap;
import java.util.Map;

/**
 * Application properties
 *
 * @author hahmad, arklos
 */
public class AppProperties {

  /** The host of the server to test. */
  public static final String SERVER_HOST = "127.0.0.1";

  /** The port of the server to test. */
  public static final int SERVER_PORT = 8081;

  /** The URL of the server to test. */
  public static final String SERVER_URL = "http://" + SERVER_HOST + ":" + SERVER_PORT + "/oasp4j-sample-server";

  /** The URL for the login. */
  public static final String SERVER_URL_LOGIN = SERVER_URL + "/j_spring_security_login";

  /**
   * Login credentials
   *
   * @author hahmad, arklos
   */
  public static class LoginCredentials {

    /** Login of the default waiter. */
    public static final String WAITER_USERNAME = "waiter";

    /** Password of the default waiter. */
    public static final String WAITER_PASSWORD = "waiter";

    /** Login of the default chief. */
    public static final String CHIEF_USERNAME = "chief";

    /** Password of the default chief. */
    public static final String CHIEF_PASSWORD = "chief";

    /** Login of the default barkeeper. */
    public static final String BARKEEPER_USERNAME = "barkeeper";

    /** Password of the default barkeeper. */
    public static final String BARKEEPER_PASSWORD = "barkeeper";

    /** Login of the default cook. */
    public static final String COOK_USERNAME = "cook";

    /** Password of the default cook. */
    public static final String COOK_PASSWORD = "cook";

    /** {@link Map} from login to password. */
    public static final Map<String, String> USERNAME2PASSWORD_MAP;
    static {
      USERNAME2PASSWORD_MAP = new HashMap<>();
      USERNAME2PASSWORD_MAP.put(WAITER_USERNAME, WAITER_PASSWORD);
      USERNAME2PASSWORD_MAP.put(CHIEF_USERNAME, CHIEF_PASSWORD);
      USERNAME2PASSWORD_MAP.put(BARKEEPER_USERNAME, BARKEEPER_PASSWORD);
      USERNAME2PASSWORD_MAP.put(COOK_USERNAME, COOK_PASSWORD);
    }

  }
}
