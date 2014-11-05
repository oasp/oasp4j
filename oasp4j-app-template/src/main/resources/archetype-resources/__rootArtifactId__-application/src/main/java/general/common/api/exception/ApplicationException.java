#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api.exception;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * Abstract main exception.
 *
 * @author jozitz
 */
public abstract class ApplicationException extends NlsRuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * @param message the error {@link ${symbol_pound}getNlsMessage() message}.
   */
  public ApplicationException(NlsMessage message) {

    super(message);
  }

  /**
   * @param cause the error {@link ${symbol_pound}getCause() cause}.
   * @param message the error {@link ${symbol_pound}getNlsMessage() message}.
   */
  public ApplicationException(Throwable cause, NlsMessage message) {

    super(cause, message);
  }

}
