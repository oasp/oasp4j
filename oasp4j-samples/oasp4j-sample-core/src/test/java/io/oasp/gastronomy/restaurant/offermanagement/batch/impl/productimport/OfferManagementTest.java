package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import static org.junit.Assert.assertEquals;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferSortByHitEntry;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;
import io.oasp.module.jpa.common.api.to.OrderDirection;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is the test-case of {@link Offermanagement}
 *
 * @author cmammado
 * @since dev
 */
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_LOGIC })
public class OfferManagementTest extends AbstractSpringIntegrationTest {

  @Inject
  private Offermanagement offerManagement;

  /**
   * Tests if the {@link Offer} is filtered correctly.
   */
  @Test
  public void testFindOffersFiltered() {

    // OfferEto offer = new OfferEto();
    // OfferFilter filter = new OfferFilter(1L, 5L, 10L, new Money(4), new Money(7));

    OfferFilter filter = new OfferFilter();
    filter.setMealId(1L);
    filter.setMinPrice(new Money(1));
    filter.setMaxPrice(new Money(7));

    OfferSortBy sort = new OfferSortBy();
    sort.setSortByEntry(OfferSortByHitEntry.PRICE);
    List<OfferEto> offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 3);
    assertEquals(offerEntity.get(0).getPrice(), new Money(1.23));
    assertEquals(offerEntity.get(1).getPrice(), new Money(4.23));
    assertEquals(offerEntity.get(2).getPrice(), new Money(6.99));

    sort.setOrderBy(OrderDirection.DESC);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.get(0).getPrice(), new Money(6.99));
    assertEquals(offerEntity.get(1).getPrice(), new Money(4.23));
    assertEquals(offerEntity.get(2).getPrice(), new Money(1.23));

    sort.setSortByEntry(OfferSortByHitEntry.DRINK);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 3);
    assertEquals(offerEntity.get(0).getPrice(), new Money(4.23));
    assertEquals(offerEntity.get(1).getPrice(), new Money(6.99));
    assertEquals(offerEntity.get(2).getPrice(), new Money(1.23));

    sort.setOrderBy(OrderDirection.ASC);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 3);
    assertEquals(offerEntity.get(0).getPrice(), new Money(1.23));
    assertEquals(offerEntity.get(1).getPrice(), new Money(6.99));
    assertEquals(offerEntity.get(2).getPrice(), new Money(4.23));

    sort.setSortByEntry(OfferSortByHitEntry.SIDEDISH);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 3);
    assertEquals(offerEntity.get(0).getPrice(), new Money(1.23));
    assertEquals(offerEntity.get(1).getPrice(), new Money(6.99));
    assertEquals(offerEntity.get(2).getPrice(), new Money(4.23));

    sort.setSortByEntry(OfferSortByHitEntry.DESCRIPTION);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 3);
    assertEquals(offerEntity.get(0).getDescription(), "Description of Schnitzel-Menü");
    assertEquals(offerEntity.get(1).getDescription(), "Description of Schnitzel-Menü");
    assertEquals(offerEntity.get(2).getDescription(), "Description of Schnitzel-Menü");

    sort.setSortByEntry(OfferSortByHitEntry.ID);
    sort.setOrderBy(OrderDirection.ASC);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.get(0).getPrice(), new Money(6.99));
    assertEquals(offerEntity.get(1).getPrice(), new Money(1.23));
    assertEquals(offerEntity.get(2).getPrice(), new Money(4.23));

    filter.setDrinkId(9L);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 1);
    assertEquals(offerEntity.get(0).getPrice(), new Money(4.23));

    filter.setSideDishId(6L);
    offerEntity = this.offerManagement.findOffersFiltered(filter, sort);
    assertEquals(offerEntity.size(), 1);
    assertEquals(offerEntity.get(0).getId(), new Long(7));
    assertEquals(offerEntity.get(0).getDescription(), "Description of Schnitzel-Menü");
    assertEquals(offerEntity.get(0).getDrinkId(), new Long(9));
    assertEquals(offerEntity.get(0).getMealId(), new Long(1));
    assertEquals(offerEntity.get(0).getSideDishId(), new Long(6));

  }

}
