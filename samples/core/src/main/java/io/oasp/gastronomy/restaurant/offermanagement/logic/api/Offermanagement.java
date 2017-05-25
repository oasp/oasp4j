package io.oasp.gastronomy.restaurant.offermanagement.logic.api;

import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
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
import java.util.List;

import javax.validation.Valid;

/**
 * Interface for OfferManagement.
 *
 */
public interface Offermanagement {

  /**
   * Gets an {@link OfferEto} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getId() offer ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} or {@code null} if no
   *         such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} exists.
   */
  OfferEto findOffer(Long id);

  /**
   * Gets an {@link OfferCto} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getId() offer ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} or {@code null} if no
   *         such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} exists.
   */
  OfferCto findOfferCto(Long id);

  /**
   * @return the {@link List} with all available {@link OfferEto}s.
   */
  List<OfferEto> findAllOffers();

  /**
   * Returns a list of offers matching the search criteria.
   *
   * @param criteria the {@link OfferSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OfferEto}s.
   */
  PaginatedListTo<OfferEto> findOfferEtos(OfferSearchCriteriaTo criteria);

  /**
   * Checks, whether a given {@link ProductEto} is in use by at least one {@link OfferEto}.
   *
   * @param product product to check if it is in use
   * @return {@code true}, if there are no {@link OfferEto offers}, that use the given {@link ProductEto}. {@code false}
   *         otherwise.
   */
  boolean isProductInUseByOffer(ProductEto product);

  /**
   * @param offerFilterBo is the {@link OfferFilter offers filter criteria}
   * @param sortBy is the {@link OfferSortBy} attribute, which defines the sorting.
   *
   * @return the {@link List} with all {@link OfferEto}s that match the {@link OfferFilter} criteria.
   */
  List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy);

  /**
   * Deletes an {@link OfferEto} by its {@link OfferEto#getId() id}.
   *
   * @param offerId is the {@link OfferEto#getId() id} that identifies the {@link OfferEto} to be deleted.
   */
  void deleteOffer(Long offerId);

  /**
   * If no ID is contained creates the {@link OfferEto} for the first time. Else it updates the {@link OfferEto} with
   * given ID. If no {@link OfferEto} with given ID is present, an exception will be thrown.
   *
   * @param offer the {@link OfferEto} to persist.
   * @return the generated/updated offer
   */
  OfferEto saveOffer(@Valid OfferEto offer);

  /**
   * Gets a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product#getId() product ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product} or {@code null} if
   *         no such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product} exists.
   */
  ProductEto findProduct(Long id);

  /**
   * Gets a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product} with a specific revision using its
   * entity identifier and a revision.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product#getId() product ID}.
   * @param revision is the revision of the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product
   *        Product}
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product} or {@code null} if
   *         no such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product} exists.
   */
  ProductEto findProductByRevision(Long id, Number revision);

  /**
   * Gets a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Meal} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Meal#getId() product ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Meal} or {@code null} if no
   *         such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Meal} exists.
   */
  MealEto findMeal(Long id);

  /**
   * Gets a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink#getId() product ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} or {@code null} if no
   *         such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink} exists.
   */
  DrinkEto findDrink(Long id);

  /**
   * Gets a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.SideDish} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.SideDish#getId() product ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.SideDish} or {@code null} if
   *         no such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.SideDish} exists.
   */
  SideDishEto findSideDish(Long id);

  /**
   * @return the {@link List} with all {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product}s.
   */
  List<ProductEto> findAllProducts();

  /**
   * @return the {@link List} with all {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Meal meals}.
   */
  List<MealEto> findAllMeals();

  /**
   * @return the {@link List} with all {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink drinks}.
   */
  List<DrinkEto> findAllDrinks();

  /**
   * @return the {@link List} with all {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.SideDish side
   *         dishes}.
   */
  List<SideDishEto> findAllSideDishes();

  /**
   * Returns the {@link List} of filtered products sorted according to the specification.
   *
   * @param productFilterBo filter specification
   * @param sortBy sorting specification
   * @return a {@link List} of filtered products
   */
  List<ProductEto> findProductsFiltered(ProductFilter productFilterBo, ProductSortBy sortBy);

  /**
   * @param productId the ID of the {@link ProductEto} to get the picture
   * @return the {@link BinaryObjectEto} that contains meta data about the picture
   */
  BinaryObjectEto findProductPicture(Long productId);

  /**
   * Returns a list of products matching the search criteria.
   *
   * @param criteria the {@link ProductSearchCriteriaTo}.
   * @return the {@link List} of matching {@link ProductEto}s.
   */
  PaginatedListTo<ProductEto> findProductEtos(ProductSearchCriteriaTo criteria);

  /**
   * @param productId the ID of the {@link ProductEto} to get the picture data
   * @return the {@link Blob} that contains the data
   */
  Blob findProductPictureBlob(Long productId);

  /**
   * If no ID is contained creates the {@link ProductEto} for the first time. Else it updates the {@link ProductEto}
   * with given ID. If no {@link ProductEto} with given ID is present, an exception will be thrown.
   *
   * @param product the {@link ProductEto} to persist.
   * @return the persisted {@link ProductEto}.
   */
  ProductEto saveProduct(ProductEto product);

  /**
   * Deletes a {@link ProductEto}.
   *
   * @param productId is the ID of the {@link ProductEto} to delete
   */
  void deleteProduct(Long productId);

  /**
   * Updates the picture of the product.
   *
   * @param productId is the ID of the {@link ProductEto} to update the picture
   * @param blob is the binary representation of the picture
   * @param binaryObjectEto is the mimeType of the blob
   */
  void updateProductPicture(Long productId, Blob blob, BinaryObjectEto binaryObjectEto);

  /**
   * Deletes the Picture of the {@link ProductEto}.
   *
   * @param productId is the ID of the {@link ProductEto} to delte the picture
   */
  void deleteProductPicture(Long productId);

}
