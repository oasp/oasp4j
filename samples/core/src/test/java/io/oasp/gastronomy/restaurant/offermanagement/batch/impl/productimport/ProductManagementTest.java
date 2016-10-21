package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.TestUtil;
import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.ProductSortByHitEntry;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao.ProductDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.module.jpa.common.api.to.OrderDirection;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * This is the test case of {@link ProductDaoImpl}
 *
 * @since dev
 */
@SpringApplicationConfiguration(classes = { SpringBootApp.class })
@WebAppConfiguration
public class ProductManagementTest extends ComponentTest {

  @Inject
  private Offermanagement offerManagement;

  @Inject
  private Flyway flyway;

  /**
   * Login
   */
  @Before
  public void setUp() {

    this.flyway.clean();
    this.flyway.migrate();
    TestUtil.login("waiter", PermissionConstants.FIND_OFFER);
  }

  /**
   * Logout
   */
  @After
  public void tearDown() {

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
    assertEquals(products.size(), 6);

    filter.setFetchDrinks(true);
    filter.setFetchMeals(false);
    sort.setSortByEntry(ProductSortByHitEntry.DESCRIPTION);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(), 4);
    assertEquals(products.get(0).getId(), new Long(13));
    assertEquals(products.get(1).getId(), new Long(12));
    assertEquals(products.get(2).getId(), new Long(11));
    assertEquals(products.get(3).getId(), new Long(14));

    filter.setFetchSideDishes(true);
    filter.setFetchDrinks(false);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(), 4);
    assertEquals(products.get(0).getId(), new Long(9));
    assertEquals(products.get(1).getId(), new Long(10));
    assertEquals(products.get(2).getId(), new Long(7));
    assertEquals(products.get(3).getId(), new Long(8));

    filter.setFetchMeals(true);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(), 10);

    filter.setFetchMeals(true);
    filter.setFetchDrinks(true);
    sort.setSortByEntry(ProductSortByHitEntry.ID);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.size(), 14);
    assertEquals(products.get(5).getDescription(), "Flammkuchen");
    sort.setOrderBy(OrderDirection.DESC);
    products = this.offerManagement.findProductsFiltered(filter, sort);
    assertEquals(products.get(5).getDescription(), "Brot");
    assertEquals(products.get(0).getId(), new Long(14));
    assertEquals(products.get(1).getId(), new Long(13));
    assertEquals(products.get(2).getId(), new Long(12));
    assertEquals(products.get(3).getId(), new Long(11));

  }
}
