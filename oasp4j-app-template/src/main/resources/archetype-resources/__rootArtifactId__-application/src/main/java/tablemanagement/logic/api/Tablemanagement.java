#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.logic.api;

import ${package}.tablemanagement.logic.api.usecase.UcFindTable;
import ${package}.tablemanagement.logic.api.usecase.UcManageTable;


/**
 * Interface for TableManagement component.
 * 
 * @author etomety
 */
public interface Tablemanagement extends UcFindTable, UcManageTable {

}
