package io.oasp.gastronomy.restaurant.offermanagement.services;

import io.oasp.gastronomy.restaurant.general.common.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.test.config.RestUrls;
import io.oasp.gastronomy.restaurant.test.config.TestData;
import io.oasp.gastronomy.restaurant.test.config.TestData.Additional;
import io.oasp.gastronomy.restaurant.test.config.TestData.DB;
import io.oasp.gastronomy.restaurant.test.general.webclient.ResponseData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * OfferManagement rest service test
 *
 * @author arklos
 */
public class OfferManagementRestServiceTest extends AbstractRestServiceTest {

  /**
   * Test CRUD functionality of offer.
   */
  @Test
  public void crudOfferTest() {

    // get all offers
    List<ResponseData<OfferEto>> offers =
        this.waiter.getAll(RestUrls.OfferManagement.Offer.getGetAllOffersUrl(), OfferEto.class);

    // get unused offer description
    List<String> offerDescriptions = new ArrayList<>();
    for (ResponseData<OfferEto> responseData : offers) {
      offerDescriptions.add(responseData.getResponseObject().getDescription());
    }
    int number = 1;
    String myDescription = "Schnitzel-Menü";
    while (offerDescriptions.contains(myDescription + number)) {
      number += 1;
    }
    myDescription += number;
    offerDescriptions.add(myDescription);

    Long mealId = null;
    Long drinkId = createDrink().getId();
    Long sidedishId = null;

    // create a new offer
    OfferEto createdOffer =
        this.chief.post(TestData.createOffer(null, myDescription, OfferState.NORMAL, mealId, sidedishId, drinkId, 0,
            new Money(new BigDecimal(2.5))), RestUrls.OfferManagement.Offer.getCreateOfferUrl(), OfferEto.class);
    // get the created offer
    ResponseData<OfferEto> offer =
        this.waiter.get(RestUrls.OfferManagement.Offer.getGetOfferUrl(createdOffer.getId()), OfferEto.class);

    assertThat(offer.getResponseObject()).isNotNull();
    assertThat(createdOffer.getDescription()).isEqualTo(offer.getResponseObject().getDescription());
    assertThat(createdOffer.getState()).isEqualTo(offer.getResponseObject().getState());

    // update offer
    OfferState newState = OfferState.SOLDOUT;
    createdOffer.setState(newState);
    OfferEto updatedOffer =
        this.chief.post(createdOffer, RestUrls.OfferManagement.Offer.getCreateOfferUrl(), OfferEto.class);

    assertThat(newState).isEqualTo(updatedOffer.getState());

    // delete created offer
    Response deleteResponse = this.chief.delete(RestUrls.OfferManagement.Offer.getDeleteOfferUrl(createdOffer.getId()));
    assertThat(deleteResponse.getStatus()).isEqualTo(204);

    // check if offer is deleted
    ResponseData<OfferEto> getDeletedOfferResponse =
        this.waiter.get(RestUrls.OfferManagement.Offer.getGetOfferUrl(createdOffer.getId()), OfferEto.class);
    assertThat(getDeletedOfferResponse.getResponseObject()).isNull();
  }

  /**
   * Create and persist a {@link DrinkEto}.
   *
   * @return the DrinkEto
   */
  public DrinkEto createDrink() {

    ProductEto drink = TestData.createDrink(null, "Wasser", null, false);
    DrinkEto response = this.chief.post(drink, RestUrls.OfferManagement.Product.getCreateProductUrl(), DrinkEto.class);
    return response;

  }

  /**
   * Test get offer
   */
  @Test
  @Ignore
  public void getOfferTest() {

    Long id = DB.OFFER_1.getId();
    ResponseData<OfferEto> offer = this.waiter.get(RestUrls.OfferManagement.Offer.getGetOfferUrl(id), OfferEto.class);
    // assertThat(offer.getResponse().getStatus(), is(200));
    assertThat(offer.getResponseObject().getId()).isEqualTo(id);

    id = TestData.DB.OFFER_2.getId();
    offer = this.waiter.get(RestUrls.OfferManagement.Offer.getGetOfferUrl(id), OfferEto.class);
    // assertThat(offer.getResponse().getStatus(), is(200));
    assertThat(offer.getResponseObject().getId()).isEqualTo(id);

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
    assertThat(offer.getResponse().getStatus()).isEqualTo(200);
    assertThat(offer.getResponseObject().getName()).isEqualTo(Additional.CHANGED_OFFER_1.getName());
  }

  /**
   * Test get all offer
   */
  @Test
  public void getAllOffersTest() {

    List<ResponseData<OfferEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Offer.getGetAllOffersUrl(), OfferEto.class);
    // assertThat(offer.size(), is(TestData.DB.ALL_OFFERS.size()));
    for (ResponseData<OfferEto> responseData : offer) {
      assertThat(responseData.getResponse().getStatus()).isEqualTo(200);
    }
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
    assertThat(offer.size()).isEqualTo(DB.ALL_PRODUCTS.size() + 1);
  }

  /**
   * Test get all products
   */
  @Test
  public void getAllProductsTest() {

    List<ResponseData<ProductEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllProductsUrl(), ProductEto.class);
    // assertThat(offer.size(), is(DB.ALL_PRODUCTS.size()));
    for (ResponseData<ProductEto> responseData : offer) {
      assertThat(responseData.getResponse().getStatus()).isEqualTo(200);
    }
  }

  /**
   * Test get all meals
   */
  @Test
  @Ignore
  public void getAllMealsTest() {

    List<ResponseData<MealEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllMealsUrl(), MealEto.class);
    // assertThat(offer.size(), is(DB.ALL_MEALS.size()));
    for (ResponseData<MealEto> responseData : offer) {
      assertThat(responseData.getResponse().getStatus()).isEqualTo(200);
    }
  }

  /**
   * Test get all drinks
   */
  @Test
  public void getAllDrinksTest() {

    List<ResponseData<DrinkEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllDrinksUrl(), DrinkEto.class);
    // assertThat(offer.size(), is(DB.ALL_DRINKS.size()));
    for (ResponseData<DrinkEto> responseData : offer) {
      assertThat(responseData.getResponse().getStatus()).isEqualTo(200);
    }
  }

  /**
   * Test get all sidedishes
   */
  @Test
  public void getAllSideDishesTest() {

    List<ResponseData<SideDishEto>> offer =
        this.waiter.getAll(RestUrls.OfferManagement.Product.getGetAllSideDishesUrl(), SideDishEto.class);
    // assertThat(offer.size(), is(DB.ALL_SIDE_DISHES.size()));
    for (ResponseData<SideDishEto> responseData : offer) {
      assertThat(responseData.getResponse().getStatus()).isEqualTo(200);
    }
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
    assertThat(offers.size()).isEqualTo(DB.ALL_OFFERS.size() - 1);
  }

  /**
   * Test find product
   */
  @Test
  public void findProductTest() {

    Long drinkId = createDrink().getId();
    ResponseData<ProductEto> offer =
        this.waiter.get(RestUrls.OfferManagement.Product.getFindProductUrl(drinkId), ProductEto.class);
    assertThat(offer.getResponse().getStatus()).isEqualTo(200);
    assertThat(offer.getResponseObject().getId()).isEqualTo(drinkId);

    offer = this.waiter.get(RestUrls.OfferManagement.Product.getFindProductUrl(drinkId + 1), ProductEto.class);
    Assert.assertNull(offer.getResponseObject());

  }

  /**
   * Test find product
   */
  @Test
  public void findProductByRevisionTest() {

    // create drink
    ProductEto drink = TestData.createDrink(null, "Wasser", null, false);
    DrinkEto createdDrink =
        this.chief.post(drink, RestUrls.OfferManagement.Product.getCreateProductUrl(), DrinkEto.class);
    // change drink
    createdDrink.setDescription("changed description");
    this.chief.post(createdDrink, RestUrls.OfferManagement.Product.getCreateProductUrl(), DrinkEto.class);

    Long revision = 1L;
    ResponseData<DrinkEto> revisionedProductResponse =
        this.waiter.get(RestUrls.OfferManagement.Product.getFindProductByRevisionUrl(createdDrink.getId(), revision),
            DrinkEto.class);
    DrinkEto revisionedProduct = revisionedProductResponse.getResponseObject();
    Assert.assertEquals(revision.longValue(), revisionedProduct.getRevision().longValue());

  }

  /**
   * Test update product
   */
  @Test
  public void updateProductTest() {

    DrinkEto drink = TestData.createDrink(null, "Wasser", null, false);
    DrinkEto productToUpdate =
        this.chief.post(drink, RestUrls.OfferManagement.Product.getCreateProductUrl(), DrinkEto.class);
    productToUpdate.setName(drink.getName() + "2");
    this.chief.post(productToUpdate, RestUrls.OfferManagement.Product.getCreateProductUrl(), DrinkEto.class);
    ResponseData<ProductEto> product =
        this.chief.get(RestUrls.OfferManagement.Product.getFindProductUrl(productToUpdate.getId()), ProductEto.class);

    assertThat(product.getResponse().getStatus()).isEqualTo(200);
    assertThat(product.getResponseObject().getName()).isEqualTo(productToUpdate.getName());
  }

  /**
   * Test is product in use
   */
  @Test
  public void isProductInUseTest() {

    Long drinkId = createDrink().getId();
    this.chief.post(TestData.createOffer(null, "some description", OfferState.NORMAL, null, null, drinkId, 0,
        new Money(new BigDecimal(2.5))), RestUrls.OfferManagement.Offer.getCreateOfferUrl(), OfferEto.class);
    ResponseData<Boolean> isproductInUse =
        this.waiter.get(RestUrls.OfferManagement.Product.getIsProductInUseByOfferUrl(drinkId), Boolean.class);
    assertThat(isproductInUse.getResponseObject()).isTrue();
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
    assertThat(offer.size()).isEqualTo(DB.ALL_OFFERS.size());
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
    assertThat(offer.size()).isEqualTo(DB.ALL_PRODUCTS.size());
  }
}
