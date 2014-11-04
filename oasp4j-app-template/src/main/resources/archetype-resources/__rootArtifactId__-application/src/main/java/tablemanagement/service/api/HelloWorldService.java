#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.service.api;

/**
 * This is the interface of a stupid PoC example. It will be removed later if reasonable services are in place.
 *
 * @author hohwille
 */
public interface HelloWorldService {

  /**
   * @param name the name to say hi to.
   * @return the response message ("Hello " + name).
   */
  String sayHi(String name);

}
