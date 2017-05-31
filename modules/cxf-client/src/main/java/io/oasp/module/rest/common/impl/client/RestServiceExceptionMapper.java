package io.oasp.module.rest.common.impl.client;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.oasp.module.service.common.api.constants.ServiceConstants;
import io.oasp.module.service.common.api.exception.ServiceInvocationFailedException;

/**
 * An Implementation of {@link ResponseExceptionMapper} that converts a REST failure {@link Response} compliant with
 * <a href="https://github.com/oasp-forge/oasp4j-wiki/wiki/guide-rest#error-results">OASP REST error specification</a>
 * to a {@link ServiceInvocationFailedException}.
 */
@Provider
public class RestServiceExceptionMapper implements ResponseExceptionMapper<Throwable> {

  private String service;

  /**
   * The constructor.
   *
   * @param service the name (e.g. {@link Class#getName() qualified name}) of the
   *        {@link io.oasp.module.service.common.api.Service} that failed.
   */
  public RestServiceExceptionMapper(String service) {
    super();
    this.service = service;
  }

  @Override
  public Throwable fromResponse(Response response) {

    response.bufferEntity();
    if (response.hasEntity()) {
      String json = response.readEntity(String.class);
      if ((json != null) && !json.isEmpty()) {
        try {
          ObjectMapper objectMapper = new ObjectMapper();
          Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
          Throwable exception = createException(jsonMap);
          return exception;
        } catch (IOException e) {
          return new IllegalStateException("Invocation of service", e);
        }
      }
    }
    return null;
  }

  private Throwable createException(Map<String, Object> jsonMap) {

    String code = (String) jsonMap.get(ServiceConstants.KEY_CODE);
    String message = (String) jsonMap.get(ServiceConstants.KEY_MESSAGE);
    String uuid = (String) jsonMap.get(ServiceConstants.KEY_UUID);

    Throwable exception = createException(code, message, UUID.fromString(uuid));
    return exception;
  }

  private Throwable createException(String code, String message, UUID uuid) {

    Throwable exception = new ServiceInvocationFailedException(message, code, uuid, this.service);
    return exception;
  }

}
