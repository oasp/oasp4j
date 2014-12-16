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
  public static final String host = "127.0.0.1";

  /** The port of the server to test. */
  public static final int port = 8081;

  /** The URL of the server to test. */
  public static final String hostUrl = "http://" + host + ":" + port + "/oasp4j-sample-server";

  /** The URL for the login. */
  public static final String loginUrl = hostUrl + "/j_spring_security_login";

  /**
   * Login credentials
   *
   * @author hahmad, arklos
   */
  public static class LoginCredentials {

    /** Login of the default waiter. */
    public static final String waiterUsername = "waiter";

    /** Password of the default waiter. */
    public static final String waiterPassword = "waiter";

    /** Login of the default chief. */
    public static final String chiefUsername = "chief";

    /** Password of the default chief. */
    public static final String chiefPassword = "chief";

    /** Login of the default barkeeper. */
    public static final String barkeeperUsername = "barkeeper";

    /** Password of the default barkeeper. */
    public static final String barkeeperPassword = "barkeeper";

    /** Login of the default cook. */
    public static final String cookUsername = "cook";

    /** Password of the default cook. */
    public static final String cookPassword = "cook";

    /** {@link Map} from login to password. */
    public static final Map<String, String> usernamePasswordMapping;
    static {
      usernamePasswordMapping = new HashMap<>();
      usernamePasswordMapping.put(waiterUsername, waiterPassword);
      usernamePasswordMapping.put(chiefUsername, chiefPassword);
      usernamePasswordMapping.put(barkeeperUsername, barkeeperPassword);
      usernamePasswordMapping.put(cookUsername, cookPassword);
    }

  }
}
