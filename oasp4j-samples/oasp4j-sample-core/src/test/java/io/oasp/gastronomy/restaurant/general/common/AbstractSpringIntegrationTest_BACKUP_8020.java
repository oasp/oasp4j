<<<<<<< HEAD
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
=======
package io.oasp.gastronomy.restaurant.general.common;

import io.oasp.module.test.common.base.ComponentTest;

import javax.inject.Inject;

import org.junit.After;
import org.junit.AfterClass;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author agreul
 */
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("db-plain")
public abstract class AbstractSpringIntegrationTest extends ComponentTest {

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
>>>>>>> 44c17c8... #199:Cleaned up exisiting test-cases as described
