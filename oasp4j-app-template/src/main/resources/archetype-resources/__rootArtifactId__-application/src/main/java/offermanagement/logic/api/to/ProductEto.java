#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.api.to;

import ${package}.offermanagement.common.api.Product;

/**
 * The {@link ${package}.general.common.api.to.AbstractEto ETO}
 * for a {@link Product}.
 * 
 * @author hohwille
 * @author jozitz
 */
public abstract class ProductEto extends MenuItemEto implements Product {

  private static final long serialVersionUID = 1L;

  // TODO oasp/oasp4j${symbol_pound}52
  // private byte[] picture;

  /**
   * Constructor.
   */
  public ProductEto() {

    super();
  }

}
