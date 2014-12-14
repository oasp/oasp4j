package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import net.sf.mmm.util.transferobject.api.AbstractTransferObject;

/**
 * {@link AbstractTransferObject TO} to filter {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product
 * products}.
 *
 * @author etomety
 */
public class ProductFilter extends AbstractTransferObject {

  private static final long serialVersionUID = 1L;

  private boolean fetchDrinks;

  private boolean fetchMeals;

  private boolean fetchSideDishes;

  /**
   * Constructor.
   *
   * Initializes the private fields fetchDrinks, fetchMeals and fetchSideDishes with <code>false</code>. So nothing is
   * fetched by default.
   */
  public ProductFilter() {

    this.fetchDrinks = false;
    this.fetchMeals = false;
    this.fetchSideDishes = false;
  }

  /**
   * Constructor.
   *
   * @param fetchDrinks is the flag for getting {@link DrinkEto drinks} from db (set this to <code>true</code>)
   * @param fetchMeals is the flag for getting {@link MealEto meals} from db (set this to <code>true</code>)
   * @param fetchSideDishes is the flag for getting {@link SideDishEto side dishes} from db (set this to
   *        <code>true</code>)
   */
  public ProductFilter(boolean fetchDrinks, boolean fetchMeals, boolean fetchSideDishes) {

    this.fetchDrinks = fetchDrinks;
    this.fetchMeals = fetchMeals;
    this.fetchSideDishes = fetchSideDishes;
  }

  /**
   * Returns the field 'fetchSideDishes'.
   *
   * @return Value of fetchSideDishes
   */
  public boolean getFetchSideDishes() {

    return this.fetchSideDishes;
  }

  /**
   * Sets the field 'fetchSideDishes'.
   *
   * @param fetchSideDishes New value for fetchSideDishes
   */
  public void setFetchSideDishes(boolean fetchSideDishes) {

    this.fetchSideDishes = fetchSideDishes;
  }

  /**
   * Returns the field 'fetchMeals'.
   *
   * @return Value of fetchMeals
   */
  public boolean getFetchMeals() {

    return this.fetchMeals;
  }

  /**
   * Sets the field 'fetchMeals'.
   *
   * @param fetchMeals New value for fetchMeals
   */
  public void setFetchMeals(boolean fetchMeals) {

    this.fetchMeals = fetchMeals;
  }

  /**
   * Returns the field 'fetchDrinks'.
   *
   * @return Value of fetchDrinks
   */
  public boolean getFetchDrinks() {

    return this.fetchDrinks;
  }

  /**
   * Sets the field 'fetchDrinks'.
   *
   * @param fetchDrinks New value for fetchDrinks
   */
  public void setFetchDrinks(boolean fetchDrinks) {

    this.fetchDrinks = fetchDrinks;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (this.fetchDrinks ? 1 : 0);
    result = prime * result + (this.fetchMeals ? 1 : 0);
    result = prime * result + (this.fetchSideDishes ? 1 : 0);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    ProductFilter other = (ProductFilter) obj;
    if (this.fetchDrinks != other.fetchDrinks) {
      return false;
    }
    if (this.fetchMeals != other.fetchMeals) {
      return false;
    }
    if (this.fetchSideDishes != other.fetchSideDishes) {
      return false;
    }
    return true;
  }

}
