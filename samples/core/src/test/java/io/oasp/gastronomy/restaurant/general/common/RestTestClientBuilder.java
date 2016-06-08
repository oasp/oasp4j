package io.oasp.gastronomy.restaurant.general.common;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.general.common.api.RestService;

/**
 * This class contains a method to aid simulating a REST client.
 *
 * @author jmolinar
 */
public class RestTestClientBuilder {

  /**
   * The port to which the server is bound during testing.
   */
  private int localServerPort;

  /**
   * The {@code JacksonJsonProvider}
   */
  private JacksonJsonProvider jacksonJsonProvider;

  /**
   * The {@code user} used for authentication.
   */
  private String user;

  /**
   * The {@code password} used for authentication.
   */
  private String password;

  /**
   * This method creates a proxy for the specified {@code RestService} interface. Properties
   * {@code server.rest.test.user} and {@code server.rest.test.password} are used by default for authentication.
   *
   * @param <T> The generic type for which a proxy must be created.
   * @param clazz The interface specifying the generic type.
   * @return a proxy of the specified type.
   */
  public <T extends RestService> T build(Class<T> clazz) {

    return this.build(clazz, this.user, this.password, createRestServiceUrl());
  }

  /**
   * This method creates a proxy for the specified {@code RestService} interface. The provided {@code String login} is
   * used as both username and password for authentication. The method {@code setLocalServerPort} MUST be called in
   * advance. The method {@code setLocalServerPort} MUST be called in advance.
   *
   * @param <T> The generic type for which a proxy must be created.
   * @param clazz The interface specifying the generic type.
   * @param login the {@code String} used as for authentcation.
   * @return a proxy of the specified type.
   */
  public <T extends RestService> T build(Class<T> clazz, String login) {

    return this.build(clazz, login, login, createRestServiceUrl());
  }

  /**
   * This method returns an instance of an implementation the specified {@code clazz} (which should be a REST service
   * interface, e.g. TablemanagementRestService.class). <br/>
   * The caller must take care that no parameter value is equal to {@code NULL}. <br/>
   * The caller will be authenticated using basic authentication with the provided credentials. The method
   * {@code setLocalServerPort} MUST be called in advance.
   *
   * @param <T> The return type.
   * @param clazz This must be an interface type.
   * @param userName The userName for basic authentication.
   * @param password The password for basic authentication.
   * @return An client object... FIXME
   */
  public <T extends RestService> T build(Class<T> clazz, String userName, String password, String url) {

    JAXRSClientFactoryBean factoryBean = new JAXRSClientFactoryBean();
    factoryBean.setAddress(url);
    factoryBean.setHeaders(new HashMap<String, String>());
    // example for basic auth
    String payload = userName + ":" + password;
    String authorizationHeader = "Basic " + Base64Utility.encode(payload.getBytes());
    factoryBean.getHeaders().put("Authorization", Arrays.asList(authorizationHeader));
    factoryBean.setProviders(Arrays.asList(this.jacksonJsonProvider));

    factoryBean.setServiceClass(clazz);
    return factoryBean.create(clazz);
  }

  /**
   * @return the URL of the REST service.
   */
  private String createRestServiceUrl() {

    return "http://localhost:" + this.localServerPort + "/services/rest";
  }

  /**
   * @param jacksonJsonProvider new value of {@link #jacksonJsonProvider}.
   */
  public void setJacksonJsonProvider(JacksonJsonProvider jacksonJsonProvider) {

    this.jacksonJsonProvider = jacksonJsonProvider;
  }

  /**
   * @param localServerPort new value of {@link #localServerPort}.
   */
  public void setLocalServerPort(int localServerPort) {

    this.localServerPort = localServerPort;
  }

  /**
   * @param user new value of {@link user}.
   */
  public void setUser(String user) {

    this.user = user;
  }

  /**
   * @param password new value of {@code password}.
   */
  public void setPassword(String password) {

    this.password = password;
  }
}
