package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
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

  @Inject
  protected Salesmanagement salesmanagement;

  protected static final String ROLE = "chief";

  protected static final int EXPECTED_NUMBER_OF_ORDERS = 1;

  protected static final long SAMPLE_OFFER_ID = 6L;

  protected static final String SAMPLE_OFFER_NAME = "Pizza-Menü";

  protected static final OrderState SAMPLE_ORDER_STATE = OrderState.OPEN;

  protected static final OrderPositionState SAMPLE_ORDER_POSITION_STATE = OrderPositionState.DELIVERED;

  protected static final ProductOrderState SAMPLE_DRINK_STATE = ProductOrderState.DELIVERED;

  protected static final Money SAMPLE_PRICE = new Money(new BigDecimal("6.23"));

  protected static final String SAMPLE_COMMENT = null;

  protected static final long SAMPLE_TABLE_ID = 101;

  protected SalesmanagementRestService service;

  protected static final String BASE_URL_PRAEFIX = "http://localhost:";

  protected static final String BASE_URL_SUFFIX_1 = "/services/rest";

  protected static final String BASE_URL_SUFFIX_2 = "/salesmanagement/v1/";

  protected static long numberOfOrderPositions = 0;

  @PostConstruct
  public void init() {

    this.service = RestTestClientBuilder.build(SalesmanagementRestService.class, ROLE, ROLE,
        BASE_URL_PRAEFIX + this.port + BASE_URL_SUFFIX_1, this.jacksonJsonProvider);
    numberOfOrderPositions = 0;
  }

  @PreDestroy
  public void destroy() {

  }

  @Before
  public void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();
  }

  protected OrderPositionEto createSampleOrderPositionEto(long orderId) {

    OrderPositionEto orderPositionEto = new OrderPositionEto();
    orderPositionEto.setOrderId(orderId);
    orderPositionEto.setOfferId(SAMPLE_OFFER_ID);
    orderPositionEto.setOfferName(SAMPLE_OFFER_NAME);
    orderPositionEto.setState(SAMPLE_ORDER_POSITION_STATE);
    orderPositionEto.setDrinkState(SAMPLE_DRINK_STATE);
    orderPositionEto.setPrice(SAMPLE_PRICE);
    orderPositionEto.setComment(SAMPLE_COMMENT);
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
