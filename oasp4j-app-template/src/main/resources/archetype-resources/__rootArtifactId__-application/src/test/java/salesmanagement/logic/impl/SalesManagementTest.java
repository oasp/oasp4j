#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.impl;

import ${package}.general.common.AbstractSpringIntegrationTest;
import ${package}.general.common.api.datatype.Money;
import ${package}.salesmanagement.logic.api.Salesmanagement;
import ${package}.salesmanagement.logic.api.to.OrderEto;
import ${package}.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is the test-case of {@link Salesmanagement}.
 *
 * @author hohwille
 */
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_LOGIC })
public class SalesManagementTest extends AbstractSpringIntegrationTest {

  @Inject
  private Salesmanagement salesManagement;

  /**
   * Tests if a Bill is persisted correctly. Special focus is on the mapping of {@link Money} and furthermore the query
   * of one of the {@link Money} fields is tested.
   */
  @Test
  public void testSalesmanagement() {

    TableEto table = new TableEto();
    Long tableId = 1L;
    table.setId(tableId);
    OrderEto order = this.salesManagement.createOrder(table);
    assertEquals(tableId, Long.valueOf(order.getTableId()));
  }

}
