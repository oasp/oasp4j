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
 */
public class RestTestClientBuilder {

  /**
   * The port to which the server is bound during testing.
   */
  private int localServerPort;

  private JacksonJsonProvider jacksonJsonProvider;

  /*
   * The user used for authentication during testing.
   */
  private String user;

  /*
   * The password used for authentication during testing.
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
   * @param tmpPassword The password for basic authentication.
   * @param tmpUrl The URL through which the server is reached.
   * @return A REST proxy of type {@code T}
   */
  public <T extends RestService> T build(Class<T> clazz, String userName, String tmpPassword, String tmpUrl) {

    JAXRSClientFactoryBean factoryBean = new JAXRSClientFactoryBean();
    factoryBean.setAddress(tmpUrl);
    factoryBean.setHeaders(new HashMap<String, String>());
    // example for basic auth
    String payload = userName + ":" + tmpPassword;
    String authorizationHeader = "Basic " + Base64Utility.encode(payload.getBytes());
    factoryBean.getHeaders().put("Authorization", Arrays.asList(authorizationHeader));
    factoryBean.setProviders(Arrays.asList(this.jacksonJsonProvider));

    factoryBean.setServiceClass(clazz);
    return factoryBean.create(clazz);
  }

  /*
   * @return the URL of the REST service.
   */
  private String createRestServiceUrl() {

    return "http://localhost:" + this.localServerPort + "/services/rest";
  }

  /**
   * Sets the {@code jacksonJsonProvider}.
   *
   * @param jacksonJsonProvider An instance of {@link JacksonJsonProvider}
   */
  public void setJacksonJsonProvider(JacksonJsonProvider jacksonJsonProvider) {

    this.jacksonJsonProvider = jacksonJsonProvider;
  }

  /**
   * Sets the {@code localServerPort}.
   *
   * @param localServerPort The port through which the server is available during testing
   */
  public void setLocalServerPort(int localServerPort) {

    this.localServerPort = localServerPort;
  }

  /**
   * Sets the {@code user}.
   *
   * @param user Used for authentication.
   */
  public void setUser(String user) {

    this.user = user;
  }

  /**
   * Sets the {@code password}.
   *
   * @param password Used for authentication.
   */
  public void setPassword(String password) {

    this.password = password;
  }
}
