#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tablemanagement.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import ${package}.tablemanagement.common.api.datatype.TableState;
import ${package}.test.general.LoginCredentials;
import ${package}.test.tablemanagement.logic.api.to.TableEtoWrapper;
import ${package}.test.tablemanagement.services.api.TableManagementRestService;
import ${package}.test.tablemanagement.services.impl.TableManagementRestServiceImpl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class TableManagementRestServiceTest {

  TableManagementRestService tableManagementService = new TableManagementRestServiceImpl(
      LoginCredentials.waiterUsername);

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {

  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void getTableTest() {

    TableEtoWrapper tableEtoWrapper = this.tableManagementService.getTable((long) 101);
    assertThat(tableEtoWrapper.getResponse().getStatus(), is(200));
    assertThat(tableEtoWrapper.getTableEto().getId(), is((long) 1));

    tableEtoWrapper = this.tableManagementService.getTable((long) 105);
    assertThat(tableEtoWrapper.getResponse().getStatus(), is(200));
    assertThat(tableEtoWrapper.getTableEto().getId(), is((long) 5));
  }

  @Test
  public void getAllTablesTest() {

    List<TableEtoWrapper> tableEtoWrapperList = this.tableManagementService.getAllTables();
    assertThat(tableEtoWrapperList.size(), is(5));
  }

  @Test
  public void getFreeTablesTest() {

    List<TableEtoWrapper> tableEtoWrapperList = this.tableManagementService.getFreeTables();
    assertThat(tableEtoWrapperList.size(), is(4));
  }

  @Test
  public void markTableAsTest() {

    Response response = this.tableManagementService.markTableAs((long) 4, TableState.OCCUPIED);
    assertThat(response.getStatus(), is(204));

  }

  @Test
  public void isTableReleaseable() {

    boolean b = this.tableManagementService.isTableReleasable((long) 1);
    assertThat(b, is(true));
  }

}
