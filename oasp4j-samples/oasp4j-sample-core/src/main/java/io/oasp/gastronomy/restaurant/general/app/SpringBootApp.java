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
    /*
     * System.out.println("Let's inspect the beans provided by Spring Boot:"); String[] beanNames =
     * ctx.getBeanDefinitionNames(); Arrays.sort(beanNames); for (String beanName : beanNames) {
     * System.out.println(beanName); }
     */

  }
}
