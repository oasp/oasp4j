package io.oasp.module.basic.common.api;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * {@link NlsBundle} for OASP modules.
 */
public interface NlsBundleOaspRoot extends NlsBundle {

  /**
   * @param service the service that failed (e.g. qualified name of service interface).
   * @param message the actual error message.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("While invoking the service {service} the following error occurred: {message}. Probably the service is temporary unavailable."
      + "Please try again later. If the problem persists contact your system administrator.")
  NlsMessage errorServiceInvocationFailed(@Named("service") String service, @Named("message") String message);

}
