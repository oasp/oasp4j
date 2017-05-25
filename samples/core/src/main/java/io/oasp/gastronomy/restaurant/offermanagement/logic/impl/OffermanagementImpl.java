package io.oasp.gastronomy.restaurant.offermanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.general.logic.base.AbstractComponentFacade;
import io.oasp.gastronomy.restaurant.general.logic.base.UcManageBinaryObject;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.ProductType;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.exception.OfferEmptyException;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.ProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.DrinkDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.MealDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.ProductDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SideDishDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferCto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.Valid;

import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation class for {@link Offermanagement}.
 *
 */
@Named
@Transactional
public class OffermanagementImpl extends AbstractComponentFacade implements Offermanagement {

  private static final Logger LOG = LoggerFactory.getLogger(OffermanagementImpl.class);

  /** @see #getOfferDao() */
  private OfferDao offerDao;

  /** @see #setProductDao(ProductDao) */
  private ProductDao productDao;

  /** @see #setMealDao(MealDao) */
  private MealDao mealDao;

  /** @see #setDrinkDao(DrinkDao) */
  private DrinkDao drinkDao;

  /** @see #setSideDishDao(SideDishDao) */
  private SideDishDao sideDishDao;

  /** **/
  private UcManageBinaryObject ucManageBinaryObject;

  /**
   * The constructor.
   */
  public OffermanagementImpl() {

    super();
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public OfferEto findOffer(Long id) {

    LOG.debug("Get OfferEto with id '{}' from database.", id);
    return getBeanMapper().map(getOfferDao().findOne(id), OfferEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public OfferCto findOfferCto(Long id) {

    LOG.debug("Get OfferCTO with id '{}' from database.", id);
    OfferCto result = new OfferCto();
    // offer
    OfferEto offerEto = findOffer(id);
    if (offerEto == null) {
      return null;
    }
    result.setOffer(offerEto);
    // meal
    Long mealId = offerEto.getMealId();
    if (mealId != null) {
      result.setMeal(findMeal(mealId));
    }
    // drink
    Long drinkId = offerEto.getDrinkId();
    if (drinkId != null) {
      result.setDrink(findDrink(drinkId));
    }
    // sideDish
    Long sideDishId = offerEto.getSideDishId();
    if (sideDishId != null) {
      result.setSideDish(findSideDish(sideDishId));
    }
    return result;
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<OfferEto> findAllOffers() {

    LOG.debug("Get all offers from database.");
    return getBeanMapper().mapList(getOfferDao().findAll(), OfferEto.class);
  }

  @Override
  @RolesAllowed({ PermissionConstants.FIND_OFFER, PermissionConstants.FIND_PRODUCT })
  public boolean isProductInUseByOffer(ProductEto product) {

    LOG.debug("Get all offers from database for the given product with id '" + product.getId() + "'.");

    List<OfferEto> persistedOffers = findAllOffers();

    /*
     * Check the occurrence of a product within all offers. Therefore, only check for a instance of a product type
     * product type.
     */
    ProductType productType = null;

    if (product instanceof DrinkEto) {
      LOG.debug("The given product is an instance of Drink '" + product.getDescription() + "', id '" + product.getId()
          + "'. Check all Offer-Drinks for that given occurrence.");
      productType = ProductType.DRINK;
    } else if (product instanceof MealEto) {
      LOG.debug("The given product is an instance of Meal '" + product.getDescription() + "', id '" + product.getId()
          + "'. Check all Offer-Meals for that given occurrence.");
      productType = ProductType.MEAL;
    } else if (product instanceof SideDishEto) {
      LOG.debug("The given product is an instance of SideDish '" + product.getDescription() + "', id '"
          + product.getId() + "'. Check all Offer-SideDishes for that given occurrence.");
      productType = ProductType.SIDEDISH;
    }

    if (productType == null) {
      LOG.debug("The given product not in use by an offer.");
      return false;
    }

    for (OfferEto offer : persistedOffers) {

      if (productType.isDrink()) {
        if (Objects.equals(offer.getDrinkId(), product.getId())) {
          LOG.debug("The given product is in use by offer with id '" + offer.getId() + "', description '"
              + offer.getDescription() + "'.");
          return true;
        }
        continue;

      }

      if (productType.isMeal()) {
        if (Objects.equals(offer.getMealId(), product.getId())) {
          LOG.debug("The given product is in use by offer with id '" + offer.getId() + "', description '"
              + offer.getDescription() + "'.");
          return true;
        }
        continue;
      }

      if (productType.isSideDish()) {
        if (Objects.equals(offer.getSideDishId(), product.getId())) {
          LOG.debug("The given product is in use by offer with id '" + offer.getId() + "', description '"
              + offer.getDescription() + "'.");
          return true;
        }
        continue;
      }
    }

    LOG.debug("The given product not in use by an offer.");
    return false;
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy) {

    List<OfferEntity> offers = getOfferDao().findOffersFiltered(offerFilterBo, sortBy);
    LOG.debug("'" + offers.size() + "' offers fetched.");

    List<OfferEto> offerBos = new ArrayList<>(offers.size());
    for (OfferEntity o : offers) {
      offerBos.add(getBeanMapper().map(o, OfferEto.class));
    }
    return offerBos;
  }

  @Override
  @RolesAllowed(PermissionConstants.DELETE_OFFER)
  public void deleteOffer(Long offerId) {

    getOfferDao().delete(offerId);
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_OFFER)
  public OfferEto saveOffer(@Valid OfferEto offer) {

    Objects.requireNonNull(offer, "offer");

    if ((offer.getMealId() == null) && (offer.getDrinkId() == null) && (offer.getSideDishId() == null)) {
      throw new OfferEmptyException(offer);
    } else {
      OfferEntity persistedOffer = getOfferDao().save(getBeanMapper().map(offer, OfferEntity.class));
      return getBeanMapper().map(persistedOffer, OfferEto.class);
    }
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public ProductEto findProduct(Long id) {

    LOG.debug("Get Product with id '" + id + "' from database.");
    ProductEntity product = getProductDao().findOne(id);
    if (product == null) {
      return null;
    } else {
      return getBeanMapper().map(product, ProductEto.class);
    }
  }

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

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<ProductEto> findAllProducts() {

    LOG.debug("Get all products from database.");
    return getBeanMapper().mapList(getProductDao().findAll(), ProductEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<MealEto> findAllMeals() {

    LOG.debug("Get all meals with from database.");
    return getBeanMapper().mapList(this.mealDao.findAll(), MealEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<DrinkEto> findAllDrinks() {

    LOG.debug("Get all drinks with from database.");
    return getBeanMapper().mapList(this.drinkDao.findAll(), DrinkEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public List<SideDishEto> findAllSideDishes() {

    LOG.debug("Get all sidedishes with from database.");
    return getBeanMapper().mapList(this.sideDishDao.findAll(), SideDishEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<ProductEto> findProductsFiltered(ProductFilter productFilterBo, ProductSortBy sortBy) {

    LOG.debug("Fetch filtered offers.");
    return getBeanMapper().mapList(getProductDao().findProductsFiltered(productFilterBo, sortBy), ProductEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public BinaryObjectEto findProductPicture(Long productId) {

    ProductEto product = findProduct(productId);
    if (product != null) {
      return getUcManageBinaryObject().findBinaryObject(product.getPictureId());
    } else {
      return null;
    }
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT_PICTURE)
  public Blob findProductPictureBlob(Long productId) {

    ProductEto product = findProduct(productId);
    if (product != null) {
      return getUcManageBinaryObject().getBinaryObjectBlob(product.getPictureId());
    } else {
      return null;
    }
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT_PICTURE)
  public ProductEto findProductByRevision(Long id, Number revision) {

    LOG.debug("Get Product with id '" + id + "' and revision '" + revision + "' from database.");
    ProductEntity product = getProductDao().load(id, revision);
    if (product == null) {
      return null;
    } else {
      return getBeanMapper().map(product, ProductEto.class);
    }
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_PRODUCT)
  public ProductEto saveProduct(ProductEto product) {

    Objects.requireNonNull(product, "product");

    ProductEntity persistedProduct = getProductDao().save(getBeanMapper().map(product, ProductEntity.class));
    return getBeanMapper().map(persistedProduct, ProductEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.DELETE_PRODUCT)
  public void deleteProduct(Long productId) {

    getProductDao().delete(productId);
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_PRODUCT_PICTURE)
  public void updateProductPicture(Long productId, Blob blob, BinaryObjectEto binaryObjectEto) {

    ProductEntity product = getProductDao().findOne(productId);
    if (product != null) {
      binaryObjectEto = getUcManageBinaryObject().saveBinaryObject(blob, binaryObjectEto);
      product.setPictureId(binaryObjectEto.getId());
      getProductDao().save(product);
    } else {
      throw new ObjectNotFoundUserException(Product.class, productId);
    }

  }

  @Override
  @RolesAllowed(PermissionConstants.DELETE_PRODUCT_PICTURE)
  public void deleteProductPicture(Long productId) {

    ProductEntity product = getProductDao().findOne(productId);
    if (product != null) {
      getUcManageBinaryObject().deleteBinaryObject(product.getPictureId());
    }
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public PaginatedListTo<OfferEto> findOfferEtos(OfferSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<OfferEntity> offers = getOfferDao().findOffers(criteria);
    return mapPaginatedEntityList(offers, OfferEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_PRODUCT)
  public PaginatedListTo<ProductEto> findProductEtos(ProductSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<ProductEntity> products = getProductDao().findProducts(criteria);
    return mapPaginatedEntityList(products, ProductEto.class);
  }

  /**
   * @return {@link OfferDao} instance.
   */
  public OfferDao getOfferDao() {

    return this.offerDao;
  }

  /**
   * Sets the field 'offerDao'.
   *
   * @param offerDao New value for offerDao
   */
  @Inject
  public void setOfferDao(OfferDao offerDao) {

    this.offerDao = offerDao;
  }

  /**
   * @param ucManageBinaryObject the ucManageBinaryObject to set
   */
  @Inject
  public void setUcManageBinaryObject(UcManageBinaryObject ucManageBinaryObject) {

    this.ucManageBinaryObject = ucManageBinaryObject;
  }

  /**
   * @return ucManageBinaryObject
   */
  public UcManageBinaryObject getUcManageBinaryObject() {

    return this.ucManageBinaryObject;
  }

  /**
   * @return productDao
   */
  public ProductDao getProductDao() {

    return this.productDao;
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
