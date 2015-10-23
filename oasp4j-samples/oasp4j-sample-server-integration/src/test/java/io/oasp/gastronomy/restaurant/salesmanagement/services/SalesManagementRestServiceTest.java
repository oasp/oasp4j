package io.oasp.gastronomy.restaurant.salesmanagement.services;

import io.oasp.gastronomy.restaurant.general.common.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.BillEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.CreditCardPaymentData;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.PaymentData;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.test.config.RestUrls;
import io.oasp.gastronomy.restaurant.test.config.TestData;
import io.oasp.gastronomy.restaurant.test.config.TestData.Additional;
import io.oasp.gastronomy.restaurant.test.config.TestData.DB;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;

import java.math.BigDecimal;
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
public class SalesManagementRestServiceTest extends AbstractRestServiceTest {

  /**
   * Test get order service
   */
  @Test
  @Ignore
  public void getOrderTest() {

    Long id = DB.ORDER_1.getId();
    ResponseData<OrderEto> order = this.waiter.get(RestUrls.SalesManagement.Order.getFindOrderURL(id), OrderEto.class);
    assertThat(order.getResponseObject().getId()).isEqualTo(id);
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
    assertThat(allOrders.size()).isEqualTo(DB.ALL_ORDERS.size());

    List<NameValuePair> params = new LinkedList<>();
    params.add(new BasicNameValuePair("tableId", tableId));
    allOrders = this.waiter.getAll(RestUrls.SalesManagement.Order.getFindOrdersURL(), params, OrderCto.class);
    assertThat(allOrders.size()).isEqualTo(1);

    params.clear();
    params.add(new BasicNameValuePair("state", TableState.OCCUPIED.toString()));
    allOrders = this.waiter.getAll(RestUrls.SalesManagement.Order.getFindOrdersURL(), params, OrderCto.class);
    assertThat(allOrders.size()).isEqualTo(1);
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
    assertThat(orderEto.getResponseObject().getState()).isEqualTo(Additional.CHANGED_ORDER_1_CTO.getOrder().getState());
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
    assertThat(allOrders.size()).isEqualTo((DB.ALL_ORDERS.size() + 1));

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
        this.chief.post(RestUrls.SalesManagement.Order.getCreateOrderPositionURL(), offer, OrderPositionEto.class);

  }

  /**
   * Test the get order position service
   */
  @Test
  @Ignore
  public void getOrderPosition() {

    DB.ORDER_POSITION_1.getOrderId();
    Long orderPositionId = DB.ORDER_POSITION_1.getId();
    ResponseData<OrderPositionEto> position =
        this.waiter.get(RestUrls.SalesManagement.Order.getGetOrderPositionURL(orderPositionId), OrderPositionEto.class);
    assertThat(position.getResponseObject().getId()).isEqualTo(orderPositionId);
  }

  /**
   * Test update order position
   */
  @SuppressWarnings("unchecked")
  @Test
  @Ignore
  public void updateOrderPositionTest() {

    this.chief.post(RestUrls.SalesManagement.Order.getUpdateOrderPositionURL(), Additional.CHANGED_ORDER_POSITION_1);

    // ResponseData<OrderPositionEto> position =
    // this.chief.get(RestUrls.SalesManagement.Order.getGetOrderPositionURL(1L, 1L), OrderPositionEto.class);

    // create table
    TableEto table = TestData.createTable(null, 1L, null, TableState.FREE);
    TableEto createdTable = this.chief.post(table, RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);
    // create order
    OrderCto order = TestData.createOrderCto(null, createdTable.getId(), null, OrderState.OPEN);
    OrderCto createdOrder = this.waiter.post(order, RestUrls.SalesManagement.Order.getCreateOrderURL(), OrderCto.class);
    // create order position
    Money price = new Money(6.99);
    OrderPositionEto orderPosition =
        TestData.createOrderPosition(null, "Schnitzel-Menü", "mit Ketschup", OrderPositionState.DELIVERED, createdOrder
            .getOrder().getId(), null, price);
    OrderPositionEto createdOrderPosition =
        this.waiter.post(orderPosition, RestUrls.SalesManagement.Order.getCreateOrderPositionURL(),
            OrderPositionEto.class);
    Money newPrice = price.add(new Money(1.01));
    createdOrderPosition.setPrice(newPrice);
    // update order position
    this.waiter.post(createdOrderPosition, RestUrls.SalesManagement.Order.getCreateOrderPositionURL(),
        OrderPositionEto.class);

    ResponseData<OrderPositionEto> changedPosition =
        this.chief.get(RestUrls.SalesManagement.Order.getGetOrderPositionURL(createdOrderPosition.getId()),
            OrderPositionEto.class);

    assertThat(changedPosition.getResponseObject().getPrice()).isEqualTo(newPrice);

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
        this.chief.get(RestUrls.SalesManagement.Order.getGetOrderPositionURL(orderPositionId), OrderPositionEto.class);

    assertThat(position.getResponseObject().getState()).isEqualTo(OrderPositionState.CANCELLED);
  }

  /**
   * Test change Table
   */
  @Test
  public void changeTableTest() {

    // create table
    TableEto table = TestData.createTable(null, 1L, null, TableState.OCCUPIED);
    TableEto createdTable = this.chief.post(table, RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);
    TableEto tableTwo = TestData.createTable(null, 2L, null, TableState.FREE);
    TableEto createdTableTwo = this.chief.post(tableTwo, RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);
    TableEto tableThree = TestData.createTable(null, 3L, null, TableState.OCCUPIED);
    TableEto createdTableThree =
        this.chief.post(tableThree, RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);
    // create order
    OrderCto order = TestData.createOrderCto(null, createdTable.getId(), null, OrderState.OPEN);
    OrderCto createdOrder = this.waiter.post(order, RestUrls.SalesManagement.Order.getCreateOrderURL(), OrderCto.class);
    // change table
    Response postStatusCode =
        this.waiter.post(RestUrls.SalesManagement.Order.getChangeTableURL(createdOrder.getOrder().getId()),
            createdTableTwo.getId());
    int CurrentPostStatus = postStatusCode.getStatus();
    assertThat(CurrentPostStatus).isEqualTo(204);

    ResponseData<TableEto> tableTwoStatus =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(createdTableTwo.getId()), TableEto.class);
    assertThat(tableTwoStatus.getResponseObject().getState()).isEqualTo(TableState.OCCUPIED);

    ResponseData<TableEto> tableStatus =
        this.waiter.get(RestUrls.TableManagement.getGetTableUrl(createdTable.getId()), TableEto.class);
    assertThat(tableStatus.getResponseObject().getState()).isEqualTo(TableState.FREE);

    // Tests if you try to change the table to the current table
    Response currentTableResp =
        this.waiter.post(RestUrls.SalesManagement.Order.getChangeTableURL(createdOrder.getOrder().getId()),
            createdOrder.getOrder().getTableId());
    int statusCodeCurrentTable = currentTableResp.getStatus();

    assertThat(statusCodeCurrentTable).isEqualTo(204);

    // Tests if the exception gets thrown if you try to change the table to an occupied table
    Response occupiedTableResp =
        this.waiter.post(RestUrls.SalesManagement.Order.getChangeTableURL(createdOrder.getOrder().getId()),
            createdTableThree.getId());

    int statusCodeOccupiedTable = occupiedTableResp.getStatus();

    assertThat(statusCodeOccupiedTable).isEqualTo(400);

  }

  /**
   * Tests the get bill rest service
   */
  @Test
  @Ignore
  public void getBill() {

    ResponseData<BillEto> bill =
        this.waiter.get(RestUrls.SalesManagement.Bill.getGetBillURL(DB.BILL_1.getId()), BillEto.class);
    assertThat(bill.getResponseObject().getId()).isEqualTo(DB.BILL_1.getId());
  }

  /**
   * Test the do payment rest service
   */
  @Test
  @Ignore
  public void doPaymentTest() {

    // create table
    TableEto table = TestData.createTable(null, 1L, null, TableState.FREE);
    TableEto createdTable = this.chief.post(table, RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);
    // create order
    OrderCto order = TestData.createOrderCto(null, createdTable.getId(), null, OrderState.OPEN);
    OrderCto createdOrder = this.waiter.post(order, RestUrls.SalesManagement.Order.getCreateOrderURL(), OrderCto.class);
    // create order position
    OrderPositionEto orderPosition =
        TestData.createOrderPosition(null, "Schnitzel-Menü", "mit Ketschup", OrderPositionState.DELIVERED, createdOrder
            .getOrder().getId(), null, new Money(BigDecimal.valueOf(6.99)));
    OrderPositionEto createdOrderPosition =
        this.waiter.post(orderPosition, RestUrls.SalesManagement.Order.getCreateOrderPositionURL(),
            OrderPositionEto.class);
    // create bill
    BillEto bill = TestData.createBill(null, false, null, null, null, createdOrderPosition.getId());
    BillEto createdBill =
        this.waiter.post(bill, RestUrls.SalesManagement.Bill.getCreateBillURL(new Money(BigDecimal.valueOf(6.99))),
            BillEto.class);
    this.waiter.post(RestUrls.SalesManagement.Bill.getDoPaymentURL(createdBill.getId()));
    // get payed bill
    ResponseData<BillCto> response =
        this.waiter.get(RestUrls.SalesManagement.Bill.getGetBillURL(createdBill.getId()), BillCto.class);
    // TODO: This check will not work currently because payment does not persist changes
    // Please enable this when payment use case is completely implemented
    // assertThat(response.getResponseObject().getBill().isPayed(), is(true));
  }

  /**
   * Test the do payment rest service with payment data
   */
  // @Test
  public void doPaymentWithPaymentDataTest() {

    // This service is not supported yet as far as I can see
    PaymentData paymentData = new CreditCardPaymentData();
    this.waiter.post(RestUrls.SalesManagement.Bill.getDoPaymentURL(DB.BILL_1.getId()), paymentData);
    ResponseData<BillEto> bill =
        this.waiter.get(RestUrls.SalesManagement.Bill.getGetBillURL(DB.BILL_1.getId()), BillEto.class);
    assertThat(bill.getResponseObject().isPayed()).isTrue();
  }

  /**
   * Test the create bill service
   */
  @Test
  @Ignore
  public void createBillTest() {

    Response r =
        this.chief.post(RestUrls.SalesManagement.Bill.getCreateBillURL(new Money(10)), Additional.ORDER_POSITION);
    assertThat(r.getStatus()).isEqualTo(200);
  }

  /**
   * Test the delteBill rest service
   */
  @Test
  @Ignore
  public void deleteBillTest() {

    // create table
    TableEto table = TestData.createTable(null, 1L, null, TableState.FREE);
    TableEto createdTable = this.chief.post(table, RestUrls.TableManagement.getCreateTableUrl(), TableEto.class);
    // create order
    OrderCto order = TestData.createOrderCto(null, createdTable.getId(), null, OrderState.OPEN);
    OrderCto createdOrder = this.waiter.post(order, RestUrls.SalesManagement.Order.getCreateOrderURL(), OrderCto.class);
    // create order position
    OrderPositionEto orderPosition =
        TestData.createOrderPosition(null, "Schnitzel-Menü", "mit Ketschup", OrderPositionState.DELIVERED, createdOrder
            .getOrder().getId(), null, new Money(BigDecimal.valueOf(6.99)));
    OrderPositionEto createdOrderPosition =
        this.waiter.post(orderPosition, RestUrls.SalesManagement.Order.getCreateOrderPositionURL(),
            OrderPositionEto.class);
    // create bill
    BillEto bill = TestData.createBill(null, false, null, null, null, createdOrderPosition.getId());
    BillEto createdBill =
        this.waiter.post(bill, RestUrls.SalesManagement.Bill.getCreateBillURL(new Money(BigDecimal.valueOf(6.99))),
            BillEto.class);

    this.chief.delete(RestUrls.SalesManagement.Bill.deleteBill(createdBill.getId()));
    ResponseData<BillCto> response =
        this.chief.get(RestUrls.SalesManagement.Bill.getGetBillURL(createdBill.getId()), BillCto.class);
    assertThat(response.getResponseObject()).isNull();
  }
}
