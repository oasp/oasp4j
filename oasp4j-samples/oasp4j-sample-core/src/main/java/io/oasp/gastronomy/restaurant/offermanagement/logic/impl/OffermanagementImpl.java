package io.oasp.gastronomy.restaurant.offermanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.base.AbstractBeanMapperSupport;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferCto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindProduct;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageProduct;

import java.sql.Blob;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation class for {@link Offermanagement}.
 *
 * @author loverbec
 */
@Named
public class OffermanagementImpl extends AbstractBeanMapperSupport implements Offermanagement {

  private UcFindOffer ucFindOffer;

  private UcManageOffer ucManageOffer;

  private UcFindProduct ucFindProduct;

  private UcManageProduct ucManageProduct;

  /**
   * The constructor.
   */
  public OffermanagementImpl() {

    super();
  }

  /**
   * Sets the field 'ucFindOffer'.
   *
   * @param ucFindOffer New value for ucFindOffer
   */
  @Inject
  @UseCase
  public void setUcFindOffer(UcFindOffer ucFindOffer) {

    this.ucFindOffer = ucFindOffer;
  }

  /**
   * Sets the field 'ucManageOffer'.
   *
   * @param ucManageOffer New value for ucManageOffer
   */
  @Inject
  @UseCase
  public void setUcManageOffer(UcManageOffer ucManageOffer) {

    this.ucManageOffer = ucManageOffer;
  }

  /**
   * Sets the field 'ucFindProduct'.
   *
   * @param ucFindProduct New value for ucFindProduct
   */
  @Inject
  @UseCase
  public void setUcFindProduct(UcFindProduct ucFindProduct) {

    this.ucFindProduct = ucFindProduct;
  }

  /**
   * Sets the field 'ucManageProduct'.
   *
   * @param ucManageProduct New value for ucManageProduct
   */
  @Inject
  @UseCase
  public void setUcManageProduct(UcManageProduct ucManageProduct) {

    this.ucManageProduct = ucManageProduct;
  }

  @Override
  public OfferEto findOffer(Long id) {

    return this.ucFindOffer.findOffer(id);
  }

  @Override
  public OfferCto findOfferCto(Long id) {

    return this.ucFindOffer.findOfferCto(id);
  }

  @Override
  public List<OfferEto> findAllOffers() {

    return this.ucFindOffer.findAllOffers();
  }

  @Override
  public List<ProductEto> findAllProducts() {

    return this.ucFindProduct.findAllProducts();
  }

  @Override
  public List<MealEto> findAllMeals() {

    return this.ucFindProduct.findAllMeals();
  }

  @Override
  public List<DrinkEto> findAllDrinks() {

    return this.ucFindProduct.findAllDrinks();
  }

  @Override
  public OfferEto saveOffer(OfferEto offer) {

    return this.ucManageOffer.saveOffer(offer);

  }

  @Override
  public void deleteOffer(Long offerId) {

    this.ucManageOffer.deleteOffer(offerId);
  }

  @Override
  public List<SideDishEto> findAllSideDishes() {

    return this.ucFindProduct.findAllSideDishes();
  }

  @Override
  public ProductEto findProduct(Long id) {

    return this.ucFindProduct.findProduct(id);
  }

  @Override
  public MealEto findMeal(Long id) {

    return this.ucFindProduct.findMeal(id);
  }

  @Override
  public DrinkEto findDrink(Long id) {

    return this.ucFindProduct.findDrink(id);
  }

  @Override
  public SideDishEto findSideDish(Long id) {

    return this.ucFindProduct.findSideDish(id);
  }

  @Override
  public ProductEto saveProduct(ProductEto product) {

    return this.ucManageProduct.saveProduct(product);
  }

  @Override
  public boolean isProductInUseByOffer(ProductEto product) {

    return this.ucFindOffer.isProductInUseByOffer(product);
  }

  @Override
  public void deleteProduct(Long productId) {

    this.ucManageProduct.deleteProduct(productId);
  }

  @Override
  public List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy) {

    return this.ucFindOffer.findOffersFiltered(offerFilterBo, sortBy);
  }

  @Override
  public List<ProductEto> findProductsFiltered(ProductFilter productFilterBo, ProductSortBy sortBy) {

    return this.ucFindProduct.findProductsFiltered(productFilterBo, sortBy);
  }

  @Override
  public BinaryObjectEto findProductPicture(Long productId) {

    return this.ucFindProduct.findProductPicture(productId);
  }

  @Override
  public Blob findProductPictureBlob(Long pictureId) {

    return this.ucFindProduct.findProductPictureBlob(pictureId);
  }

  @Override
  public void updateProductPicture(Long productId, Blob blob, BinaryObjectEto binaryObjectEto) {

    this.ucManageProduct.updateProductPicture(productId, blob, binaryObjectEto);
  }

  @Override
  public void deleteProductPicture(Long productId) {

    this.ucManageProduct.deleteProductPicture(productId);

  }

  @Override
  public ProductEto findProductByRevision(Long id, Number revision) {

    return this.ucFindProduct.findProductByRevision(id, revision);
  }

}
