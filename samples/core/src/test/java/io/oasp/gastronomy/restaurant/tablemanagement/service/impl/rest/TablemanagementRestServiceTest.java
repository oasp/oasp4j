package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static Logger LOG = LoggerFactory.getLogger(TablemanagementRestServiceTest.class);

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

    String id = "102";
    TableEto table = this.service.getTable(id);
    assertThat(table).isNotNull();
    assertThat(table.getId()).isEqualTo(Long.parseLong(id));

  }

  /**
   * This test method deletes a table. As a waiter (default login defined in application.properties) does not have the
   * permission to do so, a workaround is needed to login as member "chief". Do not try to delete table 101 as is has an
   * attached order and will fail with error code 400.
   */
  @Test
  public void testDeleteTable() {

    getRestTestClientBuilder().setUser("chief");
    getRestTestClientBuilder().setPassword("chief");
    this.service = getRestTestClientBuilder().build(TablemanagementRestService.class);

    int deleteTableNumber = 102;

    // initial state - 5 entries
    List<TableEto> tableListBeforeDelete = this.service.getAllTables();
    int numberOfTables = tableListBeforeDelete.size();

    this.service.deleteTable(deleteTableNumber);
    TableEto table = this.service.getTable(Integer.toString(deleteTableNumber));
    assertThat(table).isNull();

    // final state - 4 entries
    List<TableEto> tableListAfterDelete = this.service.getAllTables();
    assertThat(tableListAfterDelete).hasSize(numberOfTables - 1);
  }

  /**
   * This test method creates a table using {@link TableEtoBuilder} and saves it into the database. As a waiter (default
   * login defined in application.properties) does not have the permission to do so, a workaround is needed to login as
   * member "chief".
   */
  @Test
  public void testSaveTable() {

    getRestTestClientBuilder().setUser("chief");
    getRestTestClientBuilder().setPassword("chief");
    this.service = getRestTestClientBuilder().build(TablemanagementRestService.class);

    TableEto table = new TableEtoBuilder().number(7L).state(TableState.FREE).waiterId(2L).createNew();

    assertThat(table.getId()).isNull();

    table = this.service.saveTable(table);
  }

  /**
   * This test method demonstrates a simple usage of {@link TableSearchCriteriaTo} for searching a table by post.
   */
  @Test
  public void testFindTablesByPost() {

    TableSearchCriteriaTo criteria = new TableSearchCriteriaTo();
    criteria.setState(TableState.FREE);

    PaginatedListTo<TableEto> tables = this.service.findTablesByPost(criteria);
    List<TableEto> result = tables.getResult();
    assertThat(result).isNotEmpty();
  }

}
