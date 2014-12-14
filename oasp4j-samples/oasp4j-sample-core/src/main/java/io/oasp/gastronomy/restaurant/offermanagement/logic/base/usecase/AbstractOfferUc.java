package io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;

import javax.inject.Inject;

/**
 * Abstract base class for a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} related use-case.
 *
 * @author jozitz
 */
public abstract class AbstractOfferUc extends AbstractUc {

  /** @see #getOfferDao() */
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
