#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api.to;

import net.sf.mmm.util.transferobject.api.CompositeTo;

/**
 * Abstract base class for a <em>{@link CompositeTo composite transfer-object}</em> in this application.
 *
 * @author hohwille
 */
public abstract class AbstractCto extends CompositeTo {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public AbstractCto() {

    super();
  }

}
