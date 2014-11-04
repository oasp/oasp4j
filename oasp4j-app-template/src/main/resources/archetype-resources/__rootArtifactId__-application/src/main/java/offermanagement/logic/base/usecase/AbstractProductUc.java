#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.base.usecase;

import ${package}.general.logic.base.AbstractUc;
import ${package}.offermanagement.persistence.api.dao.ProductDao;

import javax.inject.Inject;

/**
 * Abstract base class for a {@link ${package}.offermanagement.common.api.Product} related use-case.
 *
 * @author jozitz
 */
public abstract class AbstractProductUc extends AbstractUc {

  /** @see ${symbol_pound}setProductDao(ProductDao) */
  private ProductDao productDao;

  /**
   * The constructor.
   */
  public AbstractProductUc() {

    super();
  }

  /**
   * @return productDao
   */
  public ProductDao getProductDao() {

    return this.productDao;
  }

  /**
   * Sets the field 'productDao'.
   *
   * @param productDao New value for productDao
   */
  @Inject
  public void setProductDao(ProductDao productDao) {

    this.productDao = productDao;
  }

}
