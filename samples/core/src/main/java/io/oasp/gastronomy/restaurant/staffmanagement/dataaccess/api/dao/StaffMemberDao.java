package io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationDao;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

/**
 * {@link ApplicationDao Data Access Object} for {@link StaffMemberEntity} entity.
 *
 */
public interface StaffMemberDao extends ApplicationDao<StaffMemberEntity>, MasterDataDao<StaffMemberEntity> {

  /**
   * Searchs the restaurant staff member with identifier 'login' within the database.
   *
   * @param login staff member identifier
   * @return Staffmember
   */
  StaffMemberEntity findByLogin(String login);

  /**
   * Finds the {@link StaffMemberEntity} objects matching the given {@link StaffMemberSearchCriteriaTo}-
   *
   * @param criteria is the {@link StaffMemberSearchCriteriaTo}.
   * @return the {@link PaginatedListTo} with the matching {@link StaffMemberEntity} objects.
   */
  PaginatedListTo<StaffMemberEntity> findStaffMembers(StaffMemberSearchCriteriaTo criteria);

}
