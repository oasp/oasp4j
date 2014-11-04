#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api.usecase;

import ${package}.salesmanagement.logic.api.to.BillEto;


/**
 * Interface of {@link ${package}.general.logic.base.AbstractUc use case} to get specific or all
 * {@link BillEto bills}.
 * 
 * @author mvielsac
 */
public interface UcFindBill {

  /**
   * This method will return a {@link BillEto bill} identified the given id.
   * 
   * @param id is the {@link BillEto${symbol_pound}getId() id} of the Bill to fetch.
   * @return the {@link BillEto bill} for the given id.
   */
  BillEto findBill(Long id);
}
