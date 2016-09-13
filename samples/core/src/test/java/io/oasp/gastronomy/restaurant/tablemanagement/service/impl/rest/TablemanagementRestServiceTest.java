package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import static io.oasp.gastronomy.restaurant.staffmanagement.common.StaffmanagementTestDataConstants.ID_WAITER;
import static io.oasp.gastronomy.restaurant.tablemanagement.common.TablemanagementTestDataConstants.ID_TABLE;
import static io.oasp.gastronomy.restaurant.tablemanagement.common.TablemanagementTestDataConstants.NUMBER_TABLE_NEW;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.common.builders.TableEtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.rest.TablemanagementRestService;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * This class serves as an example of how to perform a subsystem test (e.g., call a *RestService interface).
 *
 * @author geazzi, jmolinar, sroeger
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement" })
// , locations = {"file:src/test/resources/config" })

public class TablemanagementRestServiceTest extends AbstractRestServiceTest {

  private TablemanagementRestService service;

  /**
   * Provides initialization previous to the creation of the text fixture.
   */
  @Before
  public void init() {

    getDbTestHelper().resetDatabase();
    this.service = getRestTestClientBuilder().build(TablemanagementRestService.class);

  }

  /**
   * Provides clean up after tests.
   */
  @After
  public void clean() {

    this.service = null;

  }

  /**
   * This test method serves as an example of how to use {@link AbstractRestServiceTest} in practice.
   */
  @Test
  public void testFindTable() {

    // when
    TableEto table = this.service.getTable(ID_TABLE);

    // then
    assertThat(table).isNotNull();
    assertThat(table.getId()).isEqualTo(ID_TABLE);

  }

  /**
   * This test method deletes a table. As a waiter (default login defined in application.properties) does not have the
   * permission to do so, a workaround is needed to login as member "chief". Do not try to delete table 101 as is has an
   * attached order and will fail with error code 400.
   */
  @Test
  public void testDeleteTable() {

    // setup
    getRestTestClientBuilder().setUser("chief");
    getRestTestClientBuilder().setPassword("chief");
    this.service = getRestTestClientBuilder().build(TablemanagementRestService.class);

    // given
    assertThat(this.service.getTable(ID_TABLE)).isNotNull();

    // when
    this.service.deleteTable(ID_TABLE);

    // then
    assertThat(this.service.getTable(ID_TABLE)).isNull();

  }

  /**
   * This test method creates a table using {@link TableEtoBuilder} and saves it into the database. As a waiter (default
   * login defined in application.properties) does not have the permission to do so, a workaround is needed to login as
   * member "chief".
   */
  @Test
  public void testSaveTable() {

    // given
    getRestTestClientBuilder().setUser("chief");
    getRestTestClientBuilder().setPassword("chief");
    this.service = getRestTestClientBuilder().build(TablemanagementRestService.class);
    TableEto table = new TableEtoBuilder().createNew();
    assertThat(table.getId()).isNull();

    // when
    TableEto savedTable = this.service.saveTable(table);

    // then
    assertThat(savedTable).isNotNull();
    assertThat(savedTable.getId()).isNotNull();
    assertThat(savedTable.getState()).isEqualTo(TableState.FREE);
    assertThat(savedTable.getNumber()).isEqualTo(NUMBER_TABLE_NEW);
    assertThat(savedTable.getWaiterId()).isEqualTo(ID_WAITER);
  }

  /**
   * This test method demonstrates a simple usage of {@link TableSearchCriteriaTo} for searching a table by post with
   * {@link TableState} {@code RESERVED} that is created prior to the search job.
   */
  @Test
  public void testFindTablesByPost() {

    // given
    TableEto table = new TableEtoBuilder().state(TableState.RESERVED).createNew();
    TableEto savedTable = this.service.saveTable(table);
    assertThat(savedTable).isNotNull();
    TableSearchCriteriaTo criteria = new TableSearchCriteriaTo();
    assertThat(criteria).isNotNull();
    criteria.setState(TableState.RESERVED);

    // when
    PaginatedListTo<TableEto> tables = this.service.findTablesByPost(criteria);
    List<TableEto> result = tables.getResult();

    // then
    assertThat(result).isNotEmpty();
    assertThat(result).hasAtLeastOneElementOfType(TableEto.class).extracting("state").contains(TableState.RESERVED);
    assertThat(result).extracting("state").doesNotContain(TableState.FREE);
    assertThat(result).extracting("state").doesNotContain(TableState.OCCUPIED);
    assertThat(result).extracting("waiterId").contains(ID_WAITER);
    assertThat(result).extracting("number").contains(NUMBER_TABLE_NEW);
  }

}
