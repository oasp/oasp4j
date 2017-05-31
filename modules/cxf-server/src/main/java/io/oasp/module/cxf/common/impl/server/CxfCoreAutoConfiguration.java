package io.oasp.module.cxf.common.impl.server;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import io.oasp.module.service.common.api.constants.ServiceConstants;

/**
 * Core {@link Configuration} for Apache CXF.
 *
 * @see CxfRestAutoConfiguration
 * @see CxfSoapAutoConfiguration
 */
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CxfCoreAutoConfiguration {

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