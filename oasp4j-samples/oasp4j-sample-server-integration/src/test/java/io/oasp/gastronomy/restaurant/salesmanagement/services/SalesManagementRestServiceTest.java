package io.oasp.gastronomy.restaurant.salesmanagement.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import io.oasp.AbstractDBRollbackTest;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.CreditCardPaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.LoginCredentials;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.RestUrls;
import io.oasp.gastronomy.restaurant.test.general.TestData.Additional;
import io.oasp.gastronomy.restaurant.test.general.TestData.DB;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;
import io.oasp.gastronomy.restaurant.test.general.webclient.WebClientWrapper;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test sales management rest service
 *
 * @author arklos
 */
public class SalesManagementRestServiceTest extends AbstractDBRollbackTest {

  private WebClientWrapper waiter = new WebClientWrapper(LoginCredentials.waiterUsername,
      LoginCredentials.waiterPassword);

  private WebClientWrapper chief = new WebClientWrapper(LoginCredentials.chiefUsername, LoginCredentials.chiefPassword);

  /**
   * Test get order service
   */
  @Test
  public void getOrderTest() {

    Long id = DB.ORDER_1.getId();
    ResponseData<OrderEto> order = this.waiter.get(RestUrls.SalesManagement.Order.getFindOrderURL(id), OrderEto.class);
    assertThat(order.getResponseObject().getId(), is(id));
  }

  /**
   * Test find all orders service
   */
  @Test
  @Ignore
  public void findAllOrdersTest() {

    String tableId = "" + DB.TABLE_1.getId();

    List<ResponseData<OrderCto>> allOrders =
        this.waiter.getAll(RestUrls.SalesManagement.Order.getFindOrdersURL(), OrderCto.class);
    assertThat(allOrders.size(), is(DB.ALL_ORDERS.size()));

    List<NameValuePair> params = new LinkedList<>();
    params.add(new BasicNameValuePair("tableId", tableId));
    allOrders = this.waiter.getAll(RestUrls.SalesManagement.Order.getFindOrdersURL(), params, OrderCto.class);
    assertThat(allOrders.size(), is(1));

    params.clear();
    params.add(new BasicNameValuePair("state", TableState.OCCUPIED.toString()));
    allOrders = this.waiter.getAll(RestUrls.SalesManagement.Order.getFindOrdersURL(), params, OrderCto.class);
    assertThat(allOrders.size(), is(1));
  }

  /**
   * Test update order service
   */
  @Test
  @Ignore
  public void updateOrderTest() {

    String url = RestUrls.SalesManagement.Order.getFindOrderURL(Additional.CHANGED_ORDER_1_CTO.getOrder().getId());
    this.chief.put(url, Additional.CHANGED_ORDER_1_CTO.getOrder());
    ResponseData<OrderEto> orderEto = this.waiter.get(url, OrderEto.class);
    assertThat(orderEto.getResponseObject().getState(), is(Additional.CHANGED_ORDER_1_CTO.getOrder().getState()));
  }

  /**
   * Test the createOrder rest service
   */
  @Test
  @Ignore
  public void createOrder() {

    this.chief.post(RestUrls.SalesManagement.Order.getCreateOrderURL(), Additional.ORDER);
    List<ResponseData<OrderCto>> allOrders =
        this.waiter.getAll(RestUrls.SalesManagement.Order.getFindOrdersURL(), OrderCto.class);
    assertThat(allOrders.size(), is(DB.ALL_ORDERS.size() + 1));

  }

  /**
   * Test the createOrderPostion rest service
   */
  // @Test
  public void createOrderPosition() {

    OfferEto offer = Additional.OFFER;
    // the only way to get the newly created orderPosition is at the point. But it crashes
    // TODO: Investigate the error and fix it
    @SuppressWarnings("unused")
    ResponseData<OrderPositionEto> position =
        this.chief.post(RestUrls.SalesManagement.Order.getCreateOrderPositionURL(DB.ORDER_1.getId(), ""), offer,
            OrderPositionEto.class);

  }

  /**
   * Test the get order position service
   */
  @Test
  public void getOrderPosition() {

    Long orderId = DB.ORDER_POSITION_1.getOrderId();
    Long orderPositionId = DB.ORDER_POSITION_1.getId();
    ResponseData<OrderPositionEto> position =
        this.waiter.get(RestUrls.SalesManagement.Order.getGetOrderPositionURL(orderId, orderPositionId),
            OrderPositionEto.class);
    assertThat(position.getResponseObject().getId(), is(orderPositionId));
  }

  /**
   * Test update order position
   */
  @Test
  public void updateOrderPosition() {

    this.chief.post(RestUrls.SalesManagement.Order.getUpdateOrderPositionURL(), Additional.CHANGED_ORDER_POSITION_1);

    ResponseData<OrderPositionEto> position =
        this.chief.get(RestUrls.SalesManagement.Order.getGetOrderPositionURL(1L, 1L), OrderPositionEto.class);
    assertThat(position.getResponseObject().getState(), is(Additional.CHANGED_ORDER_POSITION_1.getState()));

  }

  /**
   * Tests the mark order position service
   */
  @Test
  @Ignore
  public void markOrderPositionAsTest() {

    Long orderId = DB.ORDER_1.getId();
    Long orderPositionId = DB.ORDER_POSITION_1.getId();

    this.chief.put(RestUrls.SalesManagement.Order.getMarkOrderPositionAsURL(orderId, orderPositionId,
        OrderPositionState.CANCELLED), DB.ORDER_POSITION_1);
    ResponseData<OrderPositionEto> position =
        this.chief.get(RestUrls.SalesManagement.Order.getGetOrderPositionURL(orderId, orderPositionId),
            OrderPositionEto.class);

    assertThat(position.getResponseObject().getState(), is(OrderPositionState.CANCELLED));
  }

  /**
   * Tests the get bill rest service
   */
  @Test
  public void getBill() {

    ResponseData<BillEto> bill =
        this.waiter.get(RestUrls.SalesManagement.Bill.getGetBillURL(DB.BILL_1.getId()), BillEto.class);
    assertThat(bill.getResponseObject().getId(), is(DB.BILL_1.getId()));
  }

  /**
   * Test the do payment rest service
   */
  @Test
  public void doPaymentTest() {

    this.waiter.post(RestUrls.SalesManagement.Bill.getDoPaymentURL(DB.BILL_1.getId()));
    ResponseData<BillEto> bill =
        this.waiter.get(RestUrls.SalesManagement.Bill.getGetBillURL(DB.BILL_1.getId()), BillEto.class);
    assertThat(bill.getResponseObject().isPayed(), is(true));
  }

  /**
   * Test the do payment rest service with payment data
   */
  // @Test
  public void doPaymentWithPaymentDataTest() {

    // This serviec is not supported yet as far as I can see
    PaymentData paymentData = new CreditCardPaymentData();
    this.waiter.post(RestUrls.SalesManagement.Bill.getDoPaymentURL(DB.BILL_1.getId()), paymentData);
    ResponseData<BillEto> bill =
        this.waiter.get(RestUrls.SalesManagement.Bill.getGetBillURL(DB.BILL_1.getId()), BillEto.class);
    assertThat(bill.getResponseObject().isPayed(), is(true));
  }

  /**
   * Test the create bill service
   */
  @Test
  @Ignore
  public void createBillTest() {

    Response r =
        this.chief.post(RestUrls.SalesManagement.Bill.getCreateBillURL(new Money(10)), Additional.ORDER_POSITION);
    assertThat(r.getStatus(), is(200));
  }

  /**
   * Test the delteBill rest service
   */
  @Test
  @Ignore
  public void deleteBillTest() {

    this.chief.delete(RestUrls.SalesManagement.Bill.deleteBill(DB.BILL_1.getId()));
    ResponseData<BillEto> bill =
        this.chief.get(RestUrls.SalesManagement.Bill.getGetBillURL(DB.BILL_1.getId()), BillEto.class);
    assertThat(bill.getResponseObject() == null, is(true));
  }
}
