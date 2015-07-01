package io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.DrinkDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.MealDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.ProductDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SideDishDao;

import javax.inject.Inject;

/**
 * Abstract use case for Products, which provides access to the commonly necessary data access objects.
 *
 * @author mbrunnli
 * @since dev
 */
public abstract class AbstractProductUc extends AbstractUc {

  /**
   * @see #setProductDao(ProductDao)
   */
  private ProductDao productDao;

  /**
   * @see #setMealDao(MealDao)
   */
  private MealDao mealDao;

  /**
   * @see #setDrinkDao(DrinkDao)
   */
  private DrinkDao drinkDao;

  /**
   * @see #setSideDishDao(SideDishDao)
   */
  private SideDishDao sideDishDao;

  /**
   * @return productDao
   */
  public ProductDao getProductDao() {

    return this.productDao;
  }

  /**
   * @return mealDao
   */
  public MealDao getMealDao() {

    return this.mealDao;
  }

  /**
   * @return drinkDao
   */
  public DrinkDao getDrinkDao() {

    return this.drinkDao;
  }

  /**
   * @return sideDishDao
   */
  public SideDishDao getSideDishDao() {

    return this.sideDishDao;
  }

  /**
   * Sets the field 'productDao'.
   *
   * @param productDao New value for productDao
   */
  @Inject
  public void setProductDao(ProductDao productDao) {

    this.productDao = productDao;
  }

  /**
   * @param mealDao the {@link MealDao} to {@link Inject}.
   */
  @Inject
  public void setMealDao(MealDao mealDao) {

    this.mealDao = mealDao;
  }

  /**
   * @param drinkDao the {@link DrinkDao} to {@link Inject}.
   */
  @Inject
  public void setDrinkDao(DrinkDao drinkDao) {

    this.drinkDao = drinkDao;
  }

  /**
   * @param sideDishDao the {@link SideDishDao} to {@link Inject}.
   */
  @Inject
  public void setSideDishDao(SideDishDao sideDishDao) {

    this.sideDishDao = sideDishDao;
  }

}
