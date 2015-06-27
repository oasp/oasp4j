package io.oasp.gastronomy.restaurant.test.config;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;

/**
 * All Service Urls
 *
 * @author arklos
 */
public interface RestUrls {

  /**
   * SalesManagement Urls
   *
   * @author arklos
   */
  public static class SalesManagement {
    /**
    *
    */
    public final static String SALES_MANAGEMENT_URL = "/services/rest/salesmanagement/v1";

    /**
     * Order Urls
     *
     * @author arklos
     */
    public static class Order {
      /**
     *
     */
      public final static String ORDER_URL = SALES_MANAGEMENT_URL + "/order";

      /**
       * @param orderId The orderId of the request
       * @return URL to get the order for the specified Id
       */
      public static final String getFindOrderURL(Long orderId) {

        return ORDER_URL + "/" + orderId;
      }

      /**
       */
      /**
       * @return Url
       */
      public static final String getFindOrdersURL() {

        return ORDER_URL + "/";
      }

      /**
       * @return URL to create a Order
       */
      public static final String getCreateOrderURL() {

        return ORDER_URL + "/";
      }

      /**
       * @return URL to create a OrderPosition
       */
      public static final String getCreateOrderPositionURL() {

        return ORDER_URL + "position/";
      }

      /**
       * @param orderPositionId Id of the position
       * @return URL to get the OrderPosition
       */
      public static final String getGetOrderPositionURL(Long orderPositionId) {

        return ORDER_URL + "position/" + orderPositionId;
      }

      /**
       * @return URL to get the OrderPosition
       */
      public static final String getUpdateOrderPositionURL() {

        return ORDER_URL + "position/";
      }

      /**
       * @param orderId Order of the position
       * @param orderPositionId Id of the position
       * @param state New state
       * @return URL to get the OrderPosition
       */
      public static final String getMarkOrderPositionAsURL(Long orderId, Long orderPositionId, OrderPositionState state) {

        return ORDER_URL + orderId + "/position/" + orderPositionId + "/" + state;
      }

      /**
       * @param orderId Id
       * @return URL to get the ChangeTable
       */
      public static final String getChangeTableURL(Long orderId) {

        return ORDER_URL + "/" + orderId + "/";
      }

    }

    /**
     * Bill Urls
     *
     * @author arklos
     */
    public static class Bill {
      /**
     *
     */
      public final static String BILL_URL = SALES_MANAGEMENT_URL + "/bill";

      /**
       * @param billId Id of the bill
       * @return URL to get the bill
       */
      public static final String getGetBillURL(Long billId) {

        return BILL_URL + "/" + billId;
      }

      /**
       * @param billId Id of the bill
       * @return URL for payments
       */
      public static final String getDoPaymentURL(Long billId) {

        return BILL_URL + "/" + billId + "/payment";
      }

      /**
       * @param tip Tip
       * @return URL to create Bills
       */
      public static final String getCreateBillURL(Money tip) {

        return BILL_URL + "/";
      }

      /**
       * @param billId Bill id
       * @return Url
       */
      public static final String deleteBill(Long billId) {

        return getGetBillURL(billId);
      }
    }
  }

  /**
   * StaffManagement Urls
   *
   * @author arklos
   */
  public static class StaffManagement {
    /**
     *
     */
    public final static String STAFF_MANAGEMENT_URL = "/services/rest/staffmanagement/v1/staff";

    /**
     * @return Url
     */
    public final static String getGetAllStaffMembersUrl() {

      return STAFF_MANAGEMENT_URL + "/";
    }

    /**
     * @param login Login
     * @return Url
     */
    public final static String getGetStaffMemberUrl(String login) {

      return STAFF_MANAGEMENT_URL + "/" + login;
    }

    /**
     * @param login Login
     * @return Url
     */
    public final static String getGetStaffMember(String login) {

      return STAFF_MANAGEMENT_URL + "/" + login;
    }

    /**
     * @param login Login
     * @return Url
     */
    public final static String getUpdateStaffMember(String login) {

      return STAFF_MANAGEMENT_URL + "/" + login;
    }

    /**
     * @param login Login
     * @return Url
     */
    public final static String getDeleteStaffMemberUrl(String login) {

      return STAFF_MANAGEMENT_URL + "/" + login;
    }
  }

  /**
   * Tablemanagement Urls
   *
   * @author arklos
   */
  public static class TableManagement {
    /**
     *
     */
    public final static String TABLE_MANAGEMENT_URL = "/services/rest/tablemanagement/v1";

    /**
     *
     */
    public static final String TABLE_URL = TABLE_MANAGEMENT_URL + "/table";

    /**
     * @param id Table Id
     * @return Url
     */
    public final static String getGetTableUrl(Long id) {

      return TABLE_URL + "/" + id + "/";
    }

    /**
     * @return Url
     */
    public final static String getAllTablesUrl() {

      return TABLE_URL;
    }

    /**
     * @return Url
     */
    public final static String getCreateTableUrl() {

      return TABLE_URL;
    }

    /**
     * @param id Table id
     * @return Url
     */
    public final static String getDeleteTableUrl(Long id) {

      return TABLE_URL + "/" + id;
    }

    /**
     * @return Url
     */
    public static final String getFreeTablesUrl() {

      return TABLE_MANAGEMENT_URL + "/freetables";
    }

    /**
     * @param id Table id
     * @param newState New state
     * @return Url
     */
    public final static String markTableAsUrl(Long id, TableState newState) {

      return TABLE_URL + "/" + id + "/marktableas/" + newState;
    }

    /**
     * @param id Table id
     * @return Url
     */
    public final static String isTableReleasableUrl(Long id) {

      return TABLE_URL + "/" + id + "/istablereleasable/";

    }

  }

  /**
   * Offermanagement Urls
   *
   * @author arklos
   */
  public static class Offermanagement {
    /**
     *
     */
    public static final String OFFER_MANAGEMENT_URL = "/services/rest/offermanagement/v1";

    /**
     * Offer Urls
     *
     * @author arklos
     */
    public static class Offer {
      /**
     *
     */
      public static final String OFFER_URL = OFFER_MANAGEMENT_URL + "/offer";

      /**
       * @param id Offer id
       * @return Url
       */
      public final static String getGetOfferUrl(Long id) {

        return OFFER_URL + "/" + id;
      }

      /**
       * @return Url
       */
      public final static String getCreateOfferUrl() {

        return OFFER_URL + "/";
      }

      /**
       * @param id Offer id
       * @return Url
       */
      public final static String getUpdateOfferUrl(Long id) {

        return OFFER_URL + "/" + id;
      }

      /**
       * @return Url
       */
      public final static String getGetAllOffersUrl() {

        return OFFER_URL + "/";
      }

      /**
       * @param id Offer id
       * @return Url
       */
      public static final String getDeleteOfferUrl(Long id) {

        return OFFER_URL + "/" + id;
      }

      /**
       * @param sortBy {@link OfferSortBy}
       * @return Url
       */
      public static final String getFilteredOffers(OfferSortBy sortBy) {

        return OFFER_MANAGEMENT_URL + "/sortby/" + sortBy;
      }
    }

    /**
     * Product Urls
     *
     * @author arklos
     */
    public static class Product {
      /**
       *
       */
      public static final String PRODUCT_URL = OFFER_MANAGEMENT_URL + "/product";

      /**
       * @return Url
       */
      public static final String getGetAllProductsUrl() {

        return PRODUCT_URL + "/";
      }

      /**
       * @return Url
       */
      public static final String getCreateProductUrl() {

        return PRODUCT_URL + "/";
      }

      /**
       * @return Url
       */
      public static final String getGetAllMealsUrl() {

        return PRODUCT_URL + "/meal";
      }

      /**
       * @return Url
       */
      public static final String getGetAllDrinksUrl() {

        return PRODUCT_URL + "/drink";
      }

      /**
       * @return Url
       */
      public static final String getGetAllSideDishesUrl() {

        return PRODUCT_URL + "/side";
      }

      /**
       * @param id Product id
       * @return Url
       */
      public static final String getFindProductUrl(Long id) {

        return PRODUCT_URL + "/" + id;
      }

      /**
       * @param id Product id
       * @return Url
       */
      public static final String getUpdateProductUrl(Long id) {

        return PRODUCT_URL + "/" + id;
      }

      /**
       * @param id Product id
       * @return Url
       */
      public static final String getIsProductInUseByOfferUrl(Long id) {

        return PRODUCT_URL + "/" + id + "/inuse";
      }

      /**
       * @param id Product id
       * @return Url
       */
      public static final String getDeleteProductUrl(Long id) {

        return PRODUCT_URL + "/" + id;
      }

      /**
       * @param id Product id
       * @return Url
       */
      public static final String getGetProductPictureUrl(Long id) {

        return PRODUCT_URL + "/" + id + "/picture";
      }

      /**
       * @param id Product id
       * @return Url
       */
      public static final String getSetProductPictureUrl(Long id) {

        return PRODUCT_URL + "/" + id + "/picture";
      }

      /**
       * @param id Product id
       * @return Url
       */
      public static final String getDeleteProductPictureUrl(Long id) {

        return PRODUCT_URL + "/" + id + "/picture";
      }

      /**
       * @param sortBy {@link ProductSortBy}
       * @return Url
       */
      public static final String getFilteredProducts(ProductSortBy sortBy) {

        return PRODUCT_URL + "/sortby/" + sortBy;
      }

      /**
       * @param id Product id
       * @param revision number
       * @return Url
       */
      public static String getFindProductByRevisionUrl(Long id, Long revision) {

        return PRODUCT_URL + "/" + id + "/" + revision;
      }

    }
  }

}
