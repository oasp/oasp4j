package io.oasp.gastronomy.restaurant.general.common;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.DatabaseMigrator;
import io.oasp.gastronomy.restaurant.test.general.AppProperties.LoginCredentials;
import io.oasp.gastronomy.restaurant.test.general.webclient.WebClientWrapper;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Contains reusable test features for rest service tests. Before each test execution the database will be wiped and
 * users will be inserted.
 *
 * @author mbrunnli
 * @author jmetzler
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_DATA_ACCESS,
"classpath:/config/app/dataaccess/beans-db-connector.xml" })
@ActiveProfiles({ "integrationTest", "db-plain" })
public abstract class AbstractRestServiceTest {

  /**
   * WebClientWrapper with access rights of the role waiter.
   */
  protected WebClientWrapper waiter = new WebClientWrapper(LoginCredentials.WAITER_USERNAME,
      LoginCredentials.WAITER_PASSWORD);

  /**
   * WebClientWrapper with access rights of the role chief.
   */
  protected WebClientWrapper chief = new WebClientWrapper(LoginCredentials.CHIEF_USERNAME,
      LoginCredentials.CHIEF_PASSWORD);

  @Inject
  private DatabaseMigrator flyway;

  /**
   * @param flyway the DatabaseMigrator to set
   */
  public void setFlyway(DatabaseMigrator flyway) {

    this.flyway = flyway;
  }

  /**
   * The {@link EntityManager}.
   */
  @Autowired
  public EntityManager entityManager;

  /**
   * The {@link TransactionTemplate}.
   */
  @Autowired
  public TransactionTemplate transactionTemplate;

  /**
   * Clear DB and migrate to the latest migration, including test data.
   */
  @Before
  public void setup() {

    this.flyway.migrate();

  }
}
