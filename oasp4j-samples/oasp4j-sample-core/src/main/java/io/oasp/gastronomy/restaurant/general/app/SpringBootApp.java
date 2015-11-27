package io.oasp.gastronomy.restaurant.general.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Main class for spring boot application
 *
 * @author tkuzynow
 */
@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
@ImportResource("config/app/beans-application.xml")
public class SpringBootApp extends WebMvcConfigurerAdapter {

  /**
   * Entry point for spring-boot based app
   *
   * @param args - arguments
   */
  public static void main(String[] args) {

    SpringApplication.run(SpringBootApp.class, args);
  }
}
