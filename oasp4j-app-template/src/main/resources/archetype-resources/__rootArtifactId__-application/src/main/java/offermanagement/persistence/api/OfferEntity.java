#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api;

import ${package}.general.common.api.datatype.Money;
import ${package}.offermanagement.common.api.Offer;
import ${package}.offermanagement.common.api.datatype.OfferState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author loverbec
 */
@Entity(name = "Offer")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "description" }) })
public class OfferEntity extends MenuItemEntity implements Offer {

  private static final long serialVersionUID = 1L;

  private Long number;

  private Money currentPrice;

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

  /**
   * {@inheritDoc}
   */
  @Column(name = "number", unique = true)
  @Override
  public Long getNumber() {

    return this.number;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setNumber(Long number) {

    this.number = number;
  }

  /**
   * Returns the field 'currentPrice'.
   *
   * @return Value of currentPrice
   */
  @Override
  public Money getCurrentPrice() {

    return this.currentPrice;
  }

  /**
   * Sets the field 'currentPrice'.
   *
   * @param currentPrice New value for currentPrice
   */
  @Override
  public void setCurrentPrice(Money currentPrice) {

    this.currentPrice = currentPrice;
  }

  /**
   * Returns the field 'meal'.
   *
   * @return Value of meal
   */
  @ManyToOne(fetch = FetchType.EAGER)
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

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public Long getMealId() {

    if (this.meal == null) {
      return null;
    }
    return this.meal.getId();
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public Long getDrinkId() {

    if (this.drink == null) {
      return null;
    }
    return this.drink.getId();
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public Long getSideDishId() {

    if (this.sideDish == null) {
      return null;
    }
    return this.sideDish.getId();
  }

  /**
   * {@inheritDoc}
   */
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
  public OfferState getState() {

    return this.state;
  }

  /**
   * Sets the field 'state'.
   *
   * @param state New value for state
   */
  public void setState(OfferState state) {

    this.state = state;
  }
}
