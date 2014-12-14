package io.oasp.gastronomy.restaurant.tablemanagement.logic.base.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.dao.TableDao;

import javax.inject.Inject;

/**
 *
 * @author jozitz
 */
public abstract class AbstractTableUc extends AbstractUc {

  /** @see #getTableDao() */
  private TableDao tableDao;

  /**
   * @return the {@link TableDao} instance.
   */
  public TableDao getTableDao() {

    return this.tableDao;
  }

  /**
   * @param tableDao the {@link TableDao} to {@link Inject}.
   */
  @Inject
  public void setTableDao(TableDao tableDao) {

    this.tableDao = tableDao;
  }

}
