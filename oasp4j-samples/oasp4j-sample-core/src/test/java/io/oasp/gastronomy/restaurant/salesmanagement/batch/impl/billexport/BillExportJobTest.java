package io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.billexport;

import static org.junit.Assert.assertEquals;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.test.context.ContextConfiguration;

/**
 * End-To-End test if job "import offer management from csv"
 *
 * @author jczas
 */
@ContextConfiguration(locations = { ApplicationConfigurationConstants.BEANS_BATCH })
public class BillExportJobTest extends AbstractSpringBatchIntegrationTest {

  @Inject
  private Job billExportJob;

  @Test
  public void shouldExportBills() throws Exception {

    JobExecution jobExecution = getJobLauncherTestUtils(this.billExportJob).launchJob();

    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
  }
}
