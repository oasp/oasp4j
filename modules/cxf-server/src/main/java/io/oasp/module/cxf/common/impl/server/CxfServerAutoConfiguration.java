package io.oasp.module.cxf.common.impl.server;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import io.oasp.module.cxf.common.impl.server.rest.CxfServerRestAutoConfiguration;
import io.oasp.module.cxf.common.impl.server.soap.CxfServerSoapAutoConfiguration;
import io.oasp.module.service.common.api.constants.ServiceConstants;

/**
 * Basic {@link Configuration} for Apache CXF server.
 *
 * @see CxfServerRestAutoConfiguration
 * @see CxfServerSoapAutoConfiguration
 *
 * @since 3.0.0
 */
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CxfServerAutoConfiguration {

  /**
   * @return the {@link ServletRegistrationBean} to register {@link CXFServlet} according to OASP conventions at
   *         {@link ServiceConstants#URL_PATH_SERVICES}.
   */
  @Bean
  public ServletRegistrationBean servletRegistrationBean() {

    CXFServlet cxfServlet = new CXFServlet();
    ServletRegistrationBean servletRegistration =
        new ServletRegistrationBean(cxfServlet, ServiceConstants.URL_PATH_SERVICES + "/*");
    return servletRegistration;
  }

}