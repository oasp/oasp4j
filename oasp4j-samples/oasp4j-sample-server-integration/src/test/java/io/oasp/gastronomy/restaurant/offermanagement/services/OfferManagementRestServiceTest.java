package io.oasp.gastronomy.restaurant.offermanagement.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import io.oasp.AbstractDBRollbackTest;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.test.general.AppProperties;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.LoginCredentials;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.RestUrls;
import io.oasp.gastronomy.restaurant.test.general.TestData;
import io.oasp.gastronomy.restaurant.test.general.TestData.Additional;
import io.oasp.gastronomy.restaurant.test.general.TestData.DB;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;
import io.oasp.gastronomy.restaurant.test.general.webclient.WebClientWrapper;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * OfferManagement rest service test
 * 
 * @author arklos
 */
@Ignore
public class OfferManagementRestServiceTest extends AbstractDBRollbackTest {

  private WebClientWrapper waiter = new WebClientWrapper(LoginCredentials.waiterUsername,
      LoginCredentials.waiterPassword);

  private WebClientWrapper chief = new WebClientWrapper(AppProperties.LoginCredentials.chiefUsername,
      LoginCredentials.chiefPassword);

  /**
   * Test get offer
   */
  @Test
  public void getOfferTest() {

    Long id = DB.OFFER_1.getId();
    ResponseData<OfferEto> offer = this.waiter.get(RestUrls.OfferManagement.Offer.getGetOfferUrl(id), OfferEto.class);
    assertThat(offer.getResponse().getStatus(), is(200));
    assertThat(offer.getResponseObject().getId(), is(id));

    id = TestData.DB.OFFER_2.getId();
    offer = this.waiter.get(RestUrls.OfferManagement.Offer.getGetOfferUrl(id), OfferEto.class);
    assertThat(offer.getResponse().getStatus(), is(200));
    assertThat(offer.getResponseObject().getId(), is(id));

  }

  /**
   * Test update offer
   */
  @Test
  @Ignore
  public void updateOfferTest() {

    Long id = Additional.CHANGED_OFFER_1.getId();
    this.chief.put(RestUrls.OfferManagement.Offer.getUpdateOfferUrl(id), Additional.CHANGED_OFFER_1);
    ResponseData<OfferEto> offer = this.chief.get(RestUrls.OfferManagement.Offer.getGetOfferUrl(id), OfferEto.class);
    assertThat(offer.getResponse().getStatus(), is(200));
    assertThat(offer.getResponseObject().getName(), is(Additional.CHANGED_OFFER_1.getName()));
  }

  /**
   * Test get all offer
   */
  @Test
  @Ignore
  public void getAllOffersTest() {

    List<ResponseData<OfferEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Offer.getGetAllOffersUrl(), OfferEto.class);
    assertThat(offer.size(), is(TestData.DB.ALL_OFFERS.size()));
  }

  /**
   * Test create product
   */
  @Test
  @Ignore
  public void createProduct() {

    this.chief.post(RestUrls.OfferManagement.Product.getCreateProductUrl(), TestData.Additional.MEAL);
    List<ResponseData<ProductEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllProductsUrl(), ProductEto.class);
    assertThat(offer.size(), is(DB.ALL_PRODUCTS.size() + 1));
  }

  /**
   * Test get all products
   */
  @Test
  public void getAllProductsTest() {

    List<ResponseData<ProductEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllProductsUrl(), ProductEto.class);
    assertThat(offer.size(), is(DB.ALL_PRODUCTS.size()));
  }

  /**
   * Test get all meals
   */
  @Test
  @Ignore
  public void getAllMealsTest() {

    List<ResponseData<MealEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllMealsUrl(), MealEto.class);
    assertThat(offer.size(), is(DB.ALL_MEALS.size()));
  }

  /**
   * Test get all drinks
   */
  @Test
  public void getAllDrinksTest() {

    List<ResponseData<DrinkEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllDrinksUrl(), DrinkEto.class);
    assertThat(offer.size(), is(DB.ALL_DRINKS.size()));
  }

  /**
   * Test get all sidedishes
   */
  @Test
  public void getAllSideDishesTest() {

    List<ResponseData<SideDishEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllSideDishesUrl(), SideDishEto.class);
    assertThat(offer.size(), is(DB.ALL_SIDE_DISHES.size()));
  }

  /**
   * Test delete offer
   */
  @Test
  @Ignore
  public void deleteOfferTest() {

    List<ResponseData<OfferEto>> offers =
        this.chief.getAll(RestUrls.OfferManagement.Offer.getGetAllOffersUrl(), OfferEto.class);

    this.chief.delete(RestUrls.OfferManagement.Offer.getDeleteOfferUrl(DB.OFFER_1.getId()));
    offers = this.chief.getAll(RestUrls.OfferManagement.Offer.getGetAllOffersUrl(), OfferEto.class);
    assertThat(offers.size(), is(DB.ALL_OFFERS.size() - 1));
  }

  /**
   * Test find product
   */
  @Test
  @Ignore
  public void findProductTest() {

    long id = DB.MEAL_1.getId();
    ResponseData<ProductEto> offer =
        this.waiter.get(RestUrls.OfferManagement.Product.getFindProductUrl(id), ProductEto.class);
    assertThat(offer.getResponse().getStatus(), is(200));
    assertThat(offer.getResponseObject().getId(), is(id));

    id = DB.DRINK_1.getId();
    offer = this.waiter.get(RestUrls.OfferManagement.Product.getFindProductUrl(id), ProductEto.class);
    assertThat(offer.getResponse().getStatus(), is(200));
    assertThat(offer.getResponseObject().getId(), is(id));

  }

  /**
   * Test update product
   */
  @Test
  public void updateProductTest() {

    long id = Additional.CHANGED_MEAL_1.getId();
    this.chief.put(RestUrls.OfferManagement.Product.getUpdateProductUrl(id), Additional.CHANGED_MEAL_1);
    ResponseData<ProductEto> product =
        this.chief.get(RestUrls.OfferManagement.Product.getFindProductUrl(id), ProductEto.class);
    assertThat(product.getResponse().getStatus(), is(200));
    assertThat(product.getResponseObject().getName(), is(Additional.CHANGED_MEAL_1.getName()));
  }

  /**
   * Test is product in use
   */
  @Test
  public void isProductInUseTest() {

    ResponseData<Boolean> isproductInUse =
        this.waiter.get(RestUrls.OfferManagement.Product.getIsProductInUseByOfferUrl(DB.MEAL_1.getId()), Boolean.class);
    assertThat(isproductInUse.getResponseObject(), is(true));
  }

  /**
   * Test is get filtered offers
   */
  // @Test
  public void getFilteredOffersTest() {

    // TODO: Clearify how this call works and write a proper test
    OfferSortBy sortBy = new OfferSortBy();

    List<ResponseData<OfferEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Offer.getFilteredOffers(sortBy), OfferEto.class);
    assertThat(offer.size(), is(DB.ALL_OFFERS.size()));
  }

  /**
   * Test is get filtered offers
   */
  // @Test
  public void getFilteredProductsTest() {

    // TODO: Clearify how this call works and write a proper test
    ProductSortBy sortBy = new ProductSortBy();

    List<ResponseData<ProductEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getFilteredProducts(sortBy), ProductEto.class);
    assertThat(offer.size(), is(DB.ALL_PRODUCTS.size()));
  }
}
