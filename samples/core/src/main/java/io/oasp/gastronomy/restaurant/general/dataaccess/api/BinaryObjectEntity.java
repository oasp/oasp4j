package io.oasp.gastronomy.restaurant.general.dataaccess.api;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import io.oasp.gastronomy.restaurant.general.common.api.BinaryObject;

/**
 * {@link ApplicationPersistenceEntity Entity} for {@link BinaryObject}. Contains the actual {@link Blob}.
 *
 */
@Entity
@Table(name = "BinaryObject")
public class BinaryObjectEntity extends ApplicationPersistenceEntity implements BinaryObject {

  private static final long serialVersionUID = 1L;

  private Blob data;

  private String mimeType;

  private long size;

  /**
   * The constructor.
   */
  public BinaryObjectEntity() {

    super();
  }

  @Override
  public void setMimeType(String mimeType) {

    this.mimeType = mimeType;

  }

  @Override
  public String getMimeType() {

    return this.mimeType;
  }

  /**
   * @return data
   */

  /**
   * Remove the following line completely (Type Annotation) in case of database other than PostGres and Uncomment the
   * annotation for @Lob
   */
  // @Type(type = "org.hibernate.type.BinaryType")
  @Lob
  public Blob getData() {

    return this.data;
  }

  /**
   * @param data the data to set
   */
  public void setData(Blob data) {

    this.data = data;
  }

  @Override
  public long getSize() {

    return this.size;
  }

  @Override
  public void setSize(long size) {

    this.size = size;
  }
}
