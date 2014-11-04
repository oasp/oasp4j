#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.service.api.ws;

import ${package}.tablemanagement.service.api.HelloWorldService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * The JAX-WS interface for {@link HelloWorldService}.
 *
 * @author hohwille
 */
@WebService
public interface HelloWorldWebService extends HelloWorldService {

  /**
   * {@inheritDoc}
   */
  @Override
  @WebMethod
  @WebResult(name = "message")
  String sayHi(@WebParam(name = "name") String name);

}
