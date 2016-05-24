package io.oasp.gastronomy.restaurant.general.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;

/**
 * TODO jmolinar This type ...
 *
 * @author jmolinar
 */
@Configuration
public class RestaurantTestConfig {

  /**
   * The constructor.
   */
  public RestaurantTestConfig() {
    super();
  }

  /**
   * @return an instance of type {@code RestTestClientBuilder}
   */
  @Bean
  public RestTestClientBuilder restTestClientBuilder() {

    return new RestTestClientBuilder();
  }

}
