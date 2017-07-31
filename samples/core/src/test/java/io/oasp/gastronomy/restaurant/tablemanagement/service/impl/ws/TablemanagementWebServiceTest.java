package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.ws;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.ws.v1_0.TablemanagementWebService;
import io.oasp.module.service.common.api.client.ServiceClientFactory;

/**
 * Test of {@link TablemanagementWebService} over the wire (via HTTP).
 */
@RunWith(SpringRunner.class)
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement",
"service.client.app.restaurant.user.login=waiter" })
public class TablemanagementWebServiceTest extends AbstractRestServiceTest {

  @Inject
  private ServiceClientFactory serviceClientFactory;

  private TablemanagementWebService tablemanagementWebService;

  @Override
  protected void doSetUp() {

    super.doSetUp();
    this.tablemanagementWebService = this.serviceClientFactory.create(TablemanagementWebService.class);
  }

  @Test
  public void testWebService() {

    // given
    long id = 102;

    // when
    TableEto table = this.tablemanagementWebService.getTable(id);

    // then
    assertThat(table).isNotNull();
    assertThat(table.getId()).isEqualTo(id);
  }

}
