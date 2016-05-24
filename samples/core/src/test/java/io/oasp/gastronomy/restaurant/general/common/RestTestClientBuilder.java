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

  private int localServerPort;

  private JacksonJsonProvider jacksonJsonProvider;

  /**
   * This method returns an instance of an implementation the specified {@code clazz} (which should be a REST service
   * interface, e.g. TablemanagementRestService.class). <br/>
   * The caller must take care that no parameter value is equal to {@code NULL}. <br/>
   * The caller will be authenticated using basic authentication with the provided credentials.
   *
   * @param <T> The return type.
   * @param clazz This must be an interface type.
   * @param userName The userName for basic authentication.
   * @param password The password for basic authentication.
   * @return An client object... FIXME
   */
  public <T extends RestService> T build(Class<T> clazz, String userName, String password) {

    JAXRSClientFactoryBean factoryBean = new JAXRSClientFactoryBean();
    factoryBean.setAddress(createRestServiceUrl());
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
   * @param jacksonJsonProvider new value of {@link #getjacksonJsonProvider}.
   */
  public void setJacksonJsonProvider(JacksonJsonProvider jacksonJsonProvider) {

    this.jacksonJsonProvider = jacksonJsonProvider;
  }

  /**
   * @param localServerPort new value of {@link #getlocalServerPort}.
   */
  public void setLocalServerPort(int localServerPort) {

    this.localServerPort = localServerPort;
  }
}
