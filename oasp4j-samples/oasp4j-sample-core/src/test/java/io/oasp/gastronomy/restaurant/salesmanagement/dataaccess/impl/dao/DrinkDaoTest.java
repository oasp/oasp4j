package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.DrinkDao;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class to test the {@link DrinkDao}.
 *
 * @author jmetzler
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_DATA_ACCESS })
@Transactional
public class DrinkDaoTest extends AbstractSpringIntegrationTest {

  @Inject
  private DrinkDao drinkDao;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Test to check if the DrinkEntity is audited.
   */
  @Test
  @Rollback(false)
  public void checkAudit() {

    // create drink
    DrinkEntity drink = new DrinkEntity();
    drink.setAlcoholic(false);
    String description = "some description";
    drink.setDescription(description);
    drink.setName("some name");
    assertNull(drink.getId());
    this.drinkDao.save(drink);

    // create another drink
    DrinkEntity drink2 = new DrinkEntity();
    drink2.setAlcoholic(false);
    String description2 = "some description2";
    drink2.setDescription(description2);
    drink2.setName("some name");
    assertNull(drink2.getId());
    this.drinkDao.save(drink2);
    assertNotNull(drink.getId());

    // update first drink
    long id = drink.getId();
    drink.setAlcoholic(true);
    drink.setDescription("some changed description");
    this.drinkDao.save(drink);

    AuditReader auditReader = AuditReaderFactory.get(this.entityManager);

    assertTrue(auditReader.isEntityClassAudited(DrinkEntity.class));

    List<Number> revisions = auditReader.getRevisions(DrinkEntity.class, id);
    assertEquals(1, revisions.size());

    List<Number> history = this.drinkDao.getRevisionHistory(drink);
    assertEquals(1, history.size());

  }

  /**
   * @param entityManager the entityManager to set
   */
  public void setEntityManager(EntityManager entityManager) {

    this.entityManager = entityManager;
  }
}
