package io.oasp.gastronomy.restaurant.general.common;

import static org.junit.Assert.fail;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.DatabaseMigrator;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base class for all spring batch integration tests. It helps to do End-to-End job tests.
 *
 * @author ABIELEWI
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("db-plain")
public abstract class AbstractSpringBatchIntegrationTest {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractSpringBatchIntegrationTest.class);

  private static final String TMP_DIR = "./tmp";

  @Inject
  protected DatabaseMigrator flyway;

  @Inject
  private JobLauncher jobLauncher;

  /**
   * @return jobLauncherTestUtils
   */
  public JobLauncherTestUtils getJobLauncherTestUtils(Job job) {

    JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
    jobLauncherTestUtils.setJob(job);
    jobLauncherTestUtils.setJobLauncher(this.jobLauncher);

    return jobLauncherTestUtils;
  }

  @Before
  public void setup() {

    LOG.debug("Delete tmp directory");
    try {
      FileUtils.deleteDirectory(new File(TMP_DIR));
    } catch (IOException e) {
      fail();
    }
  }
}
