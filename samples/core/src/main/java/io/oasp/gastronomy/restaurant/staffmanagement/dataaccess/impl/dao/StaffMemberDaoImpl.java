package io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.impl.dao;

import static com.querydsl.core.alias.Alias.$;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.dao.StaffMemberDao;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.base.LegacyDaoQuerySupport;

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

    TypedQuery<StaffMemberEntity> query = getEntityManager().createNamedQuery(NamedQueries.GET_STAFF_MEMBER_BY_LOGIN,
        StaffMemberEntity.class);
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
    JPAQuery<StaffMemberEntity> query = new JPAQuery<StaffMemberEntity>(getEntityManager()).from(alias);

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

    return LegacyDaoQuerySupport.findPaginated(criteria, query);
  }
}
