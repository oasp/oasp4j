package io.oasp.gastronomy.restaurant.general.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.oasp.gastronomy.restaurant.general.common.DbTestHelper;
import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.SecurityTestHelper;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.module.basic.configuration.SpringProfileConstants;

/**
 * This configuration class provides {@code @Bean} annotated methods. It is applied to a test class by using the
 * following class annotation: {@code @SpringApplicationConfiguration(classes = RestaurantTestConfig.class)}. Hence,
 * beans provided by {@code @Bean} annotated methods will not be available outside the test configuration. <br/>
 * <br/>
 * See {@link AbstractRestServiceTest} as an example.
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
   * @return an instance of type {@link SecurityTestHelper}
   */
  @Bean
  public SecurityTestHelper securityTestHelper() {

    return new SecurityTestHelper();
  }

  /**
   * @param flyway an instance of type {@link Flyway}.
   * @return an instance of type {@link DbTestHelper}.
   */
  @Bean
  public DbTestHelper dbTestHelper(Flyway flyway) {

    return new DbTestHelper(flyway);

  }

}
