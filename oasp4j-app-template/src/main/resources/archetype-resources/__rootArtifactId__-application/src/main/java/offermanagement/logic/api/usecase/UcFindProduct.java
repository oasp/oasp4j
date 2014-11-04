#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.api.usecase;

import ${package}.offermanagement.logic.api.to.DrinkEto;
import ${package}.offermanagement.logic.api.to.MealEto;
import ${package}.offermanagement.logic.api.to.ProductEto;
import ${package}.offermanagement.logic.api.to.ProductFilter;
import ${package}.offermanagement.logic.api.to.ProductSortBy;
import ${package}.offermanagement.logic.api.to.SideDishEto;

import java.util.List;

/**
 * Interface for the usecsae to find {@link ProductEto products}.
 *
 * @author mvielsac
 */
public interface UcFindProduct {

  /**
   * Gets a {@link ${package}.offermanagement.common.api.Product} using its entity identifier.
   *
   * @param id is the {@link ${package}.offermanagement.common.api.Product${symbol_pound}getId() product ID}.
   * @return the requested {@link ${package}.offermanagement.common.api.Product} or <code>null</code>
   *         if no such {@link ${package}.offermanagement.common.api.Product} exists.
   */
  ProductEto findProduct(Long id);

  /**
   * Gets a {@link ${package}.offermanagement.common.api.Meal} using its entity identifier.
   *
   * @param id is the {@link ${package}.offermanagement.common.api.Meal${symbol_pound}getId() product ID}.
   * @return the requested {@link ${package}.offermanagement.common.api.Meal} or <code>null</code> if
   *         no such {@link ${package}.offermanagement.common.api.Meal} exists.
   */
  MealEto findMeal(Long id);

  /**
   * Gets a {@link ${package}.offermanagement.common.api.Drink} using its entity identifier.
   *
   * @param id is the {@link ${package}.offermanagement.common.api.Drink${symbol_pound}getId() product ID}.
   * @return the requested {@link ${package}.offermanagement.common.api.Drink} or <code>null</code>
   *         if no such {@link ${package}.offermanagement.common.api.Drink} exists.
   */
  DrinkEto findDrink(Long id);

  /**
   * Gets a {@link ${package}.offermanagement.common.api.SideDish} using its entity identifier.
   *
   * @param id is the {@link ${package}.offermanagement.common.api.SideDish${symbol_pound}getId() product ID}.
   * @return the requested {@link ${package}.offermanagement.common.api.SideDish} or
   *         <code>null</code> if no such {@link ${package}.offermanagement.common.api.SideDish}
   *         exists.
   */
  SideDishEto findSideDish(Long id);

  /**
   * @return the {@link List} with all {@link ${package}.offermanagement.common.api.Product}s.
   */
  List<ProductEto> findAllProducts();

  /**
   * @return the {@link List} with all {@link ${package}.offermanagement.common.api.Meal meals}.
   */
  List<MealEto> findAllMeals();

  /**
   * @return the {@link List} with all {@link ${package}.offermanagement.common.api.Drink drinks}.
   */
  List<DrinkEto> findAllDrinks();

  /**
   * @return the {@link List} with all {@link ${package}.offermanagement.common.api.SideDish side
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
}
