#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.logic.api;

import ${package}.staffmanagement.logic.api.usecase.UcFindStaffMember;
import ${package}.staffmanagement.logic.api.usecase.UcManageStaffMember;


/**
 * Interface for StaffManagement component.
 * 
 * @author etomety
 */
public interface Staffmanagement extends UcFindStaffMember, UcManageStaffMember {

}
