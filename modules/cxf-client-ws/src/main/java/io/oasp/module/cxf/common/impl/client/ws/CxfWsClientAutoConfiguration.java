package io.oasp.module.cxf.common.impl.client.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.oasp.module.service.common.api.sync.SyncServiceClientFactory;

/**
 * {@link Configuration} for REST (JAX-RS) clients using Apache CXF.
 *
 * @since 3.0.0
 */
@Configuration
public class CxfWsClientAutoConfiguration {

  /**
   * @return an implemenation of {@link SyncServiceClientFactory} based on CXF for REST (JAX-RS).
   */
  @Bean
  public SyncServiceClientFactory syncServiceClientFactoryCxfRest() {

    return new SyncServiceClientFactoryCxfWs();
  }

}
