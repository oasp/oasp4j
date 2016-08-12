package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import java.util.List;

import org.junit.Test;

import io.oasp.gastronomy.restaurant.common.builders.TableEtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.SampleCreator;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.rest.TablemanagementRestService;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * This class serves as an example of how to perform a subsystem test (e.g., call a *RestService interface).
 *
 * @author geazzi, jmolinar, sroeger, shuber
 */

public class TablemanagementRestServiceTest extends AbstractRestServiceTest {

  private TablemanagementRestService service;

  @Override
  public void doSetUp() {

    super.doSetUp();
    this.service = getRestTestClientBuilder().build(TablemanagementRestService.class);

  }

  @Override
  public void doTearDown() {

    super.doTearDown();
    this.service = null;

  }

  /**
   * This test method serves as an example of how to use {@link AbstractRestServiceTest} in practice.
   */
  @Test
  public void testFindTable() {

    TableEto table = this.service.getTable(SampleCreator.SAMPLE_TABLE_ID);
    assertThat(table).isNotNull();
    assertThat(table.getId()).isEqualTo(SampleCreator.SAMPLE_TABLE_ID);
    setDbNeedsReset(false);

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
    assertThat(this.service.getTable(SampleCreator.SAMPLE_TABLE_ID)).isNotNull();

    // when
    this.service.deleteTable(SampleCreator.SAMPLE_TABLE_ID);

    // then
    assertThat(this.service.getTable(SampleCreator.SAMPLE_TABLE_ID)).isNull();

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
    TableEto table = SampleCreator.createSampleTableEto();
    assertThat(table.getId()).isNull();

    // when
    TableEto savedTable = this.service.saveTable(table);

    // then
    assertThat(savedTable).isNotNull();
    assertThat(savedTable.getId()).isNotNull();
    assertThat(savedTable.getState()).isEqualTo(TableState.FREE);
    assertThat(savedTable.getNumber()).isEqualTo(SampleCreator.NEW_TABLE_NUMBER);
    assertThat(savedTable.getWaiterId()).isEqualTo(SampleCreator.SAMPLE_WAITER_ID);
  }

  /**
   * This test method demonstrates a simple usage of {@link TableSearchCriteriaTo} for searching a table by post with
   * {@link TableState} {@code RESERVED} that is created prior to the search job.
   */
  @Test
  public void testFindTablesByPost() {

    // given
    TableEto table = SampleCreator.createSampleTableEto();
    table.setState(TableState.RESERVED);
    assertThat(table).isNotNull();
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
    assertThat(result).extracting("waiterId").contains(SampleCreator.SAMPLE_WAITER_ID);
    assertThat(result).extracting("number").contains(SampleCreator.NEW_TABLE_NUMBER);
  }

}
