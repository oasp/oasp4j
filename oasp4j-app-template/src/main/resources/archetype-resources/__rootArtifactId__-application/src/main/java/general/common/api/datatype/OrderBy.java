#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api.datatype;

/**
 * {@link Enum} for sort order.
 *
 * @author jozitz
 */
public enum OrderBy {

  /** Sort in ascending order. */
  ASC,

  /** Sort in descending order. */
  DESC;

  /**
   * @return <code>true</code>, if {@link OrderBy${symbol_pound}ASC} is set. <code>false</code> otherwise.
   */
  public boolean isAsc() {

    return this == ASC;
  }

  /**
   * @return <code>true</code>, if {@link OrderBy${symbol_pound}DESC} is set. <code>false</code> otherwise.
   */
  public boolean isDesc() {

    return this == DESC;
  }

}
