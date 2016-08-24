package io.oasp.gastronomy.restaurant.general.common.api.constants;

/**
 * Constants of the named queries defined in <code>NamedQueries.xml</code>.
 *
 */
public abstract class NamedQueries {

  // put your query names from NamedQueries.xml as constants here (or generate with cobigen)
  // BEGIN ARCHETYPE SKIP
  /** @see io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.impl.dao.TableDaoImpl#getFreeTables() */
  public static final String GET_FREE_TABLES = "get.free.tables";

  /** @see io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.impl.dao.StaffMemberDaoImpl#findByLogin(String) */
  public static final String GET_STAFF_MEMBER_BY_LOGIN = "get.staff.member.by.login";

  /** @see io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao.OrderPositionDaoImpl */
  public static final String GET_ALL_ORDER_POSITIONS = "get.all.order.positions";

  /** @see io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao.OrderPositionDaoImpl */
  public static final String GET_ALL_OPEN_ORDER_POSITIONS = "get.all.open.order.positions";

  /** @see io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao.OrderPositionDaoImpl */
  public static final String GET_OPEN_ORDER_POSITIONS_FOR_ORDER = "get.open.order.positions.for.order";

  /** @see io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao.OrderDaoImpl */
  public static final String GET_OPEN_ORDER_FOR_TABLE = "get.open.order.for.table";

  /** @see io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao.BillDaoImpl */
  public static final String GET_ALL_IDS_OF_PAYED_BILLS = "get.all.ids.of.payed.bills";
  // END ARCHETYPE SKIP
}
