package io.oasp.gastronomy.restaurant.offermanagement.batch.configuration;

/**
 * sroeger This type is needed to conver
 *
 * @author sroeger
 */
public class OfferCsv {

  private String name;

  private String description;

  private String state;

  private String mealId;

  private String sideDishId;

  private String drinkId;

  private String price;

  /**
   * @return name
   */
  public String getName() {

    return this.name;
  }

  /**
   * @param name new value of {@link #getname}.
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
   * @param description new value of {@link #getdescription}.
   */
  public void setDescription(String description) {

    this.description = description;
  }

  /**
   * @return state
   */
  public String getState() {

    return this.state;
  }

  /**
   * @param state new value of {@link #getstate}.
   */
  public void setState(String state) {

    this.state = state;
  }

  /**
   * @return mealId
   */
  public String getMealId() {

    return this.mealId;
  }

  /**
   * @param mealId new value of {@link #getmealId}.
   */
  public void setMealId(String mealId) {

    this.mealId = mealId;
  }

  /**
   * @return sideDishId
   */
  public String getSideDishId() {

    return this.sideDishId;
  }

  /**
   * @param sideDishId new value of {@link #getsideDishId}.
   */
  public void setSideDishId(String sideDishId) {

    this.sideDishId = sideDishId;
  }

  /**
   * @return drinkId
   */
  public String getDrinkId() {

    return this.drinkId;
  }

  /**
   * @param drinkId new value of {@link #getdrinkId}.
   */
  public void setDrinkId(String drinkId) {

    this.drinkId = drinkId;
  }

  /**
   * @return price
   */
  public String getPrice() {

    return this.price;
  }

  /**
   * @param price new value of {@link #getprice}.
   */
  public void setPrice(String price) {

    this.price = price;
  }

}
