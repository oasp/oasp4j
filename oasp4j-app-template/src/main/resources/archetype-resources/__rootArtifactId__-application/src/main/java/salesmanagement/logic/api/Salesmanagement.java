#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api;

import ${package}.salesmanagement.logic.api.usecase.UcChangeTable;
import ${package}.salesmanagement.logic.api.usecase.UcFindBill;
import ${package}.salesmanagement.logic.api.usecase.UcFindOrder;
import ${package}.salesmanagement.logic.api.usecase.UcFindOrderPosition;
import ${package}.salesmanagement.logic.api.usecase.UcManageBill;
import ${package}.salesmanagement.logic.api.usecase.UcManageOrder;
import ${package}.salesmanagement.logic.api.usecase.UcManageOrderPosition;

/**
 * This is the interface for the {@link Salesmanagement} component of the application core.
 *
 * @author hohwille
 */
public interface Salesmanagement extends UcChangeTable, UcFindBill, UcFindOrder, UcFindOrderPosition, UcManageBill,
    UcManageOrder, UcManageOrderPosition {

}
