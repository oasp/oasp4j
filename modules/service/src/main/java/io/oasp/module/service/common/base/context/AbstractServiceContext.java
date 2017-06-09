package io.oasp.module.service.common.base.context;

import io.oasp.module.service.common.api.context.ServiceContext;

/**
 * The abstract base implementation of {@link ServiceContext}.
 *
 * @param <S> the generic type of the {@link #getApi() service API}.
 *
 * @since 3.0.0
 */
public abstract class AbstractServiceContext<S> implements ServiceContext<S> {

  private final Class<S> api;

  /**
   * The constructor.
   *
   * @param api the {@link #getApi() API}.
   */
  public AbstractServiceContext(Class<S> api) {
    super();
    this.api = api;
  }

  @Override
  public Class<S> getApi() {

    return this.api;
  }

  @Override
  public String getValue(String name) {

    return getValue(name, String.class);
  }

  @Override
  public <T> T getValue(Class<T> type) {

    return getValue(type.getName(), type);
  }

  @Override
  public <T> T getValue(String name, Class<T> type) {

    return getValue(name, type, null);
  }

}
