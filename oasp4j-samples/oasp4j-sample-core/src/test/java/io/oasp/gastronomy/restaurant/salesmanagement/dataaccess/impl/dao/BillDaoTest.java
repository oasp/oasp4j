package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.BillEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.BillDao;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author mvielsac
 */
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_DATA_ACCESS })
public class BillDaoTest extends AbstractSpringIntegrationTest {

  @Inject
  private BillDao billDao;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Tests if a Bill is persisted correctly. Special focus is on the mapping of {@link Money} and furthermore the query
   * of one of the {@link Money} fields is tested.
   */
  @Test
  public void testPersist() {

    BillEntity bill = new BillEntity();
    bill.setTotal(new Money(42.42));
    bill.setTip(new Money(1.0));
    bill.setPayed(true);
    assertThat(bill.getId()).isNull();
    this.billDao.save(bill);
    assertThat(bill.getId()).isNotNull();
    BillEntity loadedBill = this.billDao.findOne(bill.getId());
    assertThat(bill).isEqualTo(loadedBill);

    TypedQuery<BillEntity> query =
        this.entityManager.createQuery("SELECT b from BillEntity b where b.total > 43", BillEntity.class);
    List<BillEntity> resultList = query.getResultList();
    assertThat(resultList.isEmpty()).isTrue();

    query = this.entityManager.createQuery("SELECT b from BillEntity b where b.total < 43", BillEntity.class);
    resultList = query.getResultList();
    assertThat(!resultList.isEmpty()).isTrue();

  }

}
