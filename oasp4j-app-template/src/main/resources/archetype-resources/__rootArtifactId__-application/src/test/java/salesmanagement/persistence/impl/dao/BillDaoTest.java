#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.persistence.impl.dao;

import ${package}.general.common.api.datatype.Money;
import ${package}.salesmanagement.persistence.api.BillEntity;
import ${package}.salesmanagement.persistence.api.dao.BillDao;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mvielsac
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_PERSISTENCE })
@Transactional
public class BillDaoTest extends Assert {

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
    bill.setTotalAmount(new Money(42.42));
    bill.setTip(new Money(1.0));
    bill.setPayed(true);
    assertNull(bill.getId());
    this.billDao.save(bill);
    assertNotNull(bill.getId());
    BillEntity loadedBill = this.billDao.findOne(bill.getId());
    assertEquals(bill, loadedBill);

    TypedQuery<BillEntity> query =
        this.entityManager.createQuery("SELECT b from Bill b where b.totalAmount > 43", BillEntity.class);
    List<BillEntity> resultList = query.getResultList();
    assertTrue(resultList.isEmpty());

    query = this.entityManager.createQuery("SELECT b from Bill b where b.totalAmount < 43", BillEntity.class);
    resultList = query.getResultList();
    assertTrue(!resultList.isEmpty());

  }

}
