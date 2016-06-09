package io.oasp.gastronomy.restaurant.staffmanagement.batch.impl.staffimport;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootBatchApp;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;

/**
 * TODO sroeger This type ... performance test with many rows TODO sroeger custom editor for custom type role add
 * auto_increment to table generation data master data
 *
 *
 * @author sroeger
 * @since dev
 */
@SpringApplicationConfiguration(classes = { SpringBootBatchApp.class, StaffImportConfig.class })
@WebAppConfiguration
@TestPropertySource(properties = { "flyway.locations=db/migration" })
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

    // prepare database - migrate all data but erase offers
    this.flyway.clean();
    this.flyway.migrate();
    cleanOffers();

    // configure job
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    jobParameterBuilder.addString("pathToFile", "staffImportJobTest/staff.csv");
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    // run job
    JobExecution jobExecution = getJobLauncherTestUtils(this.staffImportJob).launchJob(jobParameters);

    // check results
    // - job status
    assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    StaffMemberEto testMember = this.staffmanagement.findStaffMember(100L);
    System.out.println(testMember.getName() + " " + testMember.getRole().getName());

    //
    // // - imported data (there are 3 offers in setup data)
    // List<OfferEto> allOffers = this.offermanagement.findAllOffers();
    // assertThat(allOffers).hasSize(3);
    //
    // // - exemplary offer
    // OfferEto offer = allOffers.get(0);
    // assertThat(offer.getName()).isEqualTo("Leckeres-Menü");
    // assertThat(offer.getDescription()).isEqualTo("Description of Leckeres-Menü");
    // assertThat(offer.getPrice()).isEqualTo(new Money(15.99));
    // assertThat(offer.getState()).isEqualTo(OfferState.NORMAL);
  }

  /**
   * @param staffImportJob2
   * @return
   */

  private void cleanOffers() {

    // for (OfferEto offer : this.staffmanagement.findAllOffers()) {
    // this.staffmanagement.deleteOffer(offer.getId());
  }

}
