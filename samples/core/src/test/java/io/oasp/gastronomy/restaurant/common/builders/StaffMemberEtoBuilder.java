package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;

public class StaffMemberEtoBuilder {

  private List<P<StaffMemberEto>> parameterToBeApplied;

  public StaffMemberEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<P<StaffMemberEto>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

  public StaffMemberEtoBuilder name(final String name) {

    this.parameterToBeApplied.add(new P<StaffMemberEto>() {
      @Override
      public void apply(StaffMemberEto target) {

        target.setName(name);
      }
    });
    return this;
  }

  public StaffMemberEtoBuilder firstName(final String firstName) {

    this.parameterToBeApplied.add(new P<StaffMemberEto>() {
      @Override
      public void apply(StaffMemberEto target) {

        target.setFirstName(firstName);
      }
    });
    return this;
  }

  public StaffMemberEtoBuilder lastName(final String lastName) {

    this.parameterToBeApplied.add(new P<StaffMemberEto>() {
      @Override
      public void apply(StaffMemberEto target) {

        target.setLastName(lastName);
      }
    });
    return this;
  }

  public StaffMemberEtoBuilder role(final Role role) {

    this.parameterToBeApplied.add(new P<StaffMemberEto>() {
      @Override
      public void apply(StaffMemberEto target) {

        target.setRole(role);
      }
    });
    return this;
  }

  public StaffMemberEtoBuilder language(final Locale language) {

    this.parameterToBeApplied.add(new P<StaffMemberEto>() {
      @Override
      public void apply(StaffMemberEto target) {

        target.setLanguage(language);
      }
    });
    return this;
  }

  public StaffMemberEtoBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<StaffMemberEto>() {
      @Override
      public void apply(StaffMemberEto target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  public StaffMemberEto createNew() {

    StaffMemberEto staffmembereto = new StaffMemberEto();
    // default values
    staffmembereto.setFirstName("defaultFirstName");
    staffmembereto.setLastName("defaultLastName");
    staffmembereto.setRole(Role.WAITER);

    for (P<StaffMemberEto> parameter : this.parameterToBeApplied) {
      parameter.apply(staffmembereto);
    }
    return staffmembereto;
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

}
