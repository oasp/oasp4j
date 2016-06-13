package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api.StaffMemberEntity;

public class StaffMemberEntityBuilder {

  private List<P<StaffMemberEntity>> parameterToBeApplied;

  public StaffMemberEntityBuilder() {

    this.parameterToBeApplied = new LinkedList<P<StaffMemberEntity>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

  public StaffMemberEntityBuilder name(final String name) {

    this.parameterToBeApplied.add(new P<StaffMemberEntity>() {
      @Override
      public void apply(StaffMemberEntity target) {

        target.setName(name);
      }
    });
    return this;
  }

  public StaffMemberEntityBuilder firstName(final String firstName) {

    this.parameterToBeApplied.add(new P<StaffMemberEntity>() {
      @Override
      public void apply(StaffMemberEntity target) {

        target.setFirstName(firstName);
      }
    });
    return this;
  }

  public StaffMemberEntityBuilder lastName(final String lastName) {

    this.parameterToBeApplied.add(new P<StaffMemberEntity>() {
      @Override
      public void apply(StaffMemberEntity target) {

        target.setLastName(lastName);
      }
    });
    return this;
  }

  public StaffMemberEntityBuilder role(final Role role) {

    this.parameterToBeApplied.add(new P<StaffMemberEntity>() {
      @Override
      public void apply(StaffMemberEntity target) {

        target.setRole(role);
      }
    });
    return this;
  }

  public StaffMemberEntityBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<StaffMemberEntity>() {
      @Override
      public void apply(StaffMemberEntity target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  public StaffMemberEntity createNew() {

    StaffMemberEntity staffmemberentity = new StaffMemberEntity();
    for (P<StaffMemberEntity> parameter : this.parameterToBeApplied) {
      parameter.apply(staffmemberentity);
    }
    return staffmemberentity;
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

}
