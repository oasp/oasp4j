package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This configuration class provides a {@link SalesmanagementRestServiceTestHelper} and a {@link RestTemplate} bean
 */
@Configuration
public class SalesmanagementRestTestConfig {

  /**
   * This method is creating {@link SalesmanagementRestServiceTestHelper} bean
   *
   * @return {@link SalesmanagementRestServiceTestHelper}
   */
  @Bean
  public SalesmanagementRestServiceTestHelper salesmanagementRestServiceTestHelper() {

    SalesmanagementRestServiceTestHelper salesmanagementRestServiceTestHelper =
        new SalesmanagementRestServiceTestHelper();
    return salesmanagementRestServiceTestHelper;
  }

  /**
   * This method is creating {@link RestTemplate} bean
   *
   * @return {@link RestTemplate}
   */
  @Bean
  public RestTemplate restTemplate() {

    RestTemplate restTemplate = new RestTemplate();
    return restTemplate;
  }
}