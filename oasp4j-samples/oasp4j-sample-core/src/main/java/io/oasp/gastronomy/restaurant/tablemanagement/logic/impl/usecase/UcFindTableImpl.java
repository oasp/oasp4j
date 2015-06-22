package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcFindTable;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.base.usecase.AbstractTableUc;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcFindTable}.
 *
 * @author jozitz
 */
@Named
@UseCase
public class UcFindTableImpl extends AbstractTableUc implements UcFindTable {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcFindTableImpl.class);

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public TableEto findTable(Long id) {

    LOG.debug("Get table with id '" + id + "' from database.");
    return getBeanMapper().map(getTableDao().findOne(id), TableEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public List<TableEto> findAllTables() {

    LOG.debug("Get all restaurant tables from database.");
    List<TableEntity> tables = getTableDao().findAll();
    return getBeanMapper().mapList(tables, TableEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public List<TableEto> findFreeTables() {

    LOG.debug("Get all free restaurant tables from database.");

    List<TableEntity> tables = getTableDao().getFreeTables();
    return getBeanMapper().mapList(tables, TableEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public PaginatedListTo<TableEto> findTableEtos(TableSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<TableEntity> tables = getTableDao().findTables(criteria);

    return mapPaginatedEntityList(tables, TableEto.class);
  }

}
