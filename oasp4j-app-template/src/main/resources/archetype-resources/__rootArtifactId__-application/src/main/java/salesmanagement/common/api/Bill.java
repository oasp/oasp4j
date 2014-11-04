#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.common.api;

import ${package}.general.common.api.ApplicationEntity;
import ${package}.general.common.api.datatype.Money;

import java.util.List;

/**
 * This is the interface for a {@link Bill}. It represents the actual payment for an {@link Order}.
 *
 * @author hohwille
 */
public interface Bill extends ApplicationEntity {

  /**
   * @return the total amount that has to be payed for this {@link Bill}.
   */
  Money getTotalAmount();

  /**
   * @param totalAmount is the new {@link ${symbol_pound}getTotalAmount() total amount}.
   */
  void setTotalAmount(Money totalAmount);

  /**
   * @return the tip (voluntary payment in addition to {@link ${symbol_pound}getTotalAmount() total amount}).
   */
  Money getTip();

  /**
   * @param tip is the new {@link ${symbol_pound}getTip() tip}.
   */
  void setTip(Money tip);

  /**
   * @return <code>true</code> if this {@link Bill} has been payed, <code>false</code> otherwise.
   */
  boolean isPayed();

  /**
   * @param payed is the new value of {@link ${symbol_pound}isPayed() payed}.
   */
  void setPayed(boolean payed);

  /**
   * @return the {@link List} with the {@link OrderPosition${symbol_pound}getId() IDs} of the {@link OrderPosition}s.
   */
  List<Long> getOrderPositionIds();

  /**
   * @param ids are the new {@link ${symbol_pound}getOrderPositionIds() orderPositionIds}.
   */
  void setOrderPositionIds(List<Long> ids);

}
