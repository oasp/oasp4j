package io.oasp.gastronomy.restaurant.general.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.RestaurantTestHelper;
import io.oasp.gastronomy.restaurant.general.common.base.RestaurantWebIntegrationSubsystemTest;
import io.oasp.module.basic.configuration.SpringProfileConstants;

/**
 * This configuration class provides {@code @Bean} annotated methods. It is applied to a test class by using the
 * following class annotation: {@code @SpringApplicationConfiguration(classes = RestaurantTestConfig.class)}. Hence,
 * beans provided by {@code @Bean} annotated methods will not be available outside the test configuration. <br/>
 * <br/>
 * See {@link RestaurantWebIntegrationSubsystemTest} as an example.
 *
 * @author jmolinar
 */
@Configuration
@Profile(SpringProfileConstants.JUNIT)
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

  /**
   * @return an instance of type {@code RestTestHelper}
   */
  @Bean
  public RestaurantTestHelper restraurantTestHelper(Flyway flyway) {

    return new RestaurantTestHelper(flyway);
  }

}
