#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.service.impl.ws;

import ${package}.tablemanagement.service.api.ws.HelloWorldWebService;

import javax.inject.Named;
import javax.jws.WebService;

/**
 * Implementation of {@link HelloWorldWebService}.
 * 
 * @author hohwille
 */
@Named("HelloWorldWebService")
@WebService(endpointInterface = "${package}.tablemanagement.service.api.ws.HelloWorldWebService")
public class HelloWorldWebServiceImpl implements HelloWorldWebService {

  /**
   * {@inheritDoc}
   */
  @Override
  public String sayHi(String name) {

    return "Hello " + name;
  }

}
