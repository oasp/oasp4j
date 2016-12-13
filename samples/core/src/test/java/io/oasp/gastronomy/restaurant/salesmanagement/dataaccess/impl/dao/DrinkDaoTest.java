package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.api.builders.DrinkEntityBuilder;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.DrinkDao;
import io.oasp.module.jpa.dataaccess.api.RevisionMetadata;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * Test class to test the {@link DrinkDao}.
 *
 */

@SpringApplicationConfiguration(classes = { SpringBootApp.class })
@WebAppConfiguration
public class DrinkDaoTest extends ComponentTest {

  @Inject
  DrinkDaoTestBean testBean;

  /**
   * Test to check if the DrinkEntity is audited. All steps are executed in separate transactions in order to actually
   * write to the database. Otherwise hibernate envers won't work.
   */
  @Test
  public void checkAudit() {

    DrinkEntity drink = this.testBean.create();
    long drinkId = drink.getId();
    this.testBean.update(drinkId);
    this.testBean.verify(drinkId);

  }

  /**
   *
   * This type provides methods in a transactional environment for the containing test class. All methods, annotated
   * with the {@link Transactional} annotation, are executed in separate transaction, thus one test case can execute
   * multiple transactions.
   *
     */
  @Named
  static class DrinkDaoTestBean {

    private final String description = "some description";

    private final String changedDescription = "some changed description";

    @Inject
    private DrinkDao drinkDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public DrinkEntity create() {

      DrinkEntity drink =
          new DrinkEntityBuilder().alcoholic(false).description(this.description).name("some name").createNew();
      assertThat(drink.getId()).isNull();
      drink = this.drinkDao.save(drink);
      return drink;
    }

    @Transactional
    public void update(long id) {

      DrinkEntity drink = this.drinkDao.find(id);
      drink.setAlcoholic(true);
      drink.setDescription(this.changedDescription);
      this.drinkDao.save(drink);
    }

    @Transactional
    public void verify(long id) {

      AuditReader auditReader = AuditReaderFactory.get(this.entityManager);

      assertThat(auditReader.isEntityClassAudited(DrinkEntity.class)).isTrue();

      List<Number> revisions = auditReader.getRevisions(DrinkEntity.class, id);
      assertThat(2).isEqualTo(revisions.size());

      List<RevisionMetadata> history = this.drinkDao.getRevisionHistoryMetadata(id);
      assertThat(2).isEqualTo(history.size());

      // get first revision
      Number rev = history.get(0).getRevision();
      DrinkEntity drink = this.drinkDao.load(id, rev);
      assertThat(drink.getDescription()).isEqualTo(this.description);

      // get second revision
      rev = history.get(1).getRevision();
      drink = this.drinkDao.load(id, rev);
      assertThat(drink.getDescription()).isEqualTo(this.changedDescription);
    }

    /**
     * @param entityManager the entityManager to set
     */
    public void setEntityManager(EntityManager entityManager) {

      this.entityManager = entityManager;
    }
  };
}
