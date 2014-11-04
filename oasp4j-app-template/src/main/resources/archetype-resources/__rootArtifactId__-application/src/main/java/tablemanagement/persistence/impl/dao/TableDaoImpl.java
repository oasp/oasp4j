#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.persistence.impl.dao;

import ${package}.general.common.api.constants.NamedQueries;
import ${package}.general.persistence.base.dao.ApplicationMasterDataDaoImpl;
import ${package}.tablemanagement.persistence.api.TableEntity;
import ${package}.tablemanagement.persistence.api.dao.TableDao;

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
