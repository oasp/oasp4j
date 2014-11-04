#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.impl.usecase;

import ${package}.offermanagement.common.api.Product;
import ${package}.offermanagement.logic.api.to.ProductEto;
import ${package}.offermanagement.logic.api.usecase.UcManageProduct;
import ${package}.offermanagement.logic.base.usecase.AbstractProductUc;
import ${package}.offermanagement.persistence.api.ProductEntity;

import java.util.Objects;

import javax.inject.Named;

import net.sf.mmm.util.exception.api.DuplicateObjectException;

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
  public void updateProduct(ProductEto product) {

    Objects.requireNonNull(product, "product");

    ProductEntity productEntity = getBeanMapper().map(product, ProductEntity.class);
    getProductDao().save(productEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createProduct(ProductEto product) {

    Objects.requireNonNull(product, "product");

    ProductEntity persistedProduct = getProductDao().findOne(product.getId());
    if (persistedProduct != null) {
      throw new DuplicateObjectException(Product.class, product.getId());
    }
    getProductDao().save(getBeanMapper().map(product, ProductEntity.class));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteProduct(Long productId) {

    getProductDao().delete(productId);
  }

}
