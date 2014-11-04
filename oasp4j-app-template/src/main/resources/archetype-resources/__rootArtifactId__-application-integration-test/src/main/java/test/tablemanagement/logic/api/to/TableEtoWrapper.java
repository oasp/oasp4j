#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.tablemanagement.logic.api.to;

import ${package}.tablemanagement.logic.api.to.TableEto;
import ${package}.test.general.common.api.to.AbstractEtoWrapper;

import javax.ws.rs.core.Response;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class TableEtoWrapper extends AbstractEtoWrapper {

  private TableEto tableEto;

  public TableEtoWrapper(TableEto tableEto, Response response, String jsonSting) {

    super(response, jsonSting);
    this.tableEto = tableEto;

  }

  /**
   * @return tableEto
   */
  public TableEto getTableEto() {

    return this.tableEto;
  }

  /**
   * @param tableEto the tableEto to set
   */
  public void setTableEto(TableEto tableEto) {

    this.tableEto = tableEto;
  }

}
