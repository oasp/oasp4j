#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api;

import ${package}.offermanagement.common.api.Product;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * {@link ${package}.general.persistence.api.ApplicationPersistenceEntity
 * Entity} for {@link Product} .
 * 
 * @author loverbec
 */
@Entity(name = "Product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class ProductEntity extends MenuItemEntity implements Product {

  private static final long serialVersionUID = 1L;

  // TODO oasp/oasp4j${symbol_pound}52
  // private byte[] picture;

  /**
   * The constructor.
   */
  public ProductEntity() {

    super();
  }

}
