#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api.exception;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * Abstract business <i>checked</i> main exception.
 *
 * @author jozitz
 */
public abstract class ApplicationBusinessException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  /**
   * @param message the error {@link ${symbol_pound}getNlsMessage() message}.
   */
  public ApplicationBusinessException(NlsMessage message) {

    super(message);
  }

  /**
   * @param cause the error {@link ${symbol_pound}getCause() cause}.
   * @param message the error {@link ${symbol_pound}getNlsMessage() message}.
   */
  public ApplicationBusinessException(Throwable cause, NlsMessage message) {

    super(cause, message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    return false;
  }

}
