#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.common.api;

/**
 * This is the interface for a {@link Product} that represents a {@link Drink}.
 *
 * @author hohwille
 */
public interface Drink extends Product {

  /**
   * @return <code>true</code> if this drink is containing alcohol, <code>false</code> otherwise.
   */
  boolean isAlcoholic();

  /**
   * @param alcoholic is the new value of {@link ${symbol_pound}isAlcoholic() alcoholic}.
   */
  void setAlcoholic(boolean alcoholic);

}
