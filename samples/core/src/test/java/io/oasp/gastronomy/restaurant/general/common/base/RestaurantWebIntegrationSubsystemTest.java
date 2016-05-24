package io.oasp.gastronomy.restaurant.general.common.base;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.configuration.RestaurantTestConfig;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * Abstract base class for {@link SubsystemTest}s which runs the tests within a local server. <br/>
 * <br/>
 * The local server's port is randomly assigned.
 *
 * @author jmolinar
 */
@SpringApplicationConfiguration(classes = RestaurantTestConfig.class)
@WebIntegrationTest("server.port:0")
public class RestaurantWebIntegrationSubsystemTest {

  @Value("${test.user:waiter}")
  private String userName = "waiter";

  @Value("${test.user:waiter}")
  private String password = "waiter";

  /**
   * The port of the web server during the test.
   */
  @Value("${local.server.port}")
  protected int port;

  /**
   * The name of the application during the test.
   */
  @Value("${spring.application.name:restaurant}")
  protected String appName;

  /**
   * An instance of type {@code RestTestClientBuilder}.
   */
  @Inject
  private RestTestClientBuilder restTestClientBuilder;

  /**
   * An instance of type {@code JacksonJsonProvider}
   */
  @Inject
  protected JacksonJsonProvider jacksonJsonProvider;

  /**
   * @return the URL of the REST service.
   */
  public String createRestServiceUrl() {

    return "http://localhost:" + this.port + "/services/rest";
  }

  /**
   * @return restTestClientBuilder
   */
  public RestTestClientBuilder getRestTestClientBuilder() {

    return this.restTestClientBuilder;
  }

  public <T> T build(Class<T> clazz) {

    return getRestTestClientBuilder().build(clazz, this.userName, this.password, createRestServiceUrl(),
        this.jacksonJsonProvider);
  }

}
