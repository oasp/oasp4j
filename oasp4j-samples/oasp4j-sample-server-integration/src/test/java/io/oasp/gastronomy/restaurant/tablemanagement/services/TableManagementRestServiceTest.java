package io.oasp.gastronomy.restaurant.tablemanagement.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import io.oasp.gastronomy.restaurant.general.common.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.common.builders.TableEntityBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.common.builders.TableEtoBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.RestUrls;
import io.oasp.gastronomy.restaurant.test.general.TestData;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test table management rest service
 *
 * @author hahmad, arklos, mbrunnli
 */
public class TableManagementRestServiceTest extends AbstractRestServiceTest {

  /**
   * Simple crud test for tablemanagement.
   */
  @Test
  public void crudTableTest() throws Exception {

    // get all tables

    List<ResponseData<TableEto>> tables =
        this.waiter.getAll(RestUrls.TableManagement.getAllTablesUrl(), TableEto.class);

    // get unused table number
    List<Long> tableNumbers = new ArrayList<>();
    for (ResponseData<TableEto> responseData : tables) {
      tableNumbers.add(responseData.getResponseObject().getNumber());
    }
    Long number = 1l;
    while (tableNumbers.contains(number)) {
      number += 1;
    }
    tableNumbers.add(number);

    // create a new table
    TableEto createdTable =
        this.waiter.post(TestData.createTable(null, number, 0, TableState.FREE),
            RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);
    // get the created table
    ResponseData<TableEto> table =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(createdTable.getId()), TableEto.class);

    Assert.assertNotNull(table.getResponseObject());
    Assert.assertEquals(createdTable.getNumber(), table.getResponseObject().getNumber());
    Assert.assertEquals(createdTable.getState(), table.getResponseObject().getState());

    // update table
    TableState newState = TableState.OCCUPIED;
    createdTable.setState(newState);
    Long newNumber = createdTable.getNumber() + 1l;
    while (tableNumbers.contains(newNumber)) {
      newNumber += 1;
    }
    createdTable.setNumber(newNumber);
    TableEto updatedTable =
        this.waiter.post(createdTable, RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);

    // Assert.assertEquals(newState, updatedTable.getState());
    Assert.assertEquals(newNumber, updatedTable.getNumber());

    // delete created table
    Response deleteResponse = this.chief.delete(RestUrls.TableManagement.getDeleteTableUrl(createdTable.getId()));
    Assert.assertEquals(deleteResponse.getStatus(), 204);

    // check if table is deleted
    ResponseData<TableEto> getDeletedTableResponse =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(createdTable.getId()), TableEto.class);
    Assert.assertNull(getDeletedTableResponse.getResponseObject());
  }

  /**
   * Test get table
   *
   * @throws Exception test fails
   */
  @Test
  public void getTableTest() throws Exception {

    TableEntity tableEntity =
        new TableEntityBuilder().number(10l).state(TableState.RESERVED)
            .persist(this.transactionTemplate, TableManagementRestServiceTest.this.em);

    ResponseData<TableEto> table =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);

    Assert.assertNotNull(table.getResponseObject());
    Assert.assertEquals(tableEntity.getNumber(), table.getResponseObject().getNumber());
    Assert.assertEquals(tableEntity.getState(), table.getResponseObject().getState());

  }

  /**
   * Test get all tables
   */
  @Test
  public void getAllTablesTest() {

    List<TableEntity> tableEntities =
        new TableEntityBuilder().persistAndDuplicate(TableManagementRestServiceTest.this.transactionTemplate,
            TableManagementRestServiceTest.this.em, 5);

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getAllTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(tableEntities.size(), tableEtos.size());
  }

  /**
   * Test get all free tables
   */
  @Test
  public void getFreeTablesTest() {

    new TableEntityBuilder().state(TableState.OCCUPIED).persistAndDuplicate(
        TableManagementRestServiceTest.this.transactionTemplate, TableManagementRestServiceTest.this.em, 5);
    new TableEntityBuilder()
        .number(100l)
        .state(TableState.FREE)
        .persistAndDuplicate(TableManagementRestServiceTest.this.transactionTemplate,
            TableManagementRestServiceTest.this.em, 2);
    new TableEntityBuilder()
        .number(200l)
        .state(TableState.RESERVED)
        .persistAndDuplicate(TableManagementRestServiceTest.this.transactionTemplate,
            TableManagementRestServiceTest.this.em, 2);

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getFreeTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(2, tableEtos.size());
  }

  /**
   * Test mark table as
   */
  @Test
  public void markTableAsTest() {

    TableEntity tableEntity =
        new TableEntityBuilder().state(TableState.RESERVED).persist(this.transactionTemplate, this.em);

    this.waiter.post(RestUrls.TableManagement.markTableAsUrl(tableEntity.getId(), TableState.OCCUPIED));
    ResponseData<TableEto> table =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);
    assertThat(table.getResponse().getStatus(), is(200));
    assertThat(table.getResponseObject().getState(), is(TableState.OCCUPIED));

    this.waiter.post(RestUrls.TableManagement.markTableAsUrl(tableEntity.getId(), TableState.FREE));
    table = this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);
    assertThat(table.getResponse().getStatus(), is(200));
    assertThat(table.getResponseObject().getState(), is(TableState.FREE));
  }

  /**
   * Test create table
   */
  @Test
  public void createTableTest() {

    this.chief.post(RestUrls.TableManagement.getCreateTableUrl(),
        new TableEtoBuilder().number(10l).state(TableState.RESERVED).createNew());

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getAllTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(1, tableEtos.size());
    TableEto result = tableEtos.get(0).getResponseObject();
    Assert.assertNotNull(result);
    Assert.assertEquals(new Long(10), result.getNumber());
    Assert.assertEquals(TableState.RESERVED, result.getState());
  }

  /**
   * Test delete table
   */
  @Test
  public void deleteTableTest() {

    TableEntity tableToBeDeleted = new TableEntityBuilder().persist(this.transactionTemplate, this.em);

    this.chief.delete(RestUrls.TableManagement.getDeleteTableUrl(tableToBeDeleted.getId()));

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getAllTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(0, tableEtos.size());
  }

  /**
   * Test is table releasable
   */
  @Test
  public void isTableReleaseable() {

    TableEntity tableTarget =
        new TableEntityBuilder().state(TableState.OCCUPIED).persist(this.transactionTemplate, this.em);

    ResponseData<Boolean> response =
        this.waiter.get(RestUrls.TableManagement.isTableReleasableUrl(tableTarget.getId()), Boolean.class);

    assertThat(response.getResponseObject(), is(true));
  }

}
