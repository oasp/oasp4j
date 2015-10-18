package io.oasp.gastronomy.restaurant.general.common;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO todo_jczas This type ...
 *
 * @author ABIELEWI
 */
@Configuration
public class JobTestConfig {

  @Bean
  public JobLauncherTestUtils jobLauncherTestUtils() {

    return new JobLauncherTestUtils();
  }
}
