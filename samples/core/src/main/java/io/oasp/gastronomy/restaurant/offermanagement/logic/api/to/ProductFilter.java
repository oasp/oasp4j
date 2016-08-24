package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.module.basic.common.api.to.AbstractTo;

import net.sf.mmm.util.transferobject.api.AbstractTransferObject;

/**
 * {@link AbstractTransferObject TO} to filter {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product
 * products}.
 *
 */
public class ProductFilter extends AbstractTo {

  private static final long serialVersionUID = 1L;

  private boolean fetchDrinks;

  private boolean fetchMeals;

  private boolean fetchSideDishes;

  /**
   * Constructor.
   *
   * Initializes the private fields fetchDrinks, fetchMeals and fetchSideDishes with {@code false}. So nothing is
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
   * @param fetchDrinks is the flag for getting {@link DrinkEto drinks} from db (set this to {@code true})
   * @param fetchMeals is the flag for getting {@link MealEto meals} from db (set this to {@code true})
   * @param fetchSideDishes is the flag for getting {@link SideDishEto side dishes} from db (set this to {@code true})
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

}
