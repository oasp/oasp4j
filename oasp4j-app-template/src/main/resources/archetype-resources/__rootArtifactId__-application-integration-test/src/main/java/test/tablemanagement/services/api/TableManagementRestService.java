#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.tablemanagement.services.api;

import ${package}.tablemanagement.common.api.datatype.TableState;
import ${package}.test.tablemanagement.logic.api.to.TableEtoWrapper;

import java.util.List;

import javax.ws.rs.core.Response;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public interface TableManagementRestService {

  public TableEtoWrapper getTable(Long id);

  public List<TableEtoWrapper> getAllTables();

  public TableEtoWrapper createTable(TableEtoWrapper table);

  public Response deleteTable(Long id);

  public List<TableEtoWrapper> getFreeTables();

  public Response markTableAs(Long id, TableState newState);

  public boolean isTableReleasable(Long id);

}
