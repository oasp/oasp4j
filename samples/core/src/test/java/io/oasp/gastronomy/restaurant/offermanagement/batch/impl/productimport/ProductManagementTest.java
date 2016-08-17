package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.TestUtil;
import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.test.SampleCreator;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.ProductSortByHitEntry;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.module.jpa.common.api.to.OrderDirection;
import io.oasp.module.test.common.base.ComponentTest;
import io.oasp.module.test.common.helper.api.DbTestHelper;

/**
 * This is the test case of {@ProductDaoImpl}
 *
 * @author cmammado, shuber
 * @since dev
 */
@SpringApplicationConfiguration(classes = { SpringBootApp.class })
@WebAppConfiguration
public class ProductManagementTest extends ComponentTest {

  @Inject
  private Offermanagement offerManagement;

  /**
   * Login
   */
  @Override
  protected void doSetUp() {

    super.doSetUp();
    TestUtil.login("waiter", PermissionConstants.FIND_OFFER);
  }

  /**
   * Logout
   */
  @Override
  protected void doTearDown() {

    super.doTearDown();
    TestUtil.logout();
  }

  /**
   * Tests if the {@link Product} is filtered correctly.
   */
  @Test
  public void testFindProductsFiltered() {

    ProductFilter filter = new ProductFilter();
    filter.setFetchMeals(true);
    ProductSortBy sort = new ProductSortBy();

    List<ProductEto> products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(), SampleCreator.NUMBER_OF_MEALS);

    filter.setFetchDrinks(true);
    filter.setFetchMeals(false);
    sort.setSortByEntry(ProductSortByHitEntry.DESCRIPTION);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(), SampleCreator.NUMBER_OF_DRINKS);
    assertEquals(products.get(0).getId(), new Long(13));
    assertEquals(products.get(1).getId(), new Long(12));
    assertEquals(products.get(2).getId(), new Long(11));
    assertEquals(products.get(3).getId(), new Long(14));

    filter.setFetchSideDishes(true);
    filter.setFetchDrinks(false);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(), SampleCreator.NUMBER_OF_SIDEDISHES);
    assertEquals(products.get(0).getId(), new Long(9));
    assertEquals(products.get(1).getId(), new Long(10));
    assertEquals(products.get(2).getId(), new Long(7));
    assertEquals(products.get(3).getId(), new Long(8));

    filter.setFetchMeals(true);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(), SampleCreator.NUMBER_OF_MEALS + SampleCreator.NUMBER_OF_SIDEDISHES);

    filter.setFetchMeals(true);
    filter.setFetchDrinks(true);
    sort.setSortByEntry(ProductSortByHitEntry.ID);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(),
        SampleCreator.NUMBER_OF_MEALS + SampleCreator.NUMBER_OF_SIDEDISHES + SampleCreator.NUMBER_OF_DRINKS);
    assertEquals(products.get((int) (SampleCreator.SAMPLE_MEAL_ID - 1)).getDescription(),
        SampleCreator.SAMPLE_MEAL_DESCRIPTION);
    sort.setOrderBy(OrderDirection.DESC);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.get(5).getDescription(), "Brot");
    assertEquals(products.get(0).getId(), new Long(14));
    assertEquals(products.get(1).getId(), new Long(13));
    assertEquals(products.get(2).getId(), new Long(12));
    assertEquals(products.get(3).getId(), new Long(11));
    setDbNeedsReset(false);
  }

  /**
   * Injects {@link DbTestHelper}.
   */
  @Inject
  public void setDbTestHelper(DbTestHelper dbTestHelper) {

    this.dbTestHelper = dbTestHelper;
  }
}
