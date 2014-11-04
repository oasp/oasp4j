#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.impl;

import ${package}.general.common.base.AbstractBeanMapperSupport;
import ${package}.offermanagement.logic.api.Offermanagement;
import ${package}.offermanagement.logic.api.to.DrinkEto;
import ${package}.offermanagement.logic.api.to.MealEto;
import ${package}.offermanagement.logic.api.to.OfferCto;
import ${package}.offermanagement.logic.api.to.OfferEto;
import ${package}.offermanagement.logic.api.to.OfferFilter;
import ${package}.offermanagement.logic.api.to.OfferSortBy;
import ${package}.offermanagement.logic.api.to.ProductEto;
import ${package}.offermanagement.logic.api.to.ProductFilter;
import ${package}.offermanagement.logic.api.to.ProductSortBy;
import ${package}.offermanagement.logic.api.to.SideDishEto;
import ${package}.offermanagement.logic.api.usecase.UcFindOffer;
import ${package}.offermanagement.logic.api.usecase.UcFindProduct;
import ${package}.offermanagement.logic.api.usecase.UcManageOffer;
import ${package}.offermanagement.logic.api.usecase.UcManageProduct;
import ${package}.offermanagement.logic.impl.usecase.UcFindOfferImpl;
import ${package}.offermanagement.logic.impl.usecase.UcFindProductImpl;
import ${package}.offermanagement.logic.impl.usecase.UcManageOfferImpl;
import ${package}.offermanagement.logic.impl.usecase.UcManageProductImpl;

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

  public void createOffer(OfferEto offer) {

    this.ucManageOffer.createOffer(offer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateOffer(OfferEto offer) {

    this.ucManageOffer.updateOffer(offer);
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
  public void createProduct(ProductEto product) {

    this.ucManageProduct.createProduct(product);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public void updateProduct(ProductEto product) {

    this.ucManageProduct.updateProduct(product);
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

}
