package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.sql.Blob;
import java.util.List;

/**
 * Interface of UcFindProduct to centralize documentation and signatures of methods.
 *
 * @author mbrunnli
 * @since dev
 */
public interface UcFindProduct {

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
}
