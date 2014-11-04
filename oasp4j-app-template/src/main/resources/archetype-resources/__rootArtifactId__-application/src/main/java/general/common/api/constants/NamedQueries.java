#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api.constants;

/**
 * Constants of the named queries defined in <code>NamedQueries.hbm.xml</code>.
 *
 * @author hohwille
 */
public abstract class NamedQueries {

  /** @see ${package}.tablemanagement.persistence.impl.dao.TableDaoImpl${symbol_pound}getFreeTables() */
  public static final String GET_FREE_TABLES = "get.free.tables";

  /** @see ${package}.staffmanagement.persistence.impl.dao.StaffMemberDaoImpl${symbol_pound}findByLogin(String) */
  public static final String GET_STAFF_MEMBER_BY_LOGIN = "get.staff.member.by.login";

  /** @see ${package}.salesmanagement.persistence.impl.dao.OrderPositionDaoImpl */
  public static final String GET_ALL_ORDER_POSITIONS = "get.all.order.positions";

  /** @see ${package}.salesmanagement.persistence.impl.dao.OrderPositionDaoImpl */
  public static final String GET_ALL_OPEN_ORDER_POSITIONS = "get.all.open.order.positions";

  /** @see ${package}.salesmanagement.persistence.impl.dao.OrderPositionDaoImpl */
  public static final String GET_OPEN_ORDER_POSITIONS_FOR_ORDER = "get.open.order.positions.for.order";

  /** @see ${package}.salesmanagement.persistence.impl.dao.OrderDaoImpl */
  public static final String GET_OPEN_ORDER_FOR_TABLE = "get.open.order.for.table";

}
