package org.oasp.module.rest.service.impl;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;

/**
 * Test-case for {@link RestServiceExceptionFacade}.
 *
 * @author hohwille
 */
public class RestServiceExceptionFacadeTest extends Assert {

  /**
   * @return the {@link RestServiceExceptionFacade} instance to test.
   */
  protected RestServiceExceptionFacade getExceptionFacade() {

    return new RestServiceExceptionFacade();
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with various security exceptions including
   * subclasses of them.
   */
  @Test
  public void testSecurityExceptions() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();

    String secretMessage = "Secret information not to be revealed on client - only to be logged on server!";

    Response response = exceptionFacade.toResponse(new AccessDeniedException(secretMessage));
    assertEquals(403, response.getStatus());
    Object entity = response.getEntity();
    assertNull(entity); // no content

    // TODO test more exceptions
  }

  // TODO add more test methods to cover all cases.

}
