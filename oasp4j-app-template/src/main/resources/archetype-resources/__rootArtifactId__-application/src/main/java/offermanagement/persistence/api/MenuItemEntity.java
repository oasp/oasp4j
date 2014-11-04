#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api;

import ${package}.general.persistence.api.ApplicationPersistenceEntity;
import ${package}.offermanagement.common.api.MenuItem;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author loverbec
 */
@MappedSuperclass
public abstract class MenuItemEntity extends ApplicationPersistenceEntity implements MenuItem {

  private static final long serialVersionUID = 1L;

  private String name;

  private String description;

  /**
   * The constructor.
   */
  public MenuItemEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Column(name = "name"/* , unique = true */)
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setName(String name) {

    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Column(name = "description")
  @Override
  public String getDescription() {

    return this.description;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescription(String description) {

    this.description = description;
  }

}
