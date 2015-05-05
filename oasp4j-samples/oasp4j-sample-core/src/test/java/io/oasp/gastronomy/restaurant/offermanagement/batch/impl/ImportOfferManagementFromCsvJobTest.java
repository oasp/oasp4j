package io.oasp.gastronomy.restaurant.offermanagement.batch.impl;

import static org.junit.Assert.assertEquals;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * End-To-End test if job "import offer management from csv"
 *
 * @author jczas
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { ApplicationConfigurationConstants.BEANS_BATCH })
// @ContextConfiguration(locations = { ApplicationConfigurationConstants.BEANS_BATCH,
// ApplicationConfigurationConstants.BEANS_LOGIC })
// @ContextConfiguration(classes = { ImportOfferManagementFromCsvJobConfiguration.class })
// , loader = AnnotationConfigContextLoader.class)
// extends AbstractSpringIntegrationTest
public class ImportOfferManagementFromCsvJobTest {

  @Test
  public void testEndToEnd() throws Exception {

    // TODO add oasp and db

    JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();

    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
  }

  @Autowired
  JobLauncherTestUtils jobLauncherTestUtils;
}
