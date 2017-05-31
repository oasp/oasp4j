package io.oasp.module.service.common.impl;

import java.util.Collection;

import javax.inject.Inject;

import io.oasp.module.service.common.api.ServiceClientFactory;
import io.oasp.module.service.common.api.discovery.ServiceDiscoverer;
import io.oasp.module.service.common.api.header.ServiceHeaderCustomizer;
import io.oasp.module.service.common.api.sync.SyncServiceClientFactory;
import io.oasp.module.service.common.base.context.ServiceContextImpl;

/**
 * This is the implementation of {@link ServiceClientFactory}.
 */
public class ServiceClientFactoryImpl implements ServiceClientFactory {

  private Collection<SyncServiceClientFactory> syncServiceClientFactories;

  private Collection<ServiceDiscoverer> serviceDiscoverers;

  private Collection<ServiceHeaderCustomizer> serviceHeaderCustomizers;

  /**
   * @param syncServiceClientFactories the {@link Collection} of {@link SyncServiceClientFactory factories} to
   *        {@link Inject}.
   */
  @Inject
  public void setSyncServiceClientFactories(Collection<SyncServiceClientFactory> syncServiceClientFactories) {

    this.syncServiceClientFactories = syncServiceClientFactories;
  }

  /**
   * @param serviceDiscoverers the {@link Collection} of {@link ServiceDiscoverer}s to {@link Inject}.
   */
  @Inject
  public void setServiceDiscoverers(Collection<ServiceDiscoverer> serviceDiscoverers) {

    this.serviceDiscoverers = serviceDiscoverers;
  }

  /**
   * @param serviceHeaderCustomizers the {@link Collection} of {@link ServiceHeaderCustomizer}s to {@link Inject}.
   */
  @Inject
  public void setServiceHeaderCustomizers(Collection<ServiceHeaderCustomizer> serviceHeaderCustomizers) {

    this.serviceHeaderCustomizers = serviceHeaderCustomizers;
  }

  @Override
  public <S> S create(Class<S> serviceInterface) {

    ServiceContextImpl<S> context = new ServiceContextImpl<>(serviceInterface);
    discovery(serviceInterface, context);
    customizeHeaders(context);
    S serviceClient = createClient(serviceInterface, context);
    return serviceClient;
  }

  private <S> S createClient(Class<S> serviceInterface, ServiceContextImpl<S> context) {

    S serviceClient = null;
    for (SyncServiceClientFactory factory : this.syncServiceClientFactories) {
      serviceClient = factory.create(context);
      if (serviceClient != null) {
        break;
      }
    }
    if (serviceClient == null) {
      throw new IllegalStateException("Service client could not be created by any factory for " + serviceInterface);
    }
    return serviceClient;
  }

  private <S> void customizeHeaders(ServiceContextImpl<S> context) {

    for (ServiceHeaderCustomizer headerCustomizer : this.serviceHeaderCustomizers) {
      headerCustomizer.addHeaders(context);
    }
  }

  private <S> void discovery(Class<S> serviceInterface, ServiceContextImpl<S> context) {

    for (ServiceDiscoverer discoverer : this.serviceDiscoverers) {
      discoverer.discover(context);
      if (context.getUrl() != null) {
        break;
      }
    }
    if (context.getUrl() == null) {
      throw new IllegalStateException("Service discovery failed for " + serviceInterface);
    }
  }

}
