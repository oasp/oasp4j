package io.oasp.gastronomy.restaurant.general.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import io.oasp.gastronomy.restaurant.SpringBootApp;

public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

    return application.sources(SpringBootApp.class);
  }
}