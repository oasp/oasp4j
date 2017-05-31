package io.oasp.module.service.common.api.header;

import io.oasp.module.service.common.api.context.ServiceContext;

/**
 * Extends {@link ServiceContext} and allows to {@link #setHeader(String, String) set headers} to the underlying network
 * protocol.
 * 
 * @param <S> the generic type of the {@link #getApi() service API}.
 */
public interface ServiceHeaderContext<S> extends ServiceContext<S> {

  /**
   * Adds a header to underlying network invocations (e.g. HTTP) triggered by a
   * {@link io.oasp.module.service.common.api.ServiceClientFactory#create(Class) service client}.
   *
   * @param key the name of the header to set.
   * @param value the value of the header to set.
   */
  void setHeader(String key, String value);

}
