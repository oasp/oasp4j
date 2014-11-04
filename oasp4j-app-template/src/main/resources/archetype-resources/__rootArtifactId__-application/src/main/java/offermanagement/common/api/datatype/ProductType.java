#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.common.api.datatype;


/**
 * This enum contains the available types of a {@link ${package}.offermanagement.common.api.Product}.
 *
 * @author jozitz
 */
public enum ProductType {

  /**
   * This value identifies a {@link ${package}.offermanagement.common.api.Drink}.
   */
  DRINK,

  /**
   * This value identifies a {@link ${package}.offermanagement.common.api.Meal}.
   */
  MEAL,

  /**
   * This value identifies a {@link ${package}.offermanagement.common.api.SideDish}.
   */
  SIDEDISH;

  /**
   * @return <code>true</code>, if this value equals {@link ProductType${symbol_pound}DRINK} . <code>false</code> otherwise.
   */
  public boolean isDrink() {

    return equals(DRINK);
  }

  /**
   * @return <code>true</code>, if this value equals {@link ProductType${symbol_pound}MEAL}. <code>false</code> otherwise.
   */
  public boolean isMeal() {

    return equals(MEAL);
  }

  /**
   * @return <code>true</code>, if this value equals {@link ProductType${symbol_pound}SIDEDISH}. <code>false</code> otherwise.
   */
  public boolean isSideDish() {

    return equals(SIDEDISH);
  }

}
