package io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.billexport;

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
import org.springframework.batch.test.AssertFile;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootBatchApp;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;

/**
 * End-To-End test job "import offer management from csv"
 *
 * @author jczas, shuber
 */
@SpringApplicationConfiguration(classes = { SpringBootBatchApp.class }, locations = {
"classpath:/config/app/batch/beans-billexport.xml" })
@WebAppConfiguration
@TestPropertySource(properties = {
"flyway.locations=filesystem:src/test/resources/db/batch/BillExportJobTest,filesystem:src/test/resources/db/default" })
public class BillExportJobTest extends AbstractSpringBatchIntegrationTest {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractSpringBatchIntegrationTest.class);

  @Inject
  private Job billExportJob;

  @Override
  public void doSetUp() {

    super.doSetUp();
    doDatabaseSetUp();
  }

  @Override
  public void doDatabaseSetUp() {

    if (dbNeedsReset()) {
      getDbTestHelper().resetDatabase("0006");
    }
    setDbNeedsReset(true);
  }

  /**
   * @throws Exception thrown by JobLauncherTestUtils
   */
  @Test
  public void testJob() throws Exception {

    // setup test data
    File targetFile = new File("./tmp/bills.csv");
    File expectedFile = new ClassPathResource("BillExportJobTest/expected/bills.csv").getFile();

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
    assertThat(expectedFile.exists()).isTrue();

    logFileContent(expectedFile);
    logFileContent(targetFile);

    AssertFile.assertFileEquals(expectedFile, targetFile);
  }

  private void logFileContent(File file) {

    try {
      LOG.debug("--> file content: " + file.getPath());
      BufferedReader br = null;

      String line;
      try {
        br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null) {
          LOG.debug(line);
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
  }
}
