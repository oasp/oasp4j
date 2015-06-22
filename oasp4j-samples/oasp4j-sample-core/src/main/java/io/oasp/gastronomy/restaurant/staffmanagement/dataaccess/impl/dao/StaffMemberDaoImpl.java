package io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.dao.StaffMemberDao;

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

  @Override
  public Class<StaffMemberEntity> getEntityClass() {

    return StaffMemberEntity.class;
  }

  @Override
  public StaffMemberEntity findByLogin(String login) {

    TypedQuery<StaffMemberEntity> query =
        getEntityManager().createNamedQuery(NamedQueries.GET_STAFF_MEMBER_BY_LOGIN, StaffMemberEntity.class);
    query.setParameter("login", login);
    return query.getSingleResult();
  }
}
