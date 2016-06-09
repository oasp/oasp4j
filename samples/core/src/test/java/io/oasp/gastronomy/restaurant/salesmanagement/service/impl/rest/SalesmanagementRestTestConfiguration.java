package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */
// @Configuration
public class SalesmanagementRestTestConfiguration {

  @Bean
  public SalesmanagementRestServiceTestHelper salesmanagementRestServiceTestHelper(
      JacksonJsonProvider jacksonJsonProvider, Flyway flyway, Salesmanagement salesmanagement) {

    SalesmanagementRestServiceTestHelper salesmanagementRestServiceTestHelper =
        new SalesmanagementRestServiceTestHelper(jacksonJsonProvider, flyway, salesmanagement);
    return salesmanagementRestServiceTestHelper;
  }
}
