#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.logic.impl.usecase;

import ${package}.staffmanagement.logic.api.to.StaffMemberEto;
import ${package}.staffmanagement.logic.api.usecase.UcFindStaffMember;
import ${package}.staffmanagement.logic.base.usecase.AbstractStaffMemberUc;
import ${package}.staffmanagement.persistence.api.StaffMemberEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/**
 * Implementation of {@link UcFindStaffMember}.
 *
 * @author jozitz
 */
@Named
public class UcFindStaffMemberImpl extends AbstractStaffMemberUc implements UcFindStaffMember {

  /**
   * The constructor.
   */
  public UcFindStaffMemberImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StaffMemberEto findStaffMemberByLogin(String login) {

    return getBeanMapper().map(getStaffMemberDao().findByLogin(login), StaffMemberEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StaffMemberEto findStaffMember(Long id) {

    return getBeanMapper().map(getStaffMemberDao().find(id), StaffMemberEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<StaffMemberEto> findAllStaffMembers() {

    List<StaffMemberEntity> members = getStaffMemberDao().findAll();
    List<StaffMemberEto> membersBo = new ArrayList<>();

    for (StaffMemberEntity member : members) {
      membersBo.add(getBeanMapper().map(member, StaffMemberEto.class));
    }

    return membersBo;
  }

}
