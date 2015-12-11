package io.oasp.gastronomy.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class,
HibernateJpaAutoConfiguration.class })
@ImportResource("config/app/service/beans-service.xml")
public class SpringBootApp {

  /**
   * Entry point for spring-boot based app
   *
   * @param args - arguments
   */
  public static void main(String[] args) {

    SpringApplication.run(SpringBootApp.class, args);
  }
}
