package io.oasp.module.cxf.common.impl.client.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.oasp.module.cxf.common.impl.client.ws.SyncServiceClientFactoryCxfWs;
import io.oasp.module.service.common.api.sync.SyncServiceClientFactory;

/**
 * {@link Configuration} for REST (JAX-RS) clients using Apache CXF.
 *
 * @since 3.0.0
 */
@Configuration
public class CxfRestClientAutoConfiguration {

  /**
   * @return an implemenation of {@link SyncServiceClientFactory} based on CXF for REST (JAX-RS).
   */
  @Bean
  public SyncServiceClientFactory syncServiceClientFactoryCxfRest() {

    return new SyncServiceClientFactoryCxfRest();
  }

  /**
   * @return an implemenation of {@link SyncServiceClientFactory} based on CXF for SOAP Services (JAX-WS).
   */
  @Bean
  public SyncServiceClientFactory syncServiceClientFactoryCxfWs() {

    return new SyncServiceClientFactoryCxfWs();
  }

}
