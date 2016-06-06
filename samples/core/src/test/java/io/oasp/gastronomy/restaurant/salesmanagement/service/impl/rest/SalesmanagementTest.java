package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;
import io.oasp.module.basic.configuration.OaspProfile;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@WebIntegrationTest("server.port:0")
@ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST })
@Transactional
public abstract class SalesmanagementTest extends SubsystemTest {

  @Value("${local.server.port}")
  protected int port;

  @Inject
  protected JacksonJsonProvider jacksonJsonProvider;

  // TODO just workaraound, as Jonas solution is not yet approved
  @Inject
  protected Flyway flyway;

  protected UriInfo uriInfo;

  protected final String ROLE = "chief";

  protected final long SAMPLE_OFFER_ID = 6L;

  // TODO talk to Jonas, problematic for ü, escapes donnot work \u00fc
  protected final String SAMPLE_OFFER_NAME = "Pizza-Menü";

  protected final OrderState SAMPLE_ORDER_STATE = OrderState.OPEN;

  protected final OrderPositionState SAMPLE_ORDER_POSITION_STATE = OrderPositionState.DELIVERED;

  protected final ProductOrderState SAMPLE_DRINK_STATE = ProductOrderState.DELIVERED;

  protected final Money SAMPLE_PRICE = new Money(new BigDecimal("6.23"));

  protected final String SAMPLE_COMMENT = null;

  protected final long SAMPLE_TABLE_ID = 101;

  protected SalesmanagementRestService service;

  @Before
  protected void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();
    // cannot be put into constructor, as port is set after the constructor invocation
    this.service = RestTestClientBuilder.build(SalesmanagementRestService.class, this.ROLE, this.ROLE,
        "http://localhost:" + this.port + "/services/rest", this.jacksonJsonProvider);
  }

  protected OrderPositionEto createSampleOrderPositionEto(long orderId) {

    OrderPositionEto orderPositionEto = new OrderPositionEto();
    orderPositionEto.setOrderId(orderId);
    orderPositionEto.setOfferId(this.SAMPLE_OFFER_ID);
    orderPositionEto.setOfferName(this.SAMPLE_OFFER_NAME);
    orderPositionEto.setState(this.SAMPLE_ORDER_POSITION_STATE);
    orderPositionEto.setDrinkState(this.SAMPLE_DRINK_STATE);
    orderPositionEto.setPrice(this.SAMPLE_PRICE);
    orderPositionEto.setComment(this.SAMPLE_COMMENT);
    return orderPositionEto;
  }

  protected OrderCto createSampleOrderCto(long tableId) {

    OrderCto sampleOrderCto = new OrderCto();
    OrderEto sampleOrderEto = new OrderEto();
    sampleOrderEto.setTableId(tableId);
    sampleOrderCto.setOrder(sampleOrderEto);
    return sampleOrderCto;
  }

}
