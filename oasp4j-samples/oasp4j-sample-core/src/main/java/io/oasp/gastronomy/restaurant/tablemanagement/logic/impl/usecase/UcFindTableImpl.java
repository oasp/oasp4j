package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcFindTable;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.base.usecase.AbstractTableUc;

import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcFindTable}.
 *
 * @author jozitz
 */
@Named
public class UcFindTableImpl extends AbstractTableUc implements UcFindTable {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcFindTableImpl.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public TableEto findTable(Long id) {

    LOG.debug("Get table with id '" + id + "' from database.");
    return getBeanMapper().map(getTableDao().findOne(id), TableEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TableEto> findAllTables() {

    LOG.debug("Get all restaurant tables from database.");
    List<TableEntity> tables = getTableDao().findAll();
    return getBeanMapper().mapList(tables, TableEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TableEto> findFreeTables() {

    LOG.debug("Get all free restaurant tables from database.");

    List<TableEntity> tables = getTableDao().getFreeTables();
    return getBeanMapper().mapList(tables, TableEto.class);
  }

}
