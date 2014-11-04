#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.general.webclient;

import ${package}.test.general.LoginCredentials;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class WebClientPool {

  private static Map<String, WebClientWrapper> clientPool = new HashMap<String, WebClientWrapper>();

  private WebClientPool() {

  }

  public static WebClientWrapper getWebClientWrapper(String username) {

    WebClientWrapper webClientWrapper = clientPool.get(username);
    if (webClientWrapper == null) {
      WebClientWrapper client = new WebClientWrapper(username, LoginCredentials.usernamePasswordMapping.get(username));
      clientPool.put(username, client);
      webClientWrapper = client;
    }
    return webClientWrapper;

  }

}
