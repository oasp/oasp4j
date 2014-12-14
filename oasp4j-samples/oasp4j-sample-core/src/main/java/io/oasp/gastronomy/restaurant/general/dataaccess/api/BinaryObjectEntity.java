package io.oasp.gastronomy.restaurant.general.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.common.api.BinaryObject;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * {@link ApplicationPersistenceEntity Entity} for {@link BinaryObject}. Contains the actual {@link Blob}.
 *
 * @author sspielma
 */
@Entity(name = "BinaryObject")
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMimeType(String mimeType) {

    this.mimeType = mimeType;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMimeType() {

    return this.mimeType;
  }

  /**
   * @return data
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public long getSize() {

    return this.size;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(long size) {

    this.size = size;
  }
}
