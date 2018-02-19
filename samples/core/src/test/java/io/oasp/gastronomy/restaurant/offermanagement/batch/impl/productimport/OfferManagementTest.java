package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.TestUtil;
import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferSortByHitEntry;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.module.basic.common.api.to.OrderDirection;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * This is the test-case of {@link Offermanagement}
 *
 * @since dev
 */
@SpringBootTest(classes = { SpringBootApp.class })
@WebAppConfiguration
public class OfferManagementTest extends ComponentTest {

  @Inject
  private Offermanagement offerManagement;

  @Inject
  private Flyway flyway;

  /**
   * Login
   */
  @Override
  public void doSetUp() {

    super.doSetUp();
    this.flyway.clean();
    this.flyway.migrate();

    TestUtil.login("waiter", PermissionConstants.FIND_OFFER);
  }

  /**
   * Logout
   */
  @Override
  public void doTearDown() {

    super.doTearDown();
    TestUtil.logout();
  }

  /**
   * Tests if the {@link Offer} is filtered correctly.
   */
  @Test
  public void testFindOffersFiltered() {

    OfferFilter filter = new OfferFilter();
    filter.setDrinkId(12L);
    filter.setMinPrice(new Money(5));
    filter.setMaxPrice(new Money(7));

    OfferSortBy sort = new OfferSortBy();
    sort.setSortByEntry(OfferSortByHitEntry.PRICE);
    List<OfferEto> offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 3);
    assertEquals(offerEntity.get(0).getPrice(), new Money(5.99));
    assertEquals(offerEntity.get(1).getPrice(), new Money(6.23));
    assertEquals(offerEntity.get(2).getPrice(), new Money(6.99));

    sort.setOrderBy(OrderDirection.DESC);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.get(0).getPrice(), new Money(6.99));
    assertEquals(offerEntity.get(1).getPrice(), new Money(6.23));
    assertEquals(offerEntity.get(2).getPrice(), new Money(5.99));

    sort.setOrderBy(OrderDirection.ASC);
    sort.setSortByEntry(OfferSortByHitEntry.ID);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.get(0).getId(), new Long(1));
    assertEquals(offerEntity.get(1).getId(), new Long(6));
    assertEquals(offerEntity.get(2).getId(), new Long(7));

    sort.setSortByEntry(OfferSortByHitEntry.DESCRIPTION);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 3);
    assertEquals(offerEntity.get(0).getDescription(), "Description of Flammkuchen-Menü");
    assertEquals(offerEntity.get(1).getDescription(), "Description of Pizza-Menü");
    assertEquals(offerEntity.get(2).getDescription(), "Description of Schnitzel-Menü");

    sort.setSortByEntry(OfferSortByHitEntry.MEAL);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 3);
    assertEquals(offerEntity.get(0).getMealId(), new Long(6));
    assertEquals(offerEntity.get(1).getMealId(), new Long(5));
    assertEquals(offerEntity.get(2).getMealId(), new Long(1));

    sort.setSortByEntry(OfferSortByHitEntry.ID);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.get(0).getPrice(), new Money(6.99));
    assertEquals(offerEntity.get(1).getPrice(), new Money(6.23));
    assertEquals(offerEntity.get(2).getPrice(), new Money(5.99));

    filter.setMealId(1L);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 1);
    assertEquals(offerEntity.get(0).getPrice(), new Money(6.99));

    filter.setSideDishId(7L);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 1);
    assertEquals(offerEntity.get(0).getId(), new Long(1));
    assertEquals(offerEntity.get(0).getDescription(), "Description of Schnitzel-Menü");
    assertEquals(offerEntity.get(0).getDrinkId(), new Long(12));
    assertEquals(offerEntity.get(0).getMealId(), new Long(1));
    assertEquals(offerEntity.get(0).getSideDishId(), new Long(7));

  }
}
