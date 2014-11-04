#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.persistence.impl.dao;

import ${package}.general.common.api.constants.NamedQueries;
import ${package}.general.persistence.base.dao.ApplicationMasterDataDaoImpl;
import ${package}.staffmanagement.persistence.api.StaffMemberEntity;
import ${package}.staffmanagement.persistence.api.dao.StaffMemberDao;

import javax.inject.Named;
import javax.persistence.TypedQuery;

/**
 * Implementation of {@link StaffMemberDao}.
 *
 * @author jozitz
 */
@Named
public class StaffMemberDaoImpl extends ApplicationMasterDataDaoImpl<StaffMemberEntity> implements StaffMemberDao {

  /**
   * The constructor.
   */
  public StaffMemberDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<StaffMemberEntity> getEntityClass() {

    return StaffMemberEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StaffMemberEntity findByLogin(String login) {

    TypedQuery<StaffMemberEntity> query =
        getEntityManager().createNamedQuery(NamedQueries.GET_STAFF_MEMBER_BY_LOGIN, StaffMemberEntity.class);
    query.setParameter(1, login);
    return query.getSingleResult();
  }
}
