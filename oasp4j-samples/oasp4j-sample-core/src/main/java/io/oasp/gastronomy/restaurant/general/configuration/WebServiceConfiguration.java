package io.oasp.gastronomy.restaurant.general.configuration;

import io.oasp.gastronomy.restaurant.tablemanagement.service.impl.ws.v1_0.TablemanagementWebServiceImpl;

import javax.xml.ws.Endpoint;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

@Configuration
@EnableWs
public class WebServiceConfiguration extends WsConfigurerAdapter {

  @Bean(name = "cxf")
  public SpringBus springBus() {

    return new SpringBus();
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {

    CXFServlet cxfServlet = new CXFServlet();
    ServletRegistrationBean servlet = new ServletRegistrationBean(cxfServlet, "/ws/*");
    // servlet.setLoadOnStartup(1);
    return servlet;
  }

  @Bean
  public Endpoint tableManagement() {

    // Bus bus = (Bus) this.applicationContext.getBean(Bus.DEFAULT_BUS_ID);
    EndpointImpl endpoint = new EndpointImpl(springBus(), new TablemanagementWebServiceImpl());
    endpoint.publish("/TablemanagementWebService");
    return endpoint;
  }
}