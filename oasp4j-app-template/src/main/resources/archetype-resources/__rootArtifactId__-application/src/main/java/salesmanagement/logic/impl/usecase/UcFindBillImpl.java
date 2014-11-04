#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.impl.usecase;

import ${package}.salesmanagement.logic.api.to.BillEto;
import ${package}.salesmanagement.logic.api.usecase.UcFindBill;
import ${package}.salesmanagement.logic.base.usecase.AbstractBillUc;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcFindBill}.
 *
 * @author jozitz
 */
@Named
public class UcFindBillImpl extends AbstractBillUc implements UcFindBill {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindBillImpl.class);

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public BillEto findBill(Long id) {

    LOG.debug("Get Bill with id '" + id + "' from database.");
    return getBeanMapper().map(getBillDao().findOne(id), BillEto.class);
  }

}
