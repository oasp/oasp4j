package io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.dao.TableDao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

/**
 * Implementation of {@link TableDao}.
 *
 * @author hohwille
 */
@Named
public class TableDaoImpl extends ApplicationMasterDataDaoImpl<TableEntity> implements TableDao {

  /**
   * The constructor.
   */
  public TableDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<TableEntity> getEntityClass() {

    return TableEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TableEntity> getFreeTables() {

    Query query = getEntityManager().createNamedQuery(NamedQueries.GET_FREE_TABLES, TableEntity.class);
    return query.getResultList();
  }

}
