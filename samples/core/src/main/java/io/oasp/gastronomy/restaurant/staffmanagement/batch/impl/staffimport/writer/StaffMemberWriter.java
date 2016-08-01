package io.oasp.gastronomy.restaurant.staffmanagement.batch.impl.staffimport.writer;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;

/**
 * StaffMemberWriter is responsible for writing StaffMemberEto to database.
 *
 * @author sroeger
 */
public class StaffMemberWriter implements ItemWriter<StaffMemberEto> {

  private static final Logger LOG = LoggerFactory.getLogger(StaffMemberWriter.class);

  private Staffmanagement staffManagement;

  @Override
  public void write(List<? extends StaffMemberEto> items) throws Exception {

    LOG.debug("Writing " + items.size() + " staffmembers");

    for (StaffMemberEto item : items) {
      LOG.debug("Saving staffmember with login: " + item.getName());
      this.staffManagement.saveStaffMember(item);
    }

  }

  /**
   * @param staffManagement the staffManagement to set
   */
  @Inject
  public void setStaffManagement(Staffmanagement staffManagement) {

    this.staffManagement = staffManagement;
  }

}
