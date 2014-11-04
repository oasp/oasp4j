#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.api.usecase;

import ${package}.salesmanagement.logic.api.to.OrderEto;
import ${package}.tablemanagement.logic.api.to.TableEto;

/**
 * Interface of {@link ${package}.general.logic.base.AbstractUc use case} to
 * {@link ${symbol_pound}changeTable(OrderEto, TableEto) change the table}.
 *
 * Interface of UcChangeTable to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcChangeTable {

  /**
   * UseCase to change from one {@link TableEto table} to another. The people sitting at a table are identified by their
   * {@link OrderEto order} that has to be provided as argument.
   *
   * @param order the {@link OrderEto order}
   * @param newTable the new {@link TableEto table} to switch to.
   */
  void changeTable(OrderEto order, TableEto newTable);

}
