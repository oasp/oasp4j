package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.BillDao;

import javax.inject.Named;

/**
 * Implementation of {@link BillDao}.
 *
 * @author jozitz
 */
@Named
public class BillDaoImpl extends ApplicationDaoImpl<BillEntity> implements BillDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<BillEntity> getEntityClass() {

    return BillEntity.class;
  }

}
