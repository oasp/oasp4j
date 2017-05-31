package io.oasp.module.service.common.api.discovery;

import io.oasp.module.service.common.api.context.ServiceContext;

/**
 * Extends {@link ServiceContext} and allows to {@link #setUrl(String) set URL}.
 * 
 * @param <S> the generic type of the {@link #getApi() service API}.
 */
public interface ServiceDiscoveryContext<S> extends ServiceContext<S> {

  /**
   * @param url the {@link ServiceDiscoverer#discover(ServiceDiscoveryContext) discovered}
   *        {@link io.oasp.module.service.common.api.Service} {@link #getUrl() URL}. May be called only once.
   */
  void setUrl(String url);

  /**
   * @param name the {@link #getValueNames() name} (key) of value to set.
   * @param value the value to set.
   */
  void setValue(String name, Object value);

}
