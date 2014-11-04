#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.general.common.api.to;

import javax.ws.rs.core.Response;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class AbstractEtoWrapper {

  private Response response;

  private String jsonSting;

  public AbstractEtoWrapper(Response response, String jsonSting) {

    this.response = response;
    this.jsonSting = jsonSting;
  }

  /**
   * @return response
   */
  public Response getResponse() {

    return this.response;
  }

  /**
   * @param response the response to set
   */
  public void setResponse(Response response) {

    this.response = response;
  }

  /**
   * @return jsonSting
   */
  public String getJsonSting() {

    return this.jsonSting;
  }

  /**
   * @param jsonSting the jsonSting to set
   */
  public void setJsonSting(String jsonSting) {

    this.jsonSting = jsonSting;
  }

}
