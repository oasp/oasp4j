package io.oasp.gastronomy.restaurant.general.common;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * This class contains a method to aid simulating a REST client.
 *
 * @author jmolinar
 */
@Component
public class RestTestClientBuilder {

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
   * @param address The base URI of the REST service in {@code String} representation.
   * @param jacksonJsonProvider FIXME
   * @return An client object... FIXME
   */
  public <T> T build(Class<T> clazz, String userName, String password, String address,
      JacksonJsonProvider jacksonJsonProvider) {

    JAXRSClientFactoryBean factoryBean = new JAXRSClientFactoryBean();
    factoryBean.setAddress(address);
    factoryBean.setHeaders(new HashMap<String, String>());
    // example for basic auth
    String payload = userName + ":" + password;
    String authorizationHeader = "Basic " + Base64Utility.encode(payload.getBytes());
    factoryBean.getHeaders().put("Authorization", Arrays.asList(authorizationHeader));
    factoryBean.setProviders(Arrays.asList(jacksonJsonProvider));

    factoryBean.setServiceClass(clazz);
    return factoryBean.create(clazz);
  }

}
