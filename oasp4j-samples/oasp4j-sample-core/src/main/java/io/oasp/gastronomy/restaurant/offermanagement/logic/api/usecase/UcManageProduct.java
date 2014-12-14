package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;

import java.sql.Blob;

/**
 * Interface of UcManageProduct to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageProduct {

  /**
   * If no ID is contained creates the {@link ProductEto} for the first time. Else it updates the {@link ProductEto}
   * with given ID. If no {@link ProductEto} with given ID is present, an exception will be thrown.
   *
   * @param product the {@link ProductEto} to persist.
   * @return the persisted {@link ProductEto}.
   */
  ProductEto saveProduct(ProductEto product);

  /**
   * Deletes a {@link ProductEto}.
   *
   * @param productId is the ID of the {@link ProductEto} to delete
   */
  void deleteProduct(Long productId);

  /**
   * Updates the picture of the product
   *
   * @param productId is the ID of the {@link ProductEto} to update the picture
   * @param blob is the binary representation of the picture
   * @param binaryObjectEto is the mimeType of the blob
   */
  void updateProductPicture(Long productId, Blob blob, BinaryObjectEto binaryObjectEto);

  /**
   * Deletes the Picture of the {@link ProductEto}
   *
   * @param productId is the ID of the {@link ProductEto} to delte the picture
   */
  void deleteProductPicture(Long productId);

}
