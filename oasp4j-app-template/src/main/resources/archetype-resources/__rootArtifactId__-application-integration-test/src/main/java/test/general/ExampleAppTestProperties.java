#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.general;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class ExampleAppTestProperties {

  public static final String host = "localhost";

  public static final int port = 8081;

  public static final String hostUrl = "http://" + host + ":" + port + "/${artifactId}-application";

  public static final String loginUrl = hostUrl + "/j_spring_security_login";

}
