package io.oasp.gastronomy.restaurant.general.service.impl.config;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import javax.xml.ws.Endpoint;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

// BEGIN ARCHETYPE SKIP
import io.oasp.gastronomy.restaurant.tablemanagement.service.impl.ws.v1_0.TablemanagementWebServiceImpl;
// END ARCHETYPE SKIP
import io.oasp.module.rest.service.impl.RestServiceExceptionFacade;
import io.oasp.module.rest.service.impl.json.ObjectMapperFactory;

/**
 * {@link Configuration} for (REST or SOAP) services using CXF.
 */
@Configuration
@EnableWs
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class ServiceConfig extends WsConfigurerAdapter {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(ServiceConfig.class);

  /** The services "folder" of an URL. */
  public static final String URL_FOLDER_SERVICES = "services";

  public static final String URL_PATH_SERVICES = "/" + URL_FOLDER_SERVICES;

  public static final String URL_FOLDER_REST = "/rest";

  public static final String URL_FOLDER_WEB_SERVICES = "/ws";

  public static final String URL_PATH_REST_SERVICES = URL_PATH_SERVICES + "/" + URL_FOLDER_REST;

  public static final String URL_PATH_WEB_SERVICES = URL_PATH_SERVICES + "/" + URL_FOLDER_WEB_SERVICES;

  @Value("${security.expose.error.details}")
  boolean exposeInternalErrorDetails;

  @Inject
  private ApplicationContext applicationContext;

  @Inject
  private ObjectMapperFactory objectMapperFactory;

  @Bean(name = "cxf")
  public SpringBus springBus() {

    return new SpringBus();
  }

  @Bean
  public JacksonJsonProvider jacksonJsonProvider() {

    return new JacksonJsonProvider(this.objectMapperFactory.createInstance());
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {

    CXFServlet cxfServlet = new CXFServlet();
    ServletRegistrationBean servletRegistration = new ServletRegistrationBean(cxfServlet, URL_PATH_SERVICES + "/*");
    return servletRegistration;
  }

  @Bean
  public Server jaxRsServer() {

    // List<Object> restServiceBeans = new
    // ArrayList<>(this.applicationContext.getBeansOfType(RestService.class).values());
    Collection<Object> restServices = findRestServices();
    if (restServices.isEmpty()) {
      LOG.info("No REST Services have been found. Rest Endpoint will not be enabled in CXF.");
      return null;
    }
    Collection<Object> providers = this.applicationContext.getBeansWithAnnotation(Provider.class).values();

    JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
    factory.setBus(springBus());
    factory.setAddress(URL_FOLDER_REST);
    // factory.set
    factory.setServiceBeans(new ArrayList<>(restServices));
    factory.setProviders(new ArrayList<>(providers));

    return factory.create();
  }

  private Collection<Object> findRestServices() {

    return this.applicationContext.getBeansWithAnnotation(Path.class).values();
  }

  @Bean
  public RestServiceExceptionFacade restServiceExceptionFacade() {

    RestServiceExceptionFacade exceptionFacade = new RestServiceExceptionFacade();
    exceptionFacade.setExposeInternalErrorDetails(this.exposeInternalErrorDetails);
    return exceptionFacade;
  }

  // BEGIN ARCHETYPE SKIP
  @Bean
  public Endpoint tableManagement() {

    // Bus bus = (Bus) this.applicationContext.getBean(Bus.DEFAULT_BUS_ID);
    EndpointImpl endpoint = new EndpointImpl(springBus(), new TablemanagementWebServiceImpl());
    endpoint.publish("/TablemanagementWebService");
    return endpoint;
  }
  // END ARCHETYPE SKIP
}