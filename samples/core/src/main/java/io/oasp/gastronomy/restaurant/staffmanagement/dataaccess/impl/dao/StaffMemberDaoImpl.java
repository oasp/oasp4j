package io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.impl.dao;

import static com.mysema.query.alias.Alias.$;
import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.dao.StaffMemberDao;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Implementation of {@link StaffMemberDao}.
 *
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

  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedListTo<StaffMemberEntity> findStaffMembers(StaffMemberSearchCriteriaTo criteria) {

    StaffMemberEntity staffMember = Alias.alias(StaffMemberEntity.class);
    EntityPathBase<StaffMemberEntity> alias = $(staffMember);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);

    String firstName = criteria.getFirstName();
    if (firstName != null) {
      query.where($(staffMember.getFirstName()).eq(firstName));
    }
    String lastName = criteria.getLastName();
    if (lastName != null) {
      query.where($(staffMember.getLastName()).eq(lastName));
    }
    String name = criteria.getName();
    if (name != null) {
      query.where($(staffMember.getName()).eq(name));
    }
    Role role = criteria.getRole();
    if (role != null) {
      query.where($(staffMember.getRole()).eq(role));
    }

    return findPaginated(criteria, query, alias);
  }
}
