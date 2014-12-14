package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.ProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.DrinkDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.MealDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SideDishDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindProduct;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractProductUc;

import java.sql.Blob;
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

  /** @see #setMealDao(MealDao) */
  private MealDao mealDao;

  /** @see #setDrinkDao(DrinkDao) */
  private DrinkDao drinkDao;

  /** @see #setSideDishDao(SideDishDao) */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public BinaryObjectEto findProductPicture(Long productId) {

    return getUcManageBinaryObject().findBinaryObject(findProduct(productId).getPictureId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Blob findProductPictureBlob(Long productId) {

    return getUcManageBinaryObject().getBinaryObjectBlob(findProductPicture(productId).getId());
  }
}
