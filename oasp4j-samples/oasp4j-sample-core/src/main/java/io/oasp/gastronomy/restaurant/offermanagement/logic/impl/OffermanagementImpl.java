package io.oasp.gastronomy.restaurant.offermanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.base.AbstractBeanMapperSupport;
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
import io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase.UcFindOfferImpl;
import io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase.UcFindProductImpl;
import io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase.UcManageOfferImpl;
import io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase.UcManageProductImpl;

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
   * {@inheritDoc}
   */
  @Override
  public OfferEto findOffer(Long id) {

    return this.ucFindOffer.findOffer(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OfferCto findOfferCto(Long id) {

    return this.ucFindOffer.findOfferCto(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OfferEto> findAllOffers() {

    return this.ucFindOffer.findAllOffers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProductEto> findAllProducts() {

    return this.ucFindProduct.findAllProducts();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MealEto> findAllMeals() {

    return this.ucFindProduct.findAllMeals();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DrinkEto> findAllDrinks() {

    return this.ucFindProduct.findAllDrinks();
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public OfferEto saveOffer(OfferEto offer) {

    return this.ucManageOffer.saveOffer(offer);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteOffer(Long offerId) {

    this.ucManageOffer.deleteOffer(offerId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<SideDishEto> findAllSideDishes() {

    return this.ucFindProduct.findAllSideDishes();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProductEto findProduct(Long id) {

    return this.ucFindProduct.findProduct(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MealEto findMeal(Long id) {

    return this.ucFindProduct.findMeal(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DrinkEto findDrink(Long id) {

    return this.ucFindProduct.findDrink(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SideDishEto findSideDish(Long id) {

    return this.ucFindProduct.findSideDish(id);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public ProductEto saveProduct(ProductEto product) {

    return this.ucManageProduct.saveProduct(product);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isProductInUseByOffer(ProductEto product) {

    return this.ucFindOffer.isProductInUseByOffer(product);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public void deleteProduct(Long productId) {

    this.ucManageProduct.deleteProduct(productId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy) {

    return this.ucFindOffer.findOffersFiltered(offerFilterBo, sortBy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProductEto> findProductsFiltered(ProductFilter productFilterBo, ProductSortBy sortBy) {

    return this.ucFindProduct.findProductsFiltered(productFilterBo, sortBy);
  }

  /**
   * Sets the field 'ucFindOffer'.
   *
   * @param ucFindOffer New value for ucFindOffer
   */
  @Inject
  public void setUcFindOffer(UcFindOfferImpl ucFindOffer) {

    this.ucFindOffer = ucFindOffer;
  }

  /**
   * Sets the field 'ucManageOffer'.
   *
   * @param ucManageOffer New value for ucManageOffer
   */
  @Inject
  public void setUcManageOffer(UcManageOfferImpl ucManageOffer) {

    this.ucManageOffer = ucManageOffer;
  }

  /**
   * Sets the field 'ucFindProduct'.
   *
   * @param ucFindProduct New value for ucFindProduct
   */
  @Inject
  public void setUcFindProduct(UcFindProductImpl ucFindProduct) {

    this.ucFindProduct = ucFindProduct;
  }

  /**
   * Sets the field 'ucManageProduct'.
   *
   * @param ucManageProduct New value for ucManageProduct
   */
  @Inject
  public void setUcManageProduct(UcManageProductImpl ucManageProduct) {

    this.ucManageProduct = ucManageProduct;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BinaryObjectEto findProductPicture(Long productId) {

    return this.ucFindProduct.findProductPicture(productId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Blob findProductPictureBlob(Long pictureId) {

    return this.ucFindProduct.findProductPictureBlob(pictureId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateProductPicture(Long productId, Blob blob, BinaryObjectEto binaryObjectEto) {

    this.ucManageProduct.updateProductPicture(productId, blob, binaryObjectEto);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteProductPicture(Long productId) {

    this.ucManageProduct.deleteProductPicture(productId);

  }

}
