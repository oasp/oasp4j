package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link Product}.
 *
 */
@Entity
@Table(name = "Product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dType", discriminatorType = DiscriminatorType.STRING)
@Audited
public abstract class ProductEntity extends MenuItemEntity implements Product {

  private static final long serialVersionUID = 1L;

  private Long pictureId;

  /**
   * The constructor.
   */
  public ProductEntity() {

    super();
  }

  @Override
  public Long getPictureId() {

    return this.pictureId;
  }

  @Override
  public void setPictureId(Long binaryObjectId) {

    this.pictureId = binaryObjectId;
  }

}
