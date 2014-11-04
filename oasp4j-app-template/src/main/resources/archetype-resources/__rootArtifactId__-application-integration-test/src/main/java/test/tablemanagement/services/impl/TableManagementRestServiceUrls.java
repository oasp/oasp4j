#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.tablemanagement.services.impl;

import ${package}.tablemanagement.common.api.datatype.TableState;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class TableManagementRestServiceUrls {

  public final static String tablemanagementUrl = "/services/rest/tablemanagement";

  public final static String getFreeTablesUrl = tablemanagementUrl + "/freetables";

  public final static String getAllTablesUrl = tablemanagementUrl + "/table";

  public final static String createTableUrl = getAllTablesUrl;

  public final static String getTableUrl(Long id) {

    return tablemanagementUrl + "/table/" + id;
  }

  public final static String deleteTableUrl(Long id) {

    return getTableUrl(id);
  }

  public final static String markTableAsUrl(Long id, TableState newState) {

    return tablemanagementUrl + "/table/" + id + "/marktableas" + newState;
  }

  public final static String isTableReleasable(Long id) {

    return tablemanagementUrl + "table/" + id + "/istablereleasable/";

  }

}
