package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import static org.junit.Assert.assertEquals;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.test.context.ContextConfiguration;

/**
 * End-To-End test if job "import offer management from csv"
 *
 * @author jczas
 */
@ContextConfiguration(locations = { ApplicationConfigurationConstants.BEANS_BATCH })
public class ProductImportJobTest extends AbstractSpringBatchIntegrationTest {

  @Inject
  private Job productImportJob;

  @Test
  public void shouldImportProducts() throws Exception {

    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    jobParameterBuilder.addString("drinks.file", "classpath:drinks.csv");
    jobParameterBuilder.addString("meals.file", "classpath:meals.csv");
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    JobExecution jobExecution = getJobLauncherTestUtils(this.productImportJob).launchJob(jobParameters);
    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
  }

}
