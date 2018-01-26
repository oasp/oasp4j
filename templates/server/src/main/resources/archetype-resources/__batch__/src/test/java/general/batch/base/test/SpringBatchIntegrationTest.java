package ${package}.general.batch.base.test;

import java.io.File;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ${package}.general.common.api.security.UserData;
import ${package}.general.common.base.test.TestUtil;
import ${package}.general.dataaccess.base.DatabaseMigrator;
import io.oasp.module.security.common.api.accesscontrol.AccessControlPermission;
import io.oasp.module.security.common.base.accesscontrol.AccessControlGrantedAuthority;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * Base class for all spring batch integration tests. It helps to do End-to-End job tests.
 */
public abstract class SpringBatchIntegrationTest extends ComponentTest {

  private static final Logger LOG = LoggerFactory.getLogger(SpringBatchIntegrationTest.class);

  /**
   * database migration helper
   */
  @Inject
  protected DatabaseMigrator databaseMigrator;

  @Inject
  private JobLauncher jobLauncher;

  @Override
  protected void doSetUp() {

    super.doSetUp();
    this.databaseMigrator.migrate();
  }

  @Override
  protected void doTearDown() {

    super.doTearDown();
    TestUtil.logout();
  }

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
}
