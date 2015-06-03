package io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

/**
 * Interface of UcFindTable to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcFindTable {

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
}
