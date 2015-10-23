package io.oasp.gastronomy.restaurant.tablemanagement.services;

import io.oasp.gastronomy.restaurant.general.common.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.common.builders.TableEntityBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.common.builders.TableEtoBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.test.config.RestUrls;
import io.oasp.gastronomy.restaurant.test.config.TestData;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Ignore;
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
  public void crudTableTest() {

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

    Assert.assertEquals(newState, updatedTable.getState());
    Assert.assertEquals(newNumber, updatedTable.getNumber());

    // delete the table -- fails due to the OCCUPIED state
    Response deleteResponse = this.chief.delete(RestUrls.TableManagement.getDeleteTableUrl(createdTable.getId()));
    Assert.assertEquals(deleteResponse.getStatus(), 400);

    updatedTable =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(createdTable.getId()), TableEto.class)
            .getResponseObject();

    newState = TableState.FREE;
    updatedTable.setState(newState);
    updatedTable = this.waiter.post(updatedTable, RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);

    Assert.assertEquals(newState, updatedTable.getState());

    // delete the table table
    deleteResponse = this.chief.delete(RestUrls.TableManagement.getDeleteTableUrl(createdTable.getId()));
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
  @Ignore
  public void getTableTest() throws Exception {

    TableEntity tableEntity =
        new TableEntityBuilder().number(10l).state(TableState.RESERVED)
            .persist(this.transactionTemplate, TableManagementRestServiceTest.this.entityManager);

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
            TableManagementRestServiceTest.this.entityManager, 5);

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
        TableManagementRestServiceTest.this.transactionTemplate, TableManagementRestServiceTest.this.entityManager, 5);
    new TableEntityBuilder()
        .number(100l)
        .state(TableState.FREE)
        .persistAndDuplicate(TableManagementRestServiceTest.this.transactionTemplate,
            TableManagementRestServiceTest.this.entityManager, 2);
    new TableEntityBuilder()
        .number(200l)
        .state(TableState.RESERVED)
        .persistAndDuplicate(TableManagementRestServiceTest.this.transactionTemplate,
            TableManagementRestServiceTest.this.entityManager, 2);

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getFreeTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    for (ResponseData<TableEto> responseData : tableEtos) {
      Assert.assertNotNull(responseData.getResponseObject());
      Assert.assertEquals(TableState.FREE, responseData.getResponseObject().getState());
    }
  }

  /**
   * Test mark table as
   */
  @Test
  public void markTableAsTest() {

    TableEntity tableEntity =
        new TableEntityBuilder().state(TableState.RESERVED).persist(this.transactionTemplate, this.entityManager);

    ResponseData<TableEto> table =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);
    table.getResponseObject().setState(TableState.OCCUPIED);
    this.waiter.post(table.getResponseObject(), RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);

    table = this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);
    assertThat(table.getResponse().getStatus()).isEqualTo(200);
    assertThat(table.getResponseObject().getState()).isEqualTo(TableState.OCCUPIED);

    table.getResponseObject().setState(TableState.FREE);
    this.waiter.post(table.getResponseObject(), RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);
    table = this.waiter.get(RestUrls.TableManagement.getGetTableUrl(tableEntity.getId()), TableEto.class);
    assertThat(table.getResponse().getStatus()).isEqualTo(200);
    assertThat(table.getResponseObject().getState()).isEqualTo((TableState.FREE));
  }

  /**
   * Test create table
   */
  @Test
  public void createTableTest() {

    this.chief.post(RestUrls.TableManagement.getCreateTableUrl(),
        new TableEtoBuilder().number(10l).state(TableState.FREE).createNew());

    List<ResponseData<TableEto>> tableEtos =
        this.waiter.getAll(RestUrls.TableManagement.getAllTablesUrl(), TableEto.class);

    Assert.assertNotNull(tableEtos);
    Assert.assertEquals(1, tableEtos.size());
    TableEto result = tableEtos.get(0).getResponseObject();
    Assert.assertNotNull(result);
    Assert.assertEquals(new Long(10), result.getNumber());
    Assert.assertEquals(TableState.FREE, result.getState());
  }

  /**
   * Test delete table
   */
  @Test
  public void deleteTableTest() {

    TableEntity tableToBeDeleted =
        new TableEntityBuilder().state(TableState.FREE).persist(this.transactionTemplate, this.entityManager);
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
        new TableEntityBuilder().state(TableState.OCCUPIED).persist(this.transactionTemplate, this.entityManager);

    ResponseData<Boolean> response =
        this.waiter.get(RestUrls.TableManagement.isTableReleasableUrl(tableTarget.getId()), Boolean.class);

    assertThat(response.getResponseObject()).isTrue();
  }

}
