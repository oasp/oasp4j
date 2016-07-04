package io.oasp.gastronomy.restaurant.staffmanagement.batch.impl.staffimport;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootBatchApp;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;

/**
 * This class tests the behavior for importing many rows of batch data (1000 at the moment). It also explains the use of
 * {@code setCustomEditors} of {@link BeanWrapperFieldSetMapper} by converting the field 'role' from String to type
 * {@Link Role}. All configuration is done using Java Config (Annotations) in {@link StaffImportConfig}.
 *
 *
 * @author sroeger
 */
@SpringApplicationConfiguration(classes = { SpringBootBatchApp.class, StaffImportConfig.class })
@WebAppConfiguration
@TestPropertySource(properties = { "flyway.locations=db/migration", "logging.level.io=WARN", "logging.level.org=WARN" })
public class StaffImportJobTest extends AbstractSpringBatchIntegrationTest {

  @Inject
  private Job staffImportJob;

  @Inject
  private Staffmanagement staffmanagement;

  @Inject
  private Flyway flyway;

  /**
   * @throws Exception thrown by JobLauncherTestUtils
   */
  @Test
  public void testJob() throws Exception {

    // prepare database
    this.flyway.clean();
    this.flyway.migrate();

    // given
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    jobParameterBuilder.addString("pathToFile", "staffImportJobTest/staff.csv");
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    // when
    JobExecution jobExecution = getJobLauncherTestUtils(this.staffImportJob).launchJob(jobParameters);

    // then
    assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    StaffMemberEto testMember = this.staffmanagement.findStaffMember(100L);
    assertThat(testMember.getRole().getName()).isEqualTo("Cook");
    assertThat(this.staffmanagement.findAllStaffMembers().size()).isEqualTo(2003);

  }

}
