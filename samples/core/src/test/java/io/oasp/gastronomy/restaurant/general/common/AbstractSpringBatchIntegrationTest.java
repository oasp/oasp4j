package io.oasp.gastronomy.restaurant.general.common;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.DatabaseMigrator;
import io.oasp.module.test.common.base.ComponentTest;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base class for all spring batch integration tests. It helps to do End-to-End job tests.
 *
 * @author jczas
 */
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("db-plain")
public abstract class AbstractSpringBatchIntegrationTest extends ComponentTest {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractSpringBatchIntegrationTest.class);

  /** directory for temporary test files */
  private static final String TMP_DIR = "./tmp";

  /** scripts for all tests db setup */
  private static final String ALL_TESTS_DB_SETUP_DIR = "classpath:AllTests/setup/db";

  /**
   * database migration helper
   */
  @Inject
  protected DatabaseMigrator flyway;

  @Inject
  private JobLauncher jobLauncher;

  /**
   * @param job job to configure
   * @return jobLauncherTestUtils
   */
  public JobLauncherTestUtils getJobLauncherTestUtils(Job job) {

    JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
    jobLauncherTestUtils.setJob(job);
    jobLauncherTestUtils.setJobLauncher(this.jobLauncher);

    return jobLauncherTestUtils;
  }

  /**
   * setup run before all tests
   *
   * @throws Exception throw by FileUtils
   */
  @Before
  public void setup() throws Exception {

    // setup test data
    this.flyway.importTestData(ALL_TESTS_DB_SETUP_DIR);

    LOG.debug("Delete tmp directory");
    FileUtils.deleteDirectory(new File(TMP_DIR));
  }
}
