package io.oasp.module.cxf.common.impl.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.oasp.module.service.common.api.client.ServiceClientFactory;
import io.oasp.module.service.common.api.client.discovery.ServiceDiscoverer;
import io.oasp.module.service.common.api.config.ServiceConfig;
import io.oasp.module.service.common.api.header.ServiceHeaderCustomizer;
import io.oasp.module.service.common.base.config.ServiceConfigProperties;
import io.oasp.module.service.common.impl.ServiceClientFactoryImpl;
import io.oasp.module.service.common.impl.discovery.ServiceDiscovererImplConfig;
import io.oasp.module.service.common.impl.header.ServiceHeaderCustomizerBasicAuth;
import io.oasp.module.service.common.impl.header.ServiceHeaderCustomizerCorrelationId;
import io.oasp.module.service.common.impl.header.ServiceHeaderCustomizerJwt;
import io.oasp.module.service.common.impl.header.ServiceHeaderCustomizerOAuth;

/**
 * {@link Configuration} for REST (JAX-RS) clients using Apache CXF.
 *
 * @since 3.0.0
 */
@Configuration
// @Import(ServiceClientSpringFactory.class)
public class CxfClientAutoConfiguration {

  /**
   * @return the implementation of {@link ServiceClientFactory}.
   */
  @Bean
  public ServiceClientFactory serviceClientFactory() {

    return new ServiceClientFactoryImpl();
  }

  /**
   * @return the implementation of {@link ServiceConfig}.
   */
  @Bean
  public ServiceConfig serviceClientConfig() {

    return new ServiceConfigProperties();
  }

  /**
   * @return an implementation of {@link ServiceDiscoverer} based on {@link #serviceClientConfig()}.
   */
  @Bean
  public ServiceDiscoverer serviceDiscovererConfig() {

    return new ServiceDiscovererImplConfig();
  }

  /**
   * @return an implementation of {@link ServiceHeaderCustomizer} passing correlation ID.
   */
  @Bean
  public ServiceHeaderCustomizer serviceHeaderCustomizerCorrelationId() {

    return new ServiceHeaderCustomizerCorrelationId();
  }

  /**
   * @return an implementation of {@link ServiceHeaderCustomizer} for basic authentication support.
   */
  @Bean
  public ServiceHeaderCustomizer serviceHeaderCustomizerBasicAuth() {

    return new ServiceHeaderCustomizerBasicAuth();
  }

  /**
   * @return an implementation of {@link ServiceHeaderCustomizer} for OAuth support.
   */
  @Bean
  public ServiceHeaderCustomizer serviceHeaderCustomizerOAuth() {

    return new ServiceHeaderCustomizerOAuth();
  }

  /**
   * @return an implementation of {@link ServiceHeaderCustomizer} for JWT support.
   */
  @Bean
  public ServiceHeaderCustomizer ServiceHeaderCustomizerJwt() {

    return new ServiceHeaderCustomizerJwt();
  }
}
