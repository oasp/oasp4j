package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link Product}.
 *
 * @author loverbec
 */
@Entity(name = "Product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class ProductEntity extends MenuItemEntity implements Product {

  private static final long serialVersionUID = 1L;

  private Long pictureId;

  /**
   * The constructor.
   */
  public ProductEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getPictureId() {

    return this.pictureId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPictureId(Long binaryObjectId) {

    this.pictureId = binaryObjectId;
  }

}
