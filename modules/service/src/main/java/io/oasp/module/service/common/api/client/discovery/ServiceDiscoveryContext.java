package io.oasp.module.service.common.api.client.discovery;

import io.oasp.module.basic.common.api.config.ConfigProperties;
import io.oasp.module.service.common.api.client.context.ServiceContext;

/**
 * Extends {@link ServiceContext} and allows to {@link #setConfig(ConfigProperties) update the configuration
 * properties}.
 *
 * @param <S> the generic type of the {@link #getApi() service API}.
 *
 * @since 3.0.0
 */
public interface ServiceDiscoveryContext<S> extends ServiceContext<S> {

  /**
   * @param config the {@link ServiceDiscoverer#discover(ServiceDiscoveryContext) discovered}
   *        {@link io.oasp.module.service.common.api.Service} configuration such as {@link #getUrl() URL}. Has to be
   *        {@link ConfigProperties#inherit(ConfigProperties) inherited} from {@link #getConfig() existing
   *        configuration properties}.
   */
  void setConfig(ConfigProperties config);

}
