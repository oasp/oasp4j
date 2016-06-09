package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.offerimport;

import java.util.List;

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
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

/**
 * This test grabs the offers.csv and saves it into database. ItemProcessor is used here to convert type OfferCsv to
 * OfferEto. This is necessary because OfferEto contains types Money and OfferState. Other possibility is using custom
 * type converters. I had to insert AUTO_INCREMENT into database table offer. @TestPropertySource is needed because
 * database needs products. Logs skipped item as log.warn
 *
 * @author sroeger
 */
@SpringApplicationConfiguration(classes = { SpringBootBatchApp.class, OfferImportConfig.class })
@WebAppConfiguration
@TestPropertySource(properties = { "flyway.locations=db/migration" })
public class OfferImportJobTest extends AbstractSpringBatchIntegrationTest {

  @Inject
  private Job offerImportJob;

  @Inject
  private Offermanagement offermanagement;

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
    jobParameterBuilder.addString("pathToFile", "offerImportJobTest/offers.csv");
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    // run job
    JobExecution jobExecution = getJobLauncherTestUtils(this.offerImportJob).launchJob(jobParameters);

    // check results
    // - job status
    assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    // - imported data (there are 3 offers in setup data)
    List<OfferEto> allOffers = this.offermanagement.findAllOffers();
    assertThat(allOffers).hasSize(3);

    // - exemplary offer
    OfferEto offer = allOffers.get(0);
    assertThat(offer.getName()).isEqualTo("Leckeres-Menü");
    assertThat(offer.getDescription()).isEqualTo("Description of Leckeres-Menü");
    assertThat(offer.getPrice()).isEqualTo(new Money(15.99));
    assertThat(offer.getState()).isEqualTo(OfferState.NORMAL);
  }

  private void cleanOffers() {

    for (OfferEto offer : this.offermanagement.findAllOffers()) {
      this.offermanagement.deleteOffer(offer.getId());
    }

  }
}