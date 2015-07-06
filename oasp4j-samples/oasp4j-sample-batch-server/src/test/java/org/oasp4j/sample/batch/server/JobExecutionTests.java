package org.oasp4j.sample.batch.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.admin.service.SimpleJobServiceFactoryBean;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Unit test for simple App.
 */
@ContextConfiguration(locations = { "classpath:/META-INF/spring/batch/jobs/*.xml",
"classpath:/META-INF/spring/batch/override/manager/*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@EnableBatchProcessing
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobExecutionTests {

  @Inject
  private Job productImportJob;

  @Inject
  private JobExplorer jobExplorer;

  @Inject
  private JobLauncher jobLauncher;

  @Inject
  private JobRepository jobRepository;

  @Inject
  private JobRegistry jobRegistry;

  @Inject
  private DataSource dataSource;

  @Inject
  private PlatformTransactionManager transactionManager;

  private JobService jobService;

  private SimpleJobServiceFactoryBean simpleJobServiceFactoryBean;

  private JdbcTemplate jdbcTemplate;

  private Map<String, String> params = new Hashtable<String, String>();

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public JobExecutionTests() {

  }

  @Before
  public void setup() {

    try {
      this.simpleJobServiceFactoryBean = new SimpleJobServiceFactoryBean();
      this.simpleJobServiceFactoryBean.setDataSource(this.dataSource);
      this.simpleJobServiceFactoryBean.setJobExplorer(this.jobExplorer);
      this.simpleJobServiceFactoryBean.setJobLauncher(this.jobLauncher);
      this.simpleJobServiceFactoryBean.setJobRepository(this.jobRepository);
      this.simpleJobServiceFactoryBean.setJobLocator(this.jobRegistry);
      this.simpleJobServiceFactoryBean.setTransactionManager(this.transactionManager);
      this.simpleJobServiceFactoryBean.afterPropertiesSet();
      this.jobService = this.simpleJobServiceFactoryBean.getObject();
      // job parameters
      this.params.put("drinks.file", "classpath:drinks.csv");
      this.params.put("meals.file", "classpath:meals.csv");

    } catch (Exception e) {
      // TODO logging
    }

  }

  @Autowired
  public void setDataSource(DataSource dataSource) {

    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Test
  public void testJobService() throws Exception {

    assertNotNull(this.jobService);
  }

  @Test
  public void testJobAvailable() throws Exception {

    assertTrue(this.jobService.countJobs() > 0);
  }

  @Test
  public void testLaunchJob() throws Exception {

    JobExecution jobExecution = this.jobService.launch(this.productImportJob.getName(), getJobParameters(this.params));
    assertNotNull(jobExecution);
    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
  }

  @Test
  public void testListJobEecutions() throws Exception {

    Collection<org.springframework.batch.core.JobExecution> listExecutions = this.jobService.listJobExecutions(0, 2);
    assertNotNull(listExecutions);
    assertTrue(listExecutions.size() > 0);
  }

  private JobParameters getJobParameters(Map<String, String> params) {

    JobParametersBuilder builder = new JobParametersBuilder();
    Iterator<String> keys = params.keySet().iterator();
    while (keys.hasNext()) {
      String key = keys.next();
      builder.addString(key, params.get(key));
    }
    return builder.toJobParameters();
  }

}
