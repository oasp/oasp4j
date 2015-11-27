package io.oasp.gastronomy.restaurant.general.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementSecurityAutoConfiguration.class,
DispatcherServletAutoConfiguration.class })
@ImportResource("config/app/service/beans-service.xml")
public class SpringBootApp {
  public static void main(String[] args) {

    SpringApplication.run(SpringBootApp.class, args);
  }
}
