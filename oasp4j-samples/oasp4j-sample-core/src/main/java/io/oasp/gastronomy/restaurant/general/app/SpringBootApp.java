package io.oasp.gastronomy.restaurant.general.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ImportResource("config/app/beans-application.xml")
public class SpringBootApp extends WebMvcConfigurerAdapter {
  public static void main(String[] args) {

    ApplicationContext ctx = SpringApplication.run(SpringBootApp.class, args);
  }
}
