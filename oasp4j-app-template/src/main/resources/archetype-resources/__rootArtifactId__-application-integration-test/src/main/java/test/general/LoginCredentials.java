#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.general;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class LoginCredentials {

  public static final String waiterUsername = "waiter";

  public static final String waiterPassword = "waiter";

  public static final Map<String, String> usernamePasswordMapping;
  static {
    usernamePasswordMapping = new HashMap<String, String>();
    usernamePasswordMapping.put(waiterUsername, waiterPassword);
  }

}
