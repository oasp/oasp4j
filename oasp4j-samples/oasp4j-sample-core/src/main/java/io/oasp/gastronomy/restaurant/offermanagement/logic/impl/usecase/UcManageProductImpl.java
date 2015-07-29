package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.general.logic.base.UcManageBinaryObject;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.ProductType;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.ProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageProduct;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractProductUc;

import java.sql.Blob;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

/**
 * Use case implementation for modifying and deleting Offers
 *
 * @author mbrunnli
 * @since dev
 */
@Named
@UseCase
@Validated
public class UcManageProductImpl extends AbstractProductUc implements UcManageProduct {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageProductImpl.class);

  /** Use case to find Offers */
  private UcFindOffer ucFindOffer;

  /** Use case for managing binary objects */
  private UcManageBinaryObject ucManageBinaryObject;

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
    binaryObjectEto = this.ucManageBinaryObject.saveBinaryObject(blob, binaryObjectEto);
    product.setPictureId(binaryObjectEto.getId());
    getProductDao().save(product);

  }

  @Override
  @RolesAllowed(PermissionConstants.DELETE_PRODUCT_PICTURE)
  public void deleteProductPicture(Long productId) {

    ProductEntity product = getProductDao().findOne(productId);
    this.ucManageBinaryObject.deleteBinaryObject(product.getPictureId());

  }

  @Override
  @RolesAllowed({ PermissionConstants.FIND_OFFER, PermissionConstants.FIND_PRODUCT })
  public boolean isProductInUseByOffer(ProductEto product) {

    LOG.debug("Get all offers from database for the given product with id '" + product.getId() + "'.");

    List<OfferEto> persistedOffers = this.ucFindOffer.findAllOffers();

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

  /**
   * @param ucManageBinaryObject new value of {@link #getucManageBinaryObject}.
   */
  @Inject
  @UseCase
  public void setUcManageBinaryObject(UcManageBinaryObject ucManageBinaryObject) {

    this.ucManageBinaryObject = ucManageBinaryObject;
  }

  /**
   * @param ucFindOffer new value of {@link #getucFindOffer}.
   */
  @Inject
  @UseCase
  public void setUcFindOffer(UcFindOffer ucFindOffer) {

    this.ucFindOffer = ucFindOffer;
  }
}
