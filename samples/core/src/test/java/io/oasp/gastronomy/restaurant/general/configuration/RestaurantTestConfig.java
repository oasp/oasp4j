package io.oasp.gastronomy.restaurant.general.configuration;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.RestraurantTestHelper;
import io.oasp.module.basic.configuration.SpringProfileConstants;

/**
 * TODO jmolinar This type ...
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
  public RestraurantTestHelper restraurantTestHelper(Flyway flyway, DataSource dataSource) {

    return new RestraurantTestHelper(flyway, dataSource);
  }

}
