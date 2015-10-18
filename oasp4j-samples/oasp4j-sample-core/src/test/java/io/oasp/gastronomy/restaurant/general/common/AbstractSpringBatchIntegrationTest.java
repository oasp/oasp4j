package io.oasp.gastronomy.restaurant.general.common;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
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
// TODO abielewi todo_jczas Use MapJobRepositoryFactoryBean
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("db-plain")
public abstract class AbstractSpringBatchIntegrationTest {

  @Inject
  private JobLauncher jobLauncher;

  @Inject
  private JobRepository jobRepository;

  /**
   * @return jobLauncherTestUtils
   */
  public JobLauncherTestUtils getJobLauncherTestUtils(Job job) {

    JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
    jobLauncherTestUtils.setJob(job);
    jobLauncherTestUtils.setJobLauncher(this.jobLauncher);
    jobLauncherTestUtils.setJobRepository(this.jobRepository);

    return jobLauncherTestUtils;
  }

}
