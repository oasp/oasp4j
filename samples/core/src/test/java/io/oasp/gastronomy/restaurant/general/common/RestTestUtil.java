package io.oasp.gastronomy.restaurant.general.common;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * TODO jmolinar This type ...
 *
 * @author jmolinar
 * @since dev
 */
public class RestTestUtil {

  /**
   * @param <T> The type to be returned by the method
   * @param clazz The class type of the interface to be used for instantiation of the service.
   * @param address The address to be used for service instantiation
   * @param user The user used for login
   * @param password The password used for login
   * @param jacksonJsonProvider TODO remove if jacksonprovider is injected into RestTestUtil.java
   * @return A service of type {@code T}
   */
  public static <T> T createRestService(Class<T> clazz, String address, String user, String password,
      JacksonJsonProvider jacksonJsonProvider) {

    JAXRSClientFactoryBean factoryBean = new JAXRSClientFactoryBean();
    factoryBean.setAddress(address);
    factoryBean.setHeaders(new HashMap<String, String>());

    // example for basic auth
    String payload = user + ":" + password;
    String authorizationHeader = "Basic " + Base64Utility.encode(payload.getBytes());
    factoryBean.getHeaders().put("Authorization", Arrays.asList(authorizationHeader));
    factoryBean.setProviders(Arrays.asList(jacksonJsonProvider));
    factoryBean.setServiceClass(clazz);
    return factoryBean.create(clazz);
  }
}
