package io.oasp.gastronomy.restaurant.test.general;

import io.oasp.gastronomy.restaurant.test.config.TestData;

import java.util.HashMap;
import java.util.Map;

/**
 * Application properties
 *
 * @author hahmad, arklos
 */
public class AppProperties {
  static {
    TestData.init();
  }

  /**
   *
   */
  public static final String host = "localhost";

  /**
   *
   */
  public static final int port = 8081;

  /**
   *
   */
  public static final String hostUrl = "http://" + host + ":" + port + "/oasp4j-sample-server";

  /**
   *
   */
  public static final String loginUrl = hostUrl + "/j_spring_security_login";

  /**
   * Login credentials
   *
   * @author hahmad, arklos
   */
  public static class LoginCredentials {

    /**
     *
     */
    public static final String waiterUsername = "waiter";

    /**
     *
     */
    public static final String waiterPassword = "waiter";

    /**
     *
     */
    public static final String chiefUsername = "chief";

    /**
     *
     */
    public static final String chiefPassword = "chief";

    /**
     *
     */
    public static final String barkeeperUsername = "barkeeper";

    /**
     *
     */
    public static final String barkeeperPassword = "barkeeper";

    /**
     *
     */
    public static final String cookUsername = "cook";

    /**
     *
     */
    public static final String cookPassword = "cook";

    /**
     *
     */
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
