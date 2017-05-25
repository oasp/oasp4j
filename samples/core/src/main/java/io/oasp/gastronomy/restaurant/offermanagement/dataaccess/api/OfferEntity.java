package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link Offer}.
 *
 */
@Entity
@Table(name = "Offer", uniqueConstraints = { @UniqueConstraint(columnNames = { "description" }) })
public class OfferEntity extends MenuItemEntity implements Offer {

  private static final long serialVersionUID = 1L;

  private Long number;

  private Money price;

  private MealEntity meal;

  private DrinkEntity drink;

  private SideDishEntity sideDish;

  private OfferState state;

  /**
   * The constructor.
   */
  public OfferEntity() {

    super();
  }

  /*
   * Uncomment the following Column annotation if the database used is Oracle 11g and comment the Column annotation just
   * before @Override annotation
   */
  // @Column(name = "\"number\"", unique = true)

  @Column(name = "number", unique = true)
  @Override
  public Long getNumber() {

    return this.number;
  }

  @Override
  public void setNumber(Long number) {

    this.number = number;
  }

  @Override
  public Money getPrice() {

    return this.price;
  }

  /**
   * Sets the field 'currentPrice'.
   *
   * @param currentPrice New value for currentPrice
   */
  @Override
  public void setPrice(Money currentPrice) {

    this.price = currentPrice;
  }

  /**
   * Returns the field 'meal'.
   *
   * @return Value of meal
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "mealId")
  public MealEntity getMeal() {

    return this.meal;
  }

  /**
   * Sets the field 'meal'.
   *
   * @param meal New value for meal
   */
  public void setMeal(MealEntity meal) {

    this.meal = meal;
  }

  @Override
  @Transient
  public Long getMealId() {

    if (this.meal == null) {
      return null;
    }
    return this.meal.getId();
  }

  @Override
  public void setMealId(Long mealId) {

    if (mealId == null) {
      this.meal = null;
    } else {
      MealEntity mealEntity = new MealEntity();
      mealEntity.setId(mealId);
      this.meal = mealEntity;
    }
  }

  /**
   * @return Value of drink
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "drinkId")
  public DrinkEntity getDrink() {

    return this.drink;
  }

  /**
   * Sets the field 'drink'.
   *
   * @param drink New value for drink
   */
  public void setDrink(DrinkEntity drink) {

    this.drink = drink;
  }

  @Override
  @Transient
  public Long getDrinkId() {

    if (this.drink == null) {
      return null;
    }
    return this.drink.getId();
  }

  @Override
  public void setDrinkId(Long drinkId) {

    if (drinkId == null) {
      this.drink = null;
    } else {
      DrinkEntity drinkEntity = new DrinkEntity();
      drinkEntity.setId(drinkId);
      this.drink = drinkEntity;
    }
  }

  /**
   * Returns the field 'sideDish'.
   *
   * @return Value of sideDish
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "sideDishId")
  public SideDishEntity getSideDish() {

    return this.sideDish;
  }

  /**
   * Sets the field 'sideDish'.
   *
   * @param sideDish New value for sideDish
   */
  public void setSideDish(SideDishEntity sideDish) {

    this.sideDish = sideDish;
  }

  @Override
  @Transient
  public Long getSideDishId() {

    if (this.sideDish == null) {
      return null;
    }
    return this.sideDish.getId();
  }

  @Override
  public void setSideDishId(Long sideDishId) {

    if (sideDishId == null) {
      this.sideDish = null;
    } else {
      SideDishEntity sideDishEntity = new SideDishEntity();
      sideDishEntity.setId(sideDishId);
      this.sideDish = sideDishEntity;
    }
  }

  /**
   * Returns the field 'state'.
   *
   * @return Value of state
   */
  @Override
  public OfferState getState() {

    return this.state;
  }

  /**
   * Sets the field 'state'.
   *
   * @param state New value for state
   */
  @Override
  public void setState(OfferState state) {

    this.state = state;
  }
}
