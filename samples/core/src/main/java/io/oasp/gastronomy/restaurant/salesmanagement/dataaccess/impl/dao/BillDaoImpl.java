package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import javax.inject.Named;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.BillDao;

/**
 * Implementation of {@link BillDao}.
 *
 * @author jozitz
 */
@Named
public class BillDaoImpl extends ApplicationDaoImpl<BillEntity> implements BillDao {

  @Override
  public Class<BillEntity> getEntityClass() {

    return BillEntity.class;
  }

}
