#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This is a utility for testing. It allows to simulate authentication for component testing.
 *
 */
public class TestUtil {

  /**
   * @param login the id of the user to run the test as.
   * @param permissions the permissions for the test.
   */
  public static void login(String login, String... permissions) {

    Authentication authentication = new TestingAuthenticationToken(login, login, permissions);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  /**
   * Logs off any {@link ${symbol_pound}login(String, String...) previously logged on user}.
   */
  public static void logout() {

    SecurityContextHolder.getContext().setAuthentication(null);
  }

}
