#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api;

import ${package}.offermanagement.common.api.Drink;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author loverbec
 */
@Entity(name = "Drink")
@DiscriminatorValue("Drink")
public class DrinkEntity extends ProductEntity implements Drink {

  private static final long serialVersionUID = 1L;

  private boolean alcoholic;

  /**
   * The constructor.
   */
  public DrinkEntity() {

    super();
  }

  /**
   * Returns the field 'alcoholic'.
   *
   * @return Value of alcoholic
   */
  public boolean isAlcoholic() {

    return this.alcoholic;
  }

  /**
   * Sets the field 'alcoholic'.
   *
   * @param alcoholic New value for alcoholic
   */
  public void setAlcoholic(boolean alcoholic) {

    this.alcoholic = alcoholic;
  }

}
