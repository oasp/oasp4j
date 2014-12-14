package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;


/**
 * Interface of {@link io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc use case} to get specific or all
 * {@link BillEto bills}.
 * 
 * @author mvielsac
 */
public interface UcFindBill {

  /**
   * This method will return a {@link BillEto bill} identified the given id.
   * 
   * @param id is the {@link BillEto#getId() id} of the Bill to fetch.
   * @return the {@link BillEto bill} for the given id.
   */
  BillEto findBill(Long id);
}
