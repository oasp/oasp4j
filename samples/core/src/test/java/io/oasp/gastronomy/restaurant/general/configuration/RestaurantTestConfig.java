package io.oasp.gastronomy.restaurant.general.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import io.oasp.gastronomy.restaurant.general.common.DbTestHelperImpl;
import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.SecurityTestHelper;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.module.basic.configuration.SpringProfileConstants;
import io.oasp.module.test.common.helper.api.DbTestHelper;

/**
 * This configuration class provides {@code @Bean} annotated methods. It is applied to a test class by using the
 * following class annotation: {@code @SpringApplicationConfiguration(classes = RestaurantTestConfig.class)}. Hence,
 * beans provided by {@code @Bean} annotated methods will not be available outside the test configuration. <br/>
 * <br/>
 * See {@link AbstractRestServiceTest} as an example.
 *
 * @author jmolinar, shuber
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
   * @return an instance of type {@link RestTemplate}
   */
  @Bean
  public RestTemplate restTemplate() {

    return new RestTemplate();
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
   * @return an instance of type {@link DbTestHelperImpl}.
   */
  @Bean
  public DbTestHelper dbTestHelper(Flyway flyway,
      @Value("${server.rest.test.flyway.migration}") String migrationVersion) {

    return new DbTestHelperImpl(flyway, migrationVersion);

  }

}
