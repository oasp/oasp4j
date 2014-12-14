package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.ProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageProduct;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractProductUc;

import java.sql.Blob;
import java.util.Objects;

import javax.inject.Named;

/**
 * Implementation of {@link UcManageProduct}.
 *
 * @author loverbec
 */
@Named
public class UcManageProductImpl extends AbstractProductUc implements UcManageProduct {

  /**
   * {@inheritDoc}
   */
  @Override
  public ProductEto saveProduct(ProductEto product) {

    Objects.requireNonNull(product, "product");

    ProductEntity persistedProduct = getProductDao().save(getBeanMapper().map(product, ProductEntity.class));
    return getBeanMapper().map(persistedProduct, ProductEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteProduct(Long productId) {

    getProductDao().delete(productId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateProductPicture(Long productId, Blob blob, BinaryObjectEto binaryObjectEto) {

    ProductEntity product = getProductDao().findOne(productId);
    binaryObjectEto = getUcManageBinaryObject().saveBinaryObject(blob, binaryObjectEto);
    product.setPictureId(binaryObjectEto.getId());
    getProductDao().save(product);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteProductPicture(Long productId) {

    ProductEntity product = getProductDao().findOne(productId);
    getUcManageBinaryObject().deleteBinaryObject(product.getPictureId());

  }

}
