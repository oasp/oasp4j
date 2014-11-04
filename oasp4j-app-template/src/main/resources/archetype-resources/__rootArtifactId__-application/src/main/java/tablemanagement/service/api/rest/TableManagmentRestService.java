#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.service.api.rest;

import ${package}.tablemanagement.logic.api.to.TableEto;

/**
 * This is the interface for a service exposing the functionality of the
 * {@link ${package}.tablemanagement.logic.api.Tablemanagement} component.
 * 
 * @author hohwille
 */
public interface TableManagmentRestService {

  /**
   * @see ${package}.tablemanagement.logic.api.Tablemanagement${symbol_pound}findTable(Long)
   * 
   * @param id is the {@link TableEto${symbol_pound}getId() ID}.
   * @return the requested {@link TableEto table}.
   */
  TableEto getTable(Long id);

}
