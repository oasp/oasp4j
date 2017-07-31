package io.oasp.module.cxf.common.impl.client.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import io.oasp.module.basic.common.api.config.ConfigProperties;
import io.oasp.module.cxf.common.impl.client.interceptor.PerformanceStartInterceptor;
import io.oasp.module.cxf.common.impl.client.interceptor.PerformanceStopInterceptor;
import io.oasp.module.cxf.common.impl.client.interceptor.TechnicalExceptionInterceptor;
import io.oasp.module.cxf.common.impl.client.rest.RestServiceExceptionMapper;
import io.oasp.module.service.common.api.client.context.ServiceContext;
import io.oasp.module.service.common.api.config.ServiceConfig;
import io.oasp.module.service.common.api.constants.ServiceConstants;
import io.oasp.module.service.common.api.sync.SyncServiceClientFactory;

/**
 * Implementation of {@link SyncServiceClientFactory} for JAX-WS SOAP service clients using Apache CXF.
 *
 * @since 3.0.0
 */
public class SyncServiceClientFactoryCxfWs implements SyncServiceClientFactory {

  private static final String WSDL_SUFFIX = "?wsdl";

  @Override
  public <S> S create(ServiceContext<S> context) {

    boolean responsible = isResponsibleForService(context);
    if (!responsible) {
      return null;
    }
    String url = getUrl(context);

    Class<S> api = context.getApi();
    S serviceClient = createService(context, url, api);
    Client cxfClient = ClientProxy.getClient(serviceClient);
    applyInterceptors(context, cxfClient, api.getName());
    applyClientPolicy(context, cxfClient);
    applyHeaders(context, cxfClient);
    return serviceClient;
  }

  private <S> S createService(ServiceContext<S> context, String url, Class<S> api) {

    WebService webService = api.getAnnotation(WebService.class);
    QName qname = new QName(getNamespace(api, webService), getLocalName(api, webService));
    boolean downloadWsdl = context.getConfig().getChild(ServiceConfig.KEY_SEGMENT_WSDL)
        .getChild(ServiceConfig.KEY_SEGMENT_DISABLE_DOWNLOAD).getValueAsBoolean();
    URL wsdlUrl = null;
    if (downloadWsdl) {
      try {
        wsdlUrl = new URL(url);
      } catch (MalformedURLException e) {
        throw new IllegalArgumentException("Illegal URL: " + url, e);
      }
    }
    S serviceClient = Service.create(wsdlUrl, qname).getPort(api);
    if (!downloadWsdl) {
      BindingProvider bindingProvider = (BindingProvider) serviceClient;
      bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
    }
    return serviceClient;
  }

  private <S> String getUrl(ServiceContext<S> context) {

    String url = context.getUrl();
    url = url.replace(ServiceConstants.VARIABLE_TYPE, ServiceConstants.URL_FOLDER_WEB_SERVICE);
    if (!url.endsWith(WSDL_SUFFIX)) {
      String serviceName = context.getApi().getSimpleName();
      if (!url.endsWith(serviceName)) {
        if (!url.endsWith("/")) {
          url = url + "/";
        }
        url = url + serviceName;
      }
      url = url + WSDL_SUFFIX;
    }
    return url;
  }

  private String getLocalName(Class<?> api, WebService webService) {

    String portName = webService.portName();
    if (portName.isEmpty()) {
      return api.getSimpleName();
    }
    return portName;
  }

  private String getNamespace(Class<?> api, WebService webService) {

    String targetNamespace = webService.targetNamespace();
    if (targetNamespace.isEmpty()) {
      return api.getPackage().getName();
    }
    return targetNamespace;
  }

  /**
   * @param context the {@link ServiceContext}.
   * @param client the {@link ClientConfiguration} where to apply the {@link HTTPClientPolicy} to.
   */
  protected void applyClientPolicy(ServiceContext<?> context, Client client) {

    Conduit conduit = client.getConduit();
    if (conduit instanceof HTTPConduit) {
      HTTPClientPolicy clientPolicy = createClientPolicy(context);
      if (clientPolicy != null) {
        HTTPConduit httpConduit = (HTTPConduit) conduit;
        httpConduit.setClient(clientPolicy);
      }
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
   * @param client the {@link Client}.
   * @param serviceName the {@link #createServiceName(ServiceContext) service name}.
   */
  protected void applyInterceptors(ServiceContext<?> context, Client client, String serviceName) {

    client.getOutInterceptors().add(new PerformanceStartInterceptor());
    client.getInInterceptors().add(new PerformanceStopInterceptor());
    client.getInFaultInterceptors().add(new TechnicalExceptionInterceptor(serviceName));

  }

  /**
   * Applies headers to the given {@code serviceClient}.
   *
   * @param context the {@link ServiceContext}.
   * @param client the {@link Client}.
   */
  protected void applyHeaders(ServiceContext<?> context, Client client) {

    Collection<String> headerNames = context.getHeaderNames();
    if (!headerNames.isEmpty()) {
      Map<String, List<String>> headers = new HashMap<>();
      for (String headerName : headerNames) {
        headers.put(headerName, Arrays.asList(context.getHeader(headerName)));
      }
      client.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
    }
  }

  /**
   * @param context the {@link ServiceContext}.
   * @return {@code true} if this implementation is responsibe for creating a service client corresponding to the given
   *         {@link ServiceContext}, {@code false} otherwise.
   */
  protected boolean isResponsibleForService(ServiceContext<?> context) {

    return context.getApi().isAnnotationPresent(WebService.class);
  }

  /**
   * @param context the {@link ServiceContext}.
   * @param serviceName the {@link #createServiceName(ServiceContext) service name}.
   * @return the {@link List} of {@link javax.ws.rs.ext.Provider}s.
   */
  protected List<Object> createProviderList(ServiceContext<?> context, String serviceName) {

    List<Object> providers = new ArrayList<>();
    providers.add(new RestServiceExceptionMapper(serviceName));
    return providers;
  }

}
