package io.oasp.gastronomy.restaurant.general.configuration;

import io.oasp.gastronomy.restaurant.general.service.impl.rest.ApplicationObjectMapperFactory;
import io.oasp.module.rest.service.impl.RestServiceExceptionFacade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
public class ServicesConfiguration {

  @Bean
  public ObjectMapper jacksonObjectMapper() {

    ApplicationObjectMapperFactory factory = new ApplicationObjectMapperFactory();
    return factory.createInstance();
  }

  @Bean
  public JacksonJsonProvider jacksonJsonProvider() {

    JacksonJsonProvider provider = new JacksonJsonProvider();
    ObjectMapper mapper = jacksonObjectMapper();
    provider.setMapper(mapper);
    return provider;
  }

  // <!-- By convention REST services should be located in the path folder "rest" -->

  @Value("${security.expose.error.details}")
  boolean exposeInternalErrorDetails;

  @Bean
  public RestServiceExceptionFacade restServiceExceptionFacade() {

    System.out.println("zmienna: " + this.exposeInternalErrorDetails);
    RestServiceExceptionFacade exceptionFacade = new RestServiceExceptionFacade();
    exceptionFacade.setExposeInternalErrorDetails(this.exposeInternalErrorDetails);
    return exceptionFacade;
  }
}