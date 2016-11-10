package io.oasp.gastronomy.restaurant.tablemanagement.logic.api;

import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

import javax.validation.Valid;

/**
 * Interface for TableManagement component.
 *
 */
public interface Tablemanagement {

  /**
   * Returns a restaurant table by its id 'id'.
   *
   * @param id The id 'id' of the restaurant table.
   * @return The restaurant {@link TableEto} with id 'id'
   */
  TableEto findTable(Long id);

  /**
   * Returns a list of all existing restaurant tables.
   *
   * @return {@link List} of all existing restaurant {@link TableEto}s
   */
  List<TableEto> findAllTables();

  /**
   * Returns a list of all existing free restaurant tables.
   *
   * @return {@link List} of all existing free restaurant {@link TableEto}s
   */
  List<TableEto> findFreeTables();

  /**
   * Returns a list of restaurant tables matching the search criteria.
   *
   * @param criteria the {@link TableSearchCriteriaTo}.
   * @return the {@link List} of matching {@link TableEto}s.
   */
  PaginatedListTo<TableEto> findTableEtos(TableSearchCriteriaTo criteria);

  /**
   * Deletes a restaurant table from the database by its id 'id'.
   *
   * @param tableId Id of the restaurant table to delete
   */
  void deleteTable(Long tableId);

  /**
   * Creates a new restaurant table and store it in the database.
   *
   * @param table the {@link TableEto} to create.
   * @return the new {@link TableEto} that has been saved with ID and version.
   */
  TableEto saveTable(@Valid TableEto table);

  /**
   * Evaluate if this table could marked as free.
   *
   * @param table {@link TableEto} to be evaluate
   * @return {@code true} if the table could be released<br>
   *         {@code false} , otherwise
   */
  boolean isTableReleasable(@Valid TableEto table);

}
