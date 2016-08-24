package io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationDao;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link TableEntity} entity.
 *
 */
public interface TableDao extends ApplicationDao<TableEntity>, MasterDataDao<TableEntity> {

  /**
   * Returns a list of free restaurant tables.
   *
   * @return {@link List} of free restaurant {@link TableEntity}s
   */
  List<TableEntity> getFreeTables();

  /**
   * Finds the {@link TableEntity orders} matching the given {@link TableSearchCriteriaTo}.
   *
   * @param criteria is the {@link TableSearchCriteriaTo}.
   * @return the {@link List} with the matching {@link TableEntity} objects.
   */
  PaginatedListTo<TableEntity> findTables(TableSearchCriteriaTo criteria);

}
