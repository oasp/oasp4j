package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
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

import javax.annotation.security.RolesAllowed;
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
@UseCase
public class UcFindProductImpl extends AbstractProductUc implements UcFindProduct {

  private static final Logger log = LoggerFactory.getLogger(UcFindProductImpl.class);

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
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public ProductEto findProduct(Long id) {

    log.debug("Get Product with id '" + id + "' from database.");
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
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
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
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
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
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
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
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<ProductEto> findAllProducts() {

    log.debug("Get all products from database.");
    return getBeanMapper().mapList(getProductDao().findAll(), ProductEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<MealEto> findAllMeals() {

    log.debug("Get all meals with from database.");
    return getBeanMapper().mapList(this.mealDao.findAll(), MealEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<DrinkEto> findAllDrinks() {

    log.debug("Get all drinks with from database.");
    return getBeanMapper().mapList(this.drinkDao.findAll(), DrinkEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<SideDishEto> findAllSideDishes() {

    log.debug("Get all sidedishes with from database.");
    return getBeanMapper().mapList(this.sideDishDao.findAll(), SideDishEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<ProductEto> findProductsFiltered(ProductFilter productFilterBo, ProductSortBy sortBy) {

    log.debug("Fetch filtered offers.");
    return getBeanMapper().mapList(getProductDao().findProductsFiltered(productFilterBo, sortBy), ProductEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public BinaryObjectEto findProductPicture(Long productId) {

    return getUcManageBinaryObject().findBinaryObject(findProduct(productId).getPictureId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT_PICTURE)
  public Blob findProductPictureBlob(Long productId) {

    return getUcManageBinaryObject().getBinaryObjectBlob(findProductPicture(productId).getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT_PICTURE)
  public ProductEto findProductByRevision(Long id, Number revision) {

    log.debug("Get Product with id '" + id + "' and revision '" + revision + "' from database.");
    ProductEntity product = getProductDao().load(id, revision);
    if (product == null) {
      return null;
    } else {
      return getBeanMapper().map(product, ProductEto.class);
    }
  }
}
