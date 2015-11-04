package io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.billexport;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;

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
  private static final Logger LOG = LoggerFactory.getLogger(AbstractSpringBatchIntegrationTest.class);

  @Inject
  private Job billExportJob;

  /**
   * @throws Exception thrown by JobLauncherTestUtils
   */
  @Test
  public void testJob() throws Exception {

    // setup test data
    this.flyway.importTestData("classpath:BillExportJobTest/setup/db");

    File targetFile = new File("./tmp/bills.csv");
    File exportedFile = new ClassPathResource("BillExportJobTest/expected/bills.csv").getFile();

    // configure job
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    jobParameterBuilder.addString("bills.file", targetFile.getPath());
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    // run job
    JobExecution jobExecution = getJobLauncherTestUtils(this.billExportJob).launchJob(jobParameters);

    // check results
    // - job status
    assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    // - exported data
    assertThat(targetFile.exists()).isTrue();
    assertThat(exportedFile.exists()).isTrue();

    assertThat(getFileContent(exportedFile)).isEqualTo(getFileContent(targetFile));
  }

  private String getFileContent(File file) {

    StringBuffer sb = new StringBuffer();
    try {

      LOG.debug("--> file content: " + file.getPath());
      BufferedReader br = null;

      String line;
      try {
        br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null) {
          LOG.debug(line);
          sb.append(line);
          sb.append(System.lineSeparator());
        }
      } finally {
        if (br != null) {
          br.close();
        }
      }
      LOG.debug("<-- file content: " + file.getPath());
    } catch (IOException e) {
      LOG.debug(e.toString());
      e.printStackTrace();
    }
    return sb.toString();
  }
}
