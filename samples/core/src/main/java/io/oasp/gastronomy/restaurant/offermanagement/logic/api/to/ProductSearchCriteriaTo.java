package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;

/**
 * This is the {@link SearchCriteriaTo search criteria} {@link net.sf.mmm.util.transferobject.api.TransferObject TO}
 * used to find {@link Product}s.
 *
 * If no boolean is set to true, no {@link Product}s will be found.
 *
 */
public class ProductSearchCriteriaTo extends SearchCriteriaTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private boolean fetchDrinks;

  private boolean fetchMeals;

  private boolean fetchSideDishes;

  private String name;

  private String description;

  /**
   * The constructor.
   */
  public ProductSearchCriteriaTo() {

    super();

    this.fetchDrinks = true;
    this.fetchMeals = true;
    this.fetchSideDishes = true;

  }

  /**
   * @return fetchDrinks
   */
  public boolean isFetchDrinks() {

    return this.fetchDrinks;
  }

  /**
   * @param fetchDrinks the fetchDrinks to set
   */
  public void setFetchDrinks(boolean fetchDrinks) {

    this.fetchDrinks = fetchDrinks;
  }

  /**
   * @return fetchMeals
   */
  public boolean isFetchMeals() {

    return this.fetchMeals;
  }

  /**
   * @param fetchMeals the fetchMeals to set
   */
  public void setFetchMeals(boolean fetchMeals) {

    this.fetchMeals = fetchMeals;
  }

  /**
   * @return fetchSideDishes
   */
  public boolean isFetchSideDishes() {

    return this.fetchSideDishes;
  }

  /**
   * @param fetchSideDishes the fetchSideDishes to set
   */
  public void setFetchSideDishes(boolean fetchSideDishes) {

    this.fetchSideDishes = fetchSideDishes;
  }

  /**
   * @return name
   */
  public String getName() {

    return this.name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @return description
   */
  public String getDescription() {

    return this.description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {

    this.description = description;
  }

  @Override
  protected void toString(StringBuilder buffer) {

    buffer.append("ProductSearchCriteriaTo [fetchDrinks=" + this.fetchDrinks + ", fetchMeals=" + this.fetchMeals
        + ", fetchSideDishes=" + this.fetchSideDishes + ", name=" + this.name + ", description=" + this.description
        + "]");
  }

}
