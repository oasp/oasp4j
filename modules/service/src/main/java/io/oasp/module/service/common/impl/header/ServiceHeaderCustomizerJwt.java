package io.oasp.module.service.common.impl.header;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import io.oasp.module.service.common.api.config.ServiceConfig;
import io.oasp.module.service.common.api.header.ServiceHeaderContext;
import io.oasp.module.service.common.api.header.ServiceHeaderCustomizer;

/**
 * @author ssarmoka
 *
 */
public class ServiceHeaderCustomizerJwt implements ServiceHeaderCustomizer {

  private static final String AUTHORIZATION = "Authorization";

  private static final String CONTENT_TYPE = "Content-Type";

  /**
   *
   * The constructor.
   */
  public ServiceHeaderCustomizerJwt() {
    super();
  }

  @Override
  public void addHeaders(ServiceHeaderContext<?> context) {

    String auth = context.getConfig().getChildValue(ServiceConfig.KEY_SEGMENT_AUTH);
    if (!"jwt".equals(auth)) {
      return;
    }
    SecurityContext securityContext = SecurityContextHolder.getContext();
    if (securityContext == null) {
      return;
    }

    String authorizationHeader = context.getConfig().getChildValue(AUTHORIZATION);
    String contentType = context.getConfig().getChildValue(CONTENT_TYPE);
    context.setHeader(AUTHORIZATION, authorizationHeader + "");
    context.setHeader(CONTENT_TYPE, contentType);
  }

}
