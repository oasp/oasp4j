package io.oasp.gastronomy.restaurant.general.common;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 *
 * @author agreul
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("db-plain")
public abstract class AbstractSpringIntegrationTest extends Assert {

  @Inject
  private ConfigurableApplicationContext context;

  private static ConfigurableApplicationContext lastContext;

  /**
   *
   */
  @After
  public void onTearDown() {

    lastContext = this.context;
  }

  /**
   *
   */
  @AfterClass
  public static void onClassTearDown() {

    lastContext.close();
  }

}
