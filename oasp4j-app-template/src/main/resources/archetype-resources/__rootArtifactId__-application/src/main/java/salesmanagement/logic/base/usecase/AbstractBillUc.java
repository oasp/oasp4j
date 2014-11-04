#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.base.usecase;

import ${package}.general.logic.base.AbstractUc;
import ${package}.salesmanagement.persistence.api.dao.BillDao;

import javax.inject.Inject;

/**
 *
 * @author mbrunnli
 */
public abstract class AbstractBillUc extends AbstractUc {

  /** @see ${symbol_pound}getBillDao() */
  private BillDao billDao;

  /**
   * @return the {@link BillDao} instance.
   */
  public BillDao getBillDao() {

    return this.billDao;
  }

  /**
   * Sets the field 'billDao'.
   *
   * @param billDao New value for billDao
   */
  @Inject
  public void setBillDao(BillDao billDao) {

    this.billDao = billDao;
  }

}
