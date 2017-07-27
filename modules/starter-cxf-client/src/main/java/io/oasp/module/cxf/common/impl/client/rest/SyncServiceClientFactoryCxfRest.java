package io.oasp.module.cxf.common.impl.client.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.module.basic.common.api.config.ConfigProperties;
import io.oasp.module.cxf.common.impl.client.interceptor.PerformanceStartInterceptor;
import io.oasp.module.cxf.common.impl.client.interceptor.PerformanceStopInterceptor;
import io.oasp.module.cxf.common.impl.client.interceptor.TechnicalExceptionInterceptor;
import io.oasp.module.service.common.api.client.context.ServiceContext;
import io.oasp.module.service.common.api.config.ServiceConfig;
import io.oasp.module.service.common.api.constants.ServiceConstants;
import io.oasp.module.service.common.api.sync.SyncServiceClientFactory;

/**
 * Implementation of {@link SyncServiceClientFactory} for JAX-RS REST service clients using Apache CXF.
 *
 * @since 3.0.0
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

    String url = context.getUrl();
    url = url.replace(ServiceConstants.VARIABLE_TYPE, ServiceConstants.URL_FOLDER_REST);
    S serviceClient = JAXRSClientFactory.create(url, context.getApi(), providers);

    ClientConfiguration clientConfig = WebClient.getConfig(serviceClient);
    applyInterceptors(context, clientConfig, serviceName);
    applyClientPolicy(context, clientConfig);
    applyHeaders(context, serviceClient);
    return serviceClient;
  }

  /**
   * @param context the {@link ServiceContext}.
   * @param clientConfig the {@link ClientConfiguration} where to apply the {@link HTTPClientPolicy} to.
   */
  protected void applyClientPolicy(ServiceContext<?> context, ClientConfiguration clientConfig) {

    HTTPClientPolicy clientPolicy = createClientPolicy(context);
    if (clientPolicy != null) {
      HTTPConduit httpConduit = clientConfig.getHttpConduit();
      httpConduit.setClient(clientPolicy);
    }
  }

  /**
   * @param context the {@link ServiceContext}.
   * @return the {@link HTTPClientPolicy} for the {@link ServiceContext#getConfig() configuration} or {@code null} to
   *         use defaults.
   */
  protected HTTPClientPolicy createClientPolicy(ServiceContext<?> context) {

    ConfigProperties timeoutConfig = context.getConfig().getChild(ServiceConfig.KEY_SEGMENT_TIMEOUT);
    if (!timeoutConfig.isEmpty()) {
      HTTPClientPolicy policy = new HTTPClientPolicy();
      Long connectionTimeout =
          timeoutConfig.getChild(ServiceConfig.KEY_SEGMENT_TIMEOUT_CONNECTION).getValue(Long.class);
      if (connectionTimeout != null) {
        policy.setConnectionTimeout(connectionTimeout.longValue());
      }
      Long responseTimeout = timeoutConfig.getChild(ServiceConfig.KEY_SEGMENT_TIMEOUT_RESPONSE).getValue(Long.class);
      if (responseTimeout != null) {
        policy.setReceiveTimeout(responseTimeout.longValue());
      }
      return policy;
    }
    return null;
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
   * @param clientConfig the {@link ClientConfiguration}.
   * @param serviceName the {@link #createServiceName(ServiceContext) service name}.
   */
  protected void applyInterceptors(ServiceContext<?> context, ClientConfiguration clientConfig, String serviceName) {

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
