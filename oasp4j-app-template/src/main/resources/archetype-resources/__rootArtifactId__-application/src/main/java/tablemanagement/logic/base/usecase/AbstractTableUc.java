#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.logic.base.usecase;

import ${package}.general.logic.base.AbstractUc;
import ${package}.tablemanagement.persistence.api.dao.TableDao;

import javax.inject.Inject;

/**
 *
 * @author jozitz
 */
public abstract class AbstractTableUc extends AbstractUc {

  /** @see ${symbol_pound}getTableDao() */
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
