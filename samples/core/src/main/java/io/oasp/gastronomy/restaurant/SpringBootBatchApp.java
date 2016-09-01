package io.oasp.gastronomy.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import io.oasp.module.jpa.dataaccess.api.AdvancedRevisionEntity;

/**
 * Class containing main method as an entry point for spring-boot based app.
 */
@SpringBootApplication(exclude = { EndpointAutoConfiguration.class, SecurityAutoConfiguration.class,
SecurityFilterAutoConfiguration.class, })
@EntityScan(basePackages = { "io.oasp.gastronomy.restaurant" }, basePackageClasses = { AdvancedRevisionEntity.class })
@EnableGlobalMethodSecurity(jsr250Enabled = false)
public class SpringBootBatchApp {

  /**
   * Entry point for spring-boot based app
   *
   * @param args - arguments
   */
  public static void main(String[] args) {

    SpringApplication.run(SpringBootApp.class, args);
  }
}
