package io.oasp.gastronomy.restaurant.test.general.webclient;

import io.oasp.gastronomy.restaurant.general.service.impl.rest.ApplicationObjectMapperFactory;
import io.oasp.gastronomy.restaurant.test.general.AppProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.http.NameValuePair;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 *
 * This client establishes a connection to the server with the specified user. It provides various methods to interact
 * with the server.
 *
 * @author hahmad, arklos
 */
public class WebClientWrapper {

  private WebClient webClient;

  private String username;

  private String password;

  /**
   * The constructor.
   *
   * @param username The username that is used for a login
   * @param password The password that is used for a login
   */
  public WebClientWrapper(String username, String password) {

    this.username = username;
    this.password = password;
    initWebClient();
    setCsrfHeader();

  }

  /**
   * @return Returns the wrapped webclient
   */
  public WebClient getWebClient() {

    return this.webClient;
  }

  private void initWebClient() {

    List<Object> providers = new ArrayList<>();

    JacksonJaxbJsonProvider j = new JacksonJaxbJsonProvider();
    j.setMapper(new ApplicationObjectMapperFactory().createInstance());
    providers.add(j);

    WebClient client = WebClient.create(AppProperties.hostUrl, providers);

    WebClient.getConfig(client).getRequestContext().put(org.apache.cxf.message.Message.MAINTAIN_SESSION, Boolean.TRUE);

    // set http accept header and type header
    client = client.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);

    client =
        client.path("/j_spring_security_login").query("j_username", this.username).query("j_password", this.password);
    Response res = client.post("");

    // TODO Handle when login fails
    int status2 = res.getStatus();
    System.out.println("Http status " + status2);

    this.webClient = client;
  }

  /**
   * @return Returns the username
   */
  public String getUsername() {

    return this.username;
  }

  /**
   * Sets the csrf header
   */
  public void setCsrfHeader() {

    String myToken;
    this.webClient = this.webClient.replacePath("/services/rest/security/csrftoken/");

    // Response response = this.webClient.get();

    String res = this.webClient.get(String.class);
    String[] ar = res.replace("{", "").replace("}", "").replace("\"", "").split(",");

    myToken = ar[0].split(":")[1];

    this.webClient = this.webClient.header("X-CSRF-TOKEN", myToken);

  }

  /**
   * Gets the data from an Url and returns a list of ResponseData Objects.
   *
   * @param <T> Type of the Object that will be transfered from the server
   * @param url Url
   * @param responseObjectClass Class of the ResponseObject
   * @return A list of ServerResonseData Objects
   */
  public <T> List<ResponseData<T>> getAll(String url, Class<T> responseObjectClass) {

    return getAll(url, null, responseObjectClass);
  }

  /**
   * Gets the data from an Url and returns a list of ResponseData Objects.
   *
   * @param <T> Type of the Object that will be transfered from the server
   * @param url Url
   * @param params List of parameters that will be added to the query
   * @param responseObjectClass Class of the ResponseObject
   * @return A list of ServerResonseData Objects
   */
  public <T> List<ResponseData<T>> getAll(String url, List<NameValuePair> params, Class<T> responseObjectClass) {

    List<ResponseData<T>> resultList = new ArrayList<>();
    this.webClient.replacePath(url);
    if (params != null) {
      for (NameValuePair param : params) {
        this.webClient.query(param.getName(), param.getValue());
      }
    }

    @SuppressWarnings("unchecked")
    Collection<T> getResult = (Collection<T>) this.webClient.getCollection(responseObjectClass);
    Response response = this.webClient.get();
    String jsonString = this.webClient.get(String.class);
    for (T e : getResult) {
      resultList.add(new ResponseData<>(e, response, jsonString));
    }
    return resultList;
  }

  /**
   *
   * @param Url Url with the specified item to delte
   * @return Server response
   */
  public Response delete(String Url) {

    this.webClient.replacePath(Url);
    return this.webClient.delete();
  }

  /**
   * @param transferObject The Object that will be sent to the server
   * @param url Url
   * @return Server response
   */
  public Response put(String url, Object transferObject) {

    this.webClient.replacePath(url);
    return this.webClient.put(transferObject);
  }

  /**
   * @param <T> A Type of the Object that will be transfered from the server
   * @param url Url
   * @param transferObjectClass Class of the TransferObject
   * @return Server response
   */
  public <T> ResponseData<T> get(String url, Class<T> transferObjectClass) {

    this.webClient.replacePath(url);
    Response response = this.webClient.get();
    String jsonString = this.webClient.get(String.class);
    T transferObject = this.webClient.get(transferObjectClass);
    return new ResponseData<>(transferObject, response, jsonString);

  }

  /**
   * @param url Url
   * @return Server response
   */
  public Response get(String url) {

    this.webClient.replacePath(url);
    return this.webClient.get();

  }

  /**
   * @param <T> Type of the Object that will be transfered from the server
   * @param url Url
   * @param transferObject Object to put
   * @param responseObjectClass Class of object
   * @return Server response
   */
  public <T> ResponseData<T> put(String url, Object transferObject, Class<T> responseObjectClass) {

    this.webClient.replacePath(url);
    T responseObject = this.webClient.put(transferObject, responseObjectClass);
    Response response = this.webClient.put(null);
    String jsonString = this.webClient.put(null, String.class);
    return new ResponseData<>(responseObject, response, jsonString);

  }

  /**
   * @param url Url
   * @return Server response
   */
  public Response post(String url) {

    return post(url, (Object) null);
  }

  /**
   * @param url Url
   * @param transferObject Object that will be transfered
   * @return Server response
   */
  public Response post(String url, Object transferObject) {

    this.webClient.replacePath(url);
    return this.webClient.post(transferObject);
  }

  /**
   * @param <T> Type of the Object that will be transfered from the server
   * @param url Url
   * @param responseObjectClass Class of the return type
   * @return Server response
   */
  public <T> ResponseData<T> post(String url, Class<T> responseObjectClass) {

    this.webClient.replacePath(url);
    Response response = this.webClient.post(null);
    String jsonString = this.webClient.post(null, String.class);
    T responseObject = this.webClient.post(null, responseObjectClass);
    return new ResponseData<>(responseObject, response, jsonString);
  }

  /**
   * @param <T> Type for response
   * @param url Url
   * @param transferObject Object to put
   * @param responseObjectClass Class of object
   * @return Server response
   */
  public <T> ResponseData<T> post(String url, Object transferObject, Class<T> responseObjectClass) {

    this.webClient.replacePath(url);
    T responseObject = this.webClient.post(transferObject, responseObjectClass);
    Response response = this.webClient.post(null);
    String jsonString = this.webClient.post(null, String.class);
    return new ResponseData<>(responseObject, response, jsonString);
  }

  public <T> T post(Object body, String url, Class<T> responseObjectClass) {

    this.webClient.replacePath(url);
    T responseObject = this.webClient.post(body, responseObjectClass);
    return responseObject;
  }

}
