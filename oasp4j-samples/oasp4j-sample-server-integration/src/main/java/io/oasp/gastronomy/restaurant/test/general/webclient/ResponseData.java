package io.oasp.gastronomy.restaurant.test.general.webclient;

import javax.ws.rs.core.Response;

/**
 *
 * This Contrainer class holds data from the serverresponse
 *
 * @author hahmad, arklos
 * @param <T> A tranfered Object
 */
public class ResponseData<T> {

  private T responseObject;

  private Response response;

  private String jsonString;

  /**
   * The constructor.
   *
   * @param responseObject Object that has been transfered from the server
   * @param response Server response
   * @param jsonString Json response
   */
  public ResponseData(T responseObject, Response response, String jsonString) {

    this.responseObject = responseObject;
    this.response = response;
    this.jsonString = jsonString;
  }

  /**
   * @param transferObject the transferObject to set
   */
  public void setResponseObject(T transferObject) {

    this.responseObject = transferObject;
  }

  /**
   * @return transferObject
   */
  public T getResponseObject() {

    return this.responseObject;
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
  public String getJsonString() {

    return this.jsonString;
  }

  /**
   * @param jsonSting the jsonSting to set
   */
  public void setJsonString(String jsonSting) {

    this.jsonString = jsonSting;
  }

}
