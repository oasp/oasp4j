package io.oasp.module.web.common.base;

import java.io.IOException;
import java.util.Arrays;

import net.sf.mmm.util.io.api.RuntimeIoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * Registers new {@link WebApplicationInitializer} which allows setting spring profiles in application properties.
 *
 * @author sspielma
 */
public class PropertiesWebApplicationContextInitializer implements
    ApplicationContextInitializer<ConfigurableWebApplicationContext> {

  /** Logger instance. */
  private static final Logger log = LoggerFactory.getLogger(PropertiesWebApplicationContextInitializer.class);

  /**
   * List of application property resource names.
   */
  private String[] applicationPropertyResources = { "classpath:/config/app/application-default.properties",
  "classpath:/config/env/application.properties" };

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(ConfigurableWebApplicationContext applicationContext) {

    CompositePropertySource compositePropertySource = new CompositePropertySource("application properties");
    for (String propertyName : Arrays.asList(this.applicationPropertyResources)) {
      try {
        log.debug("Registering " + propertyName + " as property source.");
        compositePropertySource.addPropertySource(new ResourcePropertySource(propertyName));
        applicationContext.getEnvironment().getPropertySources().addFirst(compositePropertySource);
      } catch (IOException e) {
        throw new RuntimeIoException(e);
      }
    }
  }

  /**
   * Overwrites default application property resources.
   *
   * @param applicationPropertyResources the applicationPropertyResources to set
   */
  public void setApplicationPropertyResources(String... applicationPropertyResources) {

    this.applicationPropertyResources = applicationPropertyResources;
  }
}
