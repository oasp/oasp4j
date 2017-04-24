package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl;

import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.DbTestHelper;
import io.oasp.gastronomy.restaurant.general.common.TestUtil;
import io.oasp.gastronomy.restaurant.general.common.api.builders.OrderEtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.api.builders.OrderPositionEtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * This is the test case for the component {@link Tablemanagement}.
 *
 *
 */

@SpringApplicationConfiguration(classes = { SpringBootApp.class })
@WebAppConfiguration
public class TablemanagementTest extends ComponentTest {

  @Inject
  private Salesmanagement salesmanagement;

  @Inject
  private Tablemanagement tablemanagement;

  @Inject
  private DbTestHelper dbTestHelper;

  /**
   * Provides login credentials and permissions and resets database. The database is migrated to Version 0002 in order
   * to have a common basis for test.
   */
  @Override
  public void doSetUp() {

    super.doSetUp();
    TestUtil.login("waiter", PermissionConstants.SAVE_ORDER_POSITION, PermissionConstants.SAVE_ORDER,
        PermissionConstants.FIND_TABLE, PermissionConstants.FIND_ORDER, PermissionConstants.SAVE_TABLE,
        PermissionConstants.FIND_OFFER);
    this.dbTestHelper.setMigrationVersion("0002");
    this.dbTestHelper.resetDatabase();

  }

  /**
   * Logs out used credentials.
   */
  @Override
  public void doTearDown() {

    super.doTearDown();
    TestUtil.logout();
  }

  /**
   * This test method tests if a table is releasable without and with an open order attached to it.
   */
  @Test
  public void testIsTableReleasable() {

    // given
    // initially table is releasable (despite TableState is OCCUPIED - but there is no open order)
    Long tableNumber = 101L;
    Long offerId = 2L;
    TableEto table = this.tablemanagement.findTable(tableNumber);
    assertThat(table.getState()).isEqualTo(TableState.OCCUPIED);
    assertThat(this.tablemanagement.isTableReleasable(table)).isTrue();

    // when
    // add order with open orderPosition to table
    OrderEto newOrder = new OrderEtoBuilder().tableId(table.getId()).createNew();
    OrderEto order = this.salesmanagement.saveOrder(newOrder);
    long orderId = order.getId();
    OrderPositionEto newOrderPosition = new OrderPositionEtoBuilder().orderId(orderId).offerId(offerId).createNew();
    OrderPositionEto orderPosition = this.salesmanagement.saveOrderPosition(newOrderPosition);

    // then
    // table should be OCCUPIED
    assertThat(order).isNotNull();
    assertThat(orderPosition).isNotNull();
    assertThat(this.tablemanagement.isTableReleasable(table)).isFalse();
    assertThat(table.getState()).isEqualTo(TableState.OCCUPIED);
  }

  /**
   * This test method finds all free tables, occupies one of them and checks if the number of free tables is reduced by
   * 1.
   */
  @Test
  public void testFindFreeTables() {

    // given
    List<TableEto> freeTables = this.tablemanagement.findFreeTables();
    assertThat(freeTables).isNotNull();
    assertThat(freeTables).isNotEmpty();
    int numberOfFreeTables = freeTables.size();

    // when
    // occupy the first free table in the list
    Long idOfFirstFreeTable = freeTables.get(0).getId();
    TableEto firstFreeTable = this.tablemanagement.findTable(idOfFirstFreeTable);
    assertThat(firstFreeTable).isNotNull();
    firstFreeTable.setState(TableState.OCCUPIED);
    TableEto updatedTable = this.tablemanagement.saveTable(firstFreeTable);
    assertThat(updatedTable).isNotNull();

    // then
    List<TableEto> newFreeTables = this.tablemanagement.findFreeTables();
    assertThat(newFreeTables).isNotNull();
    assertThat(newFreeTables).isNotEmpty();
    assertThat(newFreeTables.size()).isEqualTo(numberOfFreeTables - 1);

  }

  /**
   * This test method finds all tables currently in the database and checks if a specific one is in the retrieved list.
   */
  @Test
  public void testFindAllTables() {

    // given
    Long tableNumber = 101L;
    TableEto table = this.tablemanagement.findTable(tableNumber);

    // when
    List<TableEto> allTables = this.tablemanagement.findAllTables();

    // then
    assertThat(allTables).isNotNull();
    assertThat(allTables).isNotEmpty();
    assertThat(allTables).hasOnlyElementsOfType(TableEto.class);
    assertThat(allTables).extracting("state").contains(table.getState());
    assertThat(allTables).extracting("waiterId").contains(table.getWaiterId());
    assertThat(allTables).extracting("number").contains(table.getNumber());

  }

  /**
   * This test tries to delete a table without credentials.
   */
  @Test(expected = AccessDeniedException.class)
  public void testDeleteTableWithoutCredentials() {

    // given
    Long tableNumber = 101L;

    // when
    this.tablemanagement.deleteTable(tableNumber);

    // then
    assertThat(this.tablemanagement.findTable(tableNumber)).isNotNull();
  }

  /**
   * This test tries to delete a table that is occupied.
   */
  @Test(expected = IllegalEntityStateException.class)
  public void testDeleteTableWithCredentialsButNotDeletable() {

    // given
    TestUtil.login("waiter", PermissionConstants.DELETE_TABLE, PermissionConstants.FIND_TABLE);
    Long tableNumber = 101L;

    // when
    this.tablemanagement.deleteTable(tableNumber);

    // then
    assertThat(this.tablemanagement.findTable(tableNumber)).isNotNull();
  }

  /**
   * This table tries to delete a table that does not exist.
   */
  @Test(expected = ObjectNotFoundUserException.class)
  public void testDeleteTableWithCredentialsNotExisting() {

    // given
    TestUtil.login("waiter", PermissionConstants.DELETE_TABLE, PermissionConstants.FIND_TABLE);
    Long tableNumber = 100L;

    // when
    this.tablemanagement.deleteTable(tableNumber);

    // then
    assertThat(this.tablemanagement.findTable(tableNumber)).isNull();
  }

  /**
   * This table deletes a table that is free.
   */
  @Test
  public void testDeleteTableWithCredentials() {

    // given
    TestUtil.login("waiter", PermissionConstants.DELETE_TABLE, PermissionConstants.FIND_TABLE);
    Long tableNumber = 102L;

    // when
    this.tablemanagement.deleteTable(tableNumber);

    // then
    assertThat(this.tablemanagement.findTable(tableNumber)).isNull();
  }

}
