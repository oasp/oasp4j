package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This is the test-case of {@link SalesmanagementRestServiceImpl}.
 *
 * @author llaszkie
 */
@RunWith(MockitoJUnitRunner.class)
public class SalesmanagementRestServiceTest extends Assert {

  @Mock
  private Salesmanagement salesManagement;

  @Mock
  private UriInfo info;

  @InjectMocks
  private SalesmanagementRestServiceImpl salesManagementRestService = new SalesmanagementRestServiceImpl();

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceImpl#findOrders(javax.ws.rs.core.UriInfo)}
   * .
   */
  @Test
  public void testShouldFindOrders() {

    // given
    MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
    parameters.putSingle("tableId", "1");
    parameters.putSingle("state", "OPEN");
    when(this.info.getQueryParameters()).thenReturn(parameters);

    List<OrderCto> expectedOrders = new ArrayList<>();
    when(this.salesManagement.findOrderCtos(any(OrderSearchCriteriaTo.class))).thenReturn(expectedOrders);

    // and when
    List<OrderCto> foundOrders = this.salesManagementRestService.findOrders(this.info);

    // then expected orders were found
    assertSame("Expected orders were found.", expectedOrders, foundOrders);
    // then expected criteria were provided
    OrderSearchCriteriaTo expectedCriteria = new OrderSearchCriteriaTo();
    expectedCriteria.setTableId(1L);
    expectedCriteria.setState(OrderState.OPEN);
    verify(this.salesManagement, times(1)).findOrderCtos(eq(expectedCriteria));
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceImpl#findOrders(javax.ws.rs.core.UriInfo)}
   * .
   */
  @Test
  public void testShouldFindOrdersWithPaging() {

    // given
    MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
    parameters.putSingle("page", "3");
    parameters.putSingle("count", "100");
    when(this.info.getQueryParameters()).thenReturn(parameters);

    // and when
    this.salesManagementRestService.findOrders(this.info);

    // then expected criteria were provided
    OrderSearchCriteriaTo expectedCriteria = new OrderSearchCriteriaTo();
    expectedCriteria.setHitOffset(200);
    expectedCriteria.setMaximumHitCount(100);
    verify(this.salesManagement, times(1)).findOrderCtos(eq(expectedCriteria));
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceImpl#findOrderPositions(javax.ws.rs.core.UriInfo)}
   * .
   */
  @Test
  public void testShouldFindOrderPositions() {

    // given
    MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
    parameters.putSingle("orderId", "1");
    parameters.putSingle("cookId", "2");
    parameters.putSingle("state", "PAYED");
    parameters.putSingle("mealOrSideDish", "true");
    when(this.info.getQueryParameters()).thenReturn(parameters);

    List<OrderPositionEto> expectedOrderPositions = new ArrayList<>();
    when(this.salesManagement.findOrderPositions(any(OrderPositionSearchCriteriaTo.class))).thenReturn(
        expectedOrderPositions);

    // and when
    List<OrderPositionEto> foundOrderPositions = this.salesManagementRestService.findOrderPositions(this.info);

    // then expected order positions were found
    assertSame("Expected order positions were found.", expectedOrderPositions, foundOrderPositions);
    // then expected criteria were provided
    OrderPositionSearchCriteriaTo expectedCriteria = new OrderPositionSearchCriteriaTo();
    expectedCriteria.setOrderId(1L);
    expectedCriteria.setCookId(2L);
    expectedCriteria.setState(OrderPositionState.PAYED);
    expectedCriteria.setMealOrSideDish(true);
    verify(this.salesManagement, times(1)).findOrderPositions(eq(expectedCriteria));
  }

  /**
   * Test method for
   * {@link io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceImpl#findOrderPositions(javax.ws.rs.core.UriInfo)}
   * .
   */
  @Test
  public void testShouldFindOrderPositionsWithPaging() {

    // given
    MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
    parameters.putSingle("page", "4");
    parameters.putSingle("count", "50");
    when(this.info.getQueryParameters()).thenReturn(parameters);

    // and when
    this.salesManagementRestService.findOrderPositions(this.info);

    // then expected criteria were provided
    OrderPositionSearchCriteriaTo expectedCriteria = new OrderPositionSearchCriteriaTo();
    expectedCriteria.setHitOffset(150);
    expectedCriteria.setMaximumHitCount(50);
    verify(this.salesManagement, times(1)).findOrderPositions(eq(expectedCriteria));
  }

}
