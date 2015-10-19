package io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.billexport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

/**
 * End-To-End test job "import offer management from csv"
 *
 * @author jczas
 */
@ContextConfiguration(locations = { ApplicationConfigurationConstants.BEANS_BATCH })
public class BillExportJobTest extends AbstractSpringBatchIntegrationTest {
  private static final Logger LOG = LoggerFactory.getLogger(BillExportJobTest.class);

  @Inject
  private Job billExportJob;

  @Test
  public void testJob() throws Exception {

    // setup test data
    this.flyway.importTestData("classpath:BillExportJobTest/setup/db");

    // configure job
    File targetPath = new File("./tmp/bills.csv");
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    jobParameterBuilder.addString("bills.file", targetPath.getPath());
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    // run job
    JobExecution jobExecution = getJobLauncherTestUtils(this.billExportJob).launchJob(jobParameters);

    // check results
    // - job status
    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

    // - exported data
    assertTrue(targetPath.exists());
    assertTrue(FileUtils.contentEquals(targetPath,
        new ClassPathResource("BillExportJobTest/expected/bills.csv").getFile()));
  }
}
