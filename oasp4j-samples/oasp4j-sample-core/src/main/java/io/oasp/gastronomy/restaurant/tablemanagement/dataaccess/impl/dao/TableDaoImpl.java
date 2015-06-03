package io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.dao.TableDao;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

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

  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedListTo<TableEntity> findTables(TableSearchCriteriaTo criteria) {

    TableEntity table = Alias.alias(TableEntity.class);
    EntityPathBase<TableEntity> alias = Alias.$(table);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);

    Long waiterId = criteria.getWaiterId();
    if (waiterId != null) {
      query.where(Alias.$(table.getWaiterId()).eq(waiterId));
    }
    Long number = criteria.getNumber();
    if (number != null) {
      query.where(Alias.$(table.getNumber()).eq(number));
    }
    TableState state = criteria.getState();
    if (state != null) {
      query.where(Alias.$(table.getState()).eq(state));
    }

    return findPaginated(criteria, query, alias);
  }

}
