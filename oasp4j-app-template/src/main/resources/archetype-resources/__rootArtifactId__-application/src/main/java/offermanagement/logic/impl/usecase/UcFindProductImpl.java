#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.impl.usecase;

import ${package}.offermanagement.logic.api.to.DrinkEto;
import ${package}.offermanagement.logic.api.to.MealEto;
import ${package}.offermanagement.logic.api.to.ProductEto;
import ${package}.offermanagement.logic.api.to.ProductFilter;
import ${package}.offermanagement.logic.api.to.ProductSortBy;
import ${package}.offermanagement.logic.api.to.SideDishEto;
import ${package}.offermanagement.logic.api.usecase.UcFindProduct;
import ${package}.offermanagement.logic.base.usecase.AbstractProductUc;
import ${package}.offermanagement.persistence.api.ProductEntity;
import ${package}.offermanagement.persistence.api.dao.DrinkDao;
import ${package}.offermanagement.persistence.api.dao.MealDao;
import ${package}.offermanagement.persistence.api.dao.SideDishDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.exception.api.ObjectMismatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcFindProduct}.
 *
 * @author jozitz
 */
@Named
public class UcFindProductImpl extends AbstractProductUc implements UcFindProduct {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindProductImpl.class);

  /** @see ${symbol_pound}setMealDao(MealDao) */
  private MealDao mealDao;

  /** @see ${symbol_pound}setDrinkDao(DrinkDao) */
  private DrinkDao drinkDao;

  /** @see ${symbol_pound}setSideDishDao(SideDishDao) */
  private SideDishDao sideDishDao;

  /**
   * The constructor.
   */
  public UcFindProductImpl() {

    super();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public ProductEto findProduct(Long id) {

    LOG.debug("Get Product with id '" + id + "' from database.");
    ProductEntity product = getProductDao().findOne(id);
    if (product == null) {
      return null;
    } else {
      return getBeanMapper().map(product, ProductEto.class);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MealEto findMeal(Long id) {

    ProductEto product = findProduct(id);
    try {
      return (MealEto) product;
    } catch (ClassCastException e) {
      throw new ObjectMismatchException(product, MealEto.class, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DrinkEto findDrink(Long id) {

    ProductEto product = findProduct(id);
    try {
      return (DrinkEto) product;
    } catch (ClassCastException e) {
      throw new ObjectMismatchException(product, DrinkEto.class, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SideDishEto findSideDish(Long id) {

    ProductEto product = findProduct(id);
    try {
      return (SideDishEto) product;
    } catch (ClassCastException e) {
      throw new ObjectMismatchException(product, SideDishEto.class, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProductEto> findAllProducts() {

    LOG.debug("Get all products from database.");
    return getBeanMapper().mapList(getProductDao().findAll(), ProductEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MealEto> findAllMeals() {

    LOG.debug("Get all meals with from database.");
    return getBeanMapper().mapList(this.mealDao.findAll(), MealEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DrinkEto> findAllDrinks() {

    LOG.debug("Get all drinks with from database.");
    return getBeanMapper().mapList(this.drinkDao.findAll(), DrinkEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<SideDishEto> findAllSideDishes() {

    LOG.debug("Get all sidedishes with from database.");
    return getBeanMapper().mapList(this.sideDishDao.findAll(), SideDishEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProductEto> findProductsFiltered(ProductFilter productFilterBo, ProductSortBy sortBy) {

    LOG.debug("Fetch filtered offers.");
    return getBeanMapper().mapList(getProductDao().findProductsFiltered(productFilterBo, sortBy), ProductEto.class);
  }
}
