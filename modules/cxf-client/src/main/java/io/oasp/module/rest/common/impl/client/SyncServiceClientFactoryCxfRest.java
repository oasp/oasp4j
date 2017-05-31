package io.oasp.module.rest.common.impl.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.module.service.common.api.context.ServiceContext;
import io.oasp.module.service.common.api.sync.SyncServiceClientFactory;
import io.oasp.module.service.common.impl.cxf.interceptor.PerformanceStartInterceptor;
import io.oasp.module.service.common.impl.cxf.interceptor.PerformanceStopInterceptor;
import io.oasp.module.service.common.impl.cxf.interceptor.TechnicalExceptionInterceptor;

/**
 * Implementation of {@link SyncServiceClientFactory} for JAX-RS REST service clients using Apache CXF.
 */
public class SyncServiceClientFactoryCxfRest implements SyncServiceClientFactory {

  private JacksonJsonProvider jsonProvider;

  /**
   * @param jsonProvider the {@link JacksonJsonProvider} to {@link Inject}.
   */
  @Inject
  public void setJsonProvider(JacksonJsonProvider jsonProvider) {

    this.jsonProvider = jsonProvider;
  }

  @Override
  public <S> S create(ServiceContext<S> context) {

    boolean responsible = isResponsibleForService(context);
    if (!responsible) {
      return null;
    }
    String serviceName = createServiceName(context);
    List<Object> providers = createProviderList(context, serviceName);

    S serviceClient = JAXRSClientFactory.create(context.getUrl(), context.getApi(), providers);

    applyInterceptors(context, serviceClient, serviceName);
    applyHeaders(context, serviceClient);
    return serviceClient;
  }

  /**
   * @param context the {@link ServiceContext}.
   * @return the display name of the service for exception or log messages.
   */
  protected String createServiceName(ServiceContext<?> context) {

    return context.getApi().getName();
  }

  /**
   * Applies CXF interceptors to the given {@code serviceClient}.
   *
   * @param context the {@link ServiceContext}.
   * @param serviceClient the service client instance.
   * @param serviceName the {@link #createServiceName(ServiceContext) service name}.
   */
  protected void applyInterceptors(ServiceContext<?> context, Object serviceClient, String serviceName) {

    ClientConfiguration clientConfig = WebClient.getConfig(serviceClient);
    clientConfig.getOutInterceptors().add(new PerformanceStartInterceptor());
    clientConfig.getInInterceptors().add(new PerformanceStopInterceptor());
    clientConfig.getInFaultInterceptors().add(new TechnicalExceptionInterceptor(serviceName));
  }

  /**
   * Applies headers to the given {@code serviceClient}.
   *
   * @param context the {@link ServiceContext}.
   * @param serviceClient the service client instance.
   */
  protected void applyHeaders(ServiceContext<?> context, Object serviceClient) {

    Collection<String> headerNames = context.getHeaderNames();
    if (!headerNames.isEmpty()) {
      Client webClient = WebClient.client(serviceClient);
      for (String headerName : headerNames) {
        webClient.header(headerName, context.getHeader(headerName));
      }
    }
  }

  /**
   * @param context the {@link ServiceContext}.
   * @return {@code true} if this implementation is responsibe for creating a service client corresponding to the given
   *         {@link ServiceContext}, {@code false} otherwise.
   */
  protected boolean isResponsibleForService(ServiceContext<?> context) {

    return context.getApi().isAnnotationPresent(Path.class);
  }

  /**
   * @param context the {@link ServiceContext}.
   * @param serviceName the {@link #createServiceName(ServiceContext) service name}.
   * @return the {@link List} of {@link javax.ws.rs.ext.Provider}s.
   */
  protected List<Object> createProviderList(ServiceContext<?> context, String serviceName) {

    List<Object> providers = new ArrayList<>();
    if (this.jsonProvider != null) {
      providers.add(this.jsonProvider);
    }
    providers.add(new RestServiceExceptionMapper(serviceName));
    return providers;
  }

}
