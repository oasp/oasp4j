#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.base.usecase;

import ${package}.general.logic.base.AbstractUc;
import ${package}.offermanagement.persistence.api.dao.OfferDao;

import javax.inject.Inject;

/**
 * Abstract base class for a {@link ${package}.offermanagement.common.api.Offer} related use-case.
 *
 * @author jozitz
 */
public abstract class AbstractOfferUc extends AbstractUc {

  /** @see ${symbol_pound}getOfferDao() */
  private OfferDao offerDao;

  /**
   * @return {@link OfferDao} instance.
   */
  public OfferDao getOfferDao() {

    return this.offerDao;
  }

  /**
   * Sets the field 'offerDao'.
   *
   * @param offerDao New value for offerDao
   */
  @Inject
  public void setOfferDao(OfferDao offerDao) {

    this.offerDao = offerDao;
  }

}
