#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api;

import ${package}.offermanagement.common.api.SideDish;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * {@link ${package}.general.persistence.api.ApplicationPersistenceEntity Entity} for a
 * {@link SideDish}.
 *
 * @author loverbec
 */
@Entity(name = "SideDish")
@DiscriminatorValue("SideDish")
public class SideDishEntity extends ProductEntity implements SideDish {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public SideDishEntity() {

    super();
  }

}
