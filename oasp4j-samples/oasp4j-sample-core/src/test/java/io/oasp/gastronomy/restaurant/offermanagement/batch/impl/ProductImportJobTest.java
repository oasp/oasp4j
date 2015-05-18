package io.oasp.gastronomy.restaurant.offermanagement.batch.impl;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import javax.inject.Inject;

import org.junit.Assert;
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
public class ProductImportJobTest extends AbstractSpringBatchIntegrationTest {

  @Inject
  private Job productImportJob;

  @Test
  public void testEndToEnd() throws Exception {

    JobExecution jobExecution = getJobLauncherTestUtils(this.productImportJob).launchJob();

    Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
  }
}
