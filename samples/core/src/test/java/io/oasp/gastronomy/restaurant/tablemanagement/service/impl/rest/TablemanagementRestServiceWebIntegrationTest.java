package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.RestraurantTestHelper;
import io.oasp.gastronomy.restaurant.general.common.base.RestaurantWebIntegrationSubsystemTest;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.staffmanagement.service.api.rest.StaffmanagementRestService;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.rest.TablemanagementRestService;

/**
 * This class serves as an example of how to perform a subsystem test (e.g., call a *RestService interface).
 *
 * @author geazzi, jmolinar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@TestPropertySource(properties = "flyway.locations=db/migration")
// @TestPropertySource(properties = { "database.migration.auto=true", "flyway.locations=db/migration" })
public class TablemanagementRestServiceWebIntegrationTest extends RestaurantWebIntegrationSubsystemTest {

  private static Logger LOG = LoggerFactory.getLogger(TablemanagementRestServiceWebIntegrationTest.class);

  @Inject
  private RestraurantTestHelper testHelper;

  @Inject
  private Flyway flyway;

  @Before
  public void init() {

    this.testHelper.resetH2();
  }

  /**
   * This test method serves as an example of how to use {@link RestraurantWebIntegrationSubsystemTest} in practice.
   */
  @Test
  public void testFindTable() {

    String id = "102";
    TablemanagementRestService service = getRestTestClientBuilder().build(TablemanagementRestService.class);
    TableEto table = service.getTable(id);
    assertThat(table).isNotNull();
    assertThat(table.getId()).isEqualTo(Long.parseLong(id));

  }

  @Test
  public void testSaveTable() {

    // setup

    TableEto eto = new TableEto();
    StaffmanagementRestService staffmanagementRestService =
        getRestTestClientBuilder().build(StaffmanagementRestService.class);
    StaffMemberEto staff = staffmanagementRestService.getStaffMember("waiter");
    assertThat(staff).isNotNull();
    eto.setNumber(312L);
    eto.setState(TableState.FREE);
    eto.setWaiterId(staff.getId());
    TablemanagementRestService service = getRestTestClientBuilder().build(TablemanagementRestService.class);

    // exercise
    TableEto resultEto = service.saveTable(eto);
    // verify
    assertThat(eto.getId()).isNull();
    assertThat(resultEto).isNotNull();
    assertThat(resultEto.getId()).isNotNull();
  }

}
