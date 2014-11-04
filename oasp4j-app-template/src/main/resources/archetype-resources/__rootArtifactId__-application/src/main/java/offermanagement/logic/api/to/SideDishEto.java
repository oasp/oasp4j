#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.api.to;

import ${package}.offermanagement.common.api.SideDish;

/**
 * The {@link ${package}.general.common.api.to.AbstractEto ETO} for a {@link SideDish}.
 *
 * @author jozitz
 */
public class SideDishEto extends ProductEto implements SideDish {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public SideDishEto() {

    super();
  }

}
