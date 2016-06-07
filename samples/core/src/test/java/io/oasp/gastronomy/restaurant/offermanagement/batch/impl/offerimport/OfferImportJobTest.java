package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.offerimport;

import java.util.List;

import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootBatchApp;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.gastronomy.restaurant.offermanagement.batch.configuration.OfferImportConfig;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

/**
 * TODO sroeger needs to integrate custom editors in OfferImportConf to get this working with OfferEtos (Money and
 * OfferState are the problem right now) Bypass: increase skipLimit to skip all occurring errors
 *
 *
 * @author sroeger
 */
@SpringApplicationConfiguration(classes = { SpringBootBatchApp.class, OfferImportConfig.class })
@WebAppConfiguration
public class OfferImportJobTest extends AbstractSpringBatchIntegrationTest {

  @Autowired
  private Job offerImportJob;

  @Autowired
  private Offermanagement offermanagement;

  /**
   * @throws Exception thrown by JobLauncherTestUtils
   */
  @Test
  public void testJob() throws Exception {

    cleanDatabase();

    // configure job
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    jobParameterBuilder.addString("offers.file", "classpath:ProductImportJobTest/data/offers.csv");
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    // run job
    JobExecution jobExecution = getJobLauncherTestUtils(this.offerImportJob).launchJob(jobParameters);

    // check results
    // - job status
    assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    // - imported data (there is x products in setup data)
    List<OfferEto> allOffers = this.offermanagement.findAllOffers();

    for (OfferEto offer : allOffers) {
      System.out.println(offer.toString());
    }

    // // - exemplary drink
    // DrinkEto drink = (DrinkEto) allProducts.get(0);
    // assertThat(drink.getName()).isEqualTo("Heineken");
    // assertThat(drink.getDescription()).isEqualTo("Pretty good beer");
    // assertThat(drink.getPictureId()).isEqualTo(1);
    // assertThat(drink.isAlcoholic()).isTrue();
    //
    // // - exemplary meal
    // MealEto meal = (MealEto) allProducts.get(3);
    // assertThat(meal.getName()).isEqualTo("Bratwurst");
    // assertThat(meal.getDescription()).isEqualTo("Tasty sausage");
    // assertThat(meal.getPictureId()).isEqualTo(1);
  }

  private void cleanDatabase() {

    for (OfferEto offer : this.offermanagement.findAllOffers()) {
      this.offermanagement.deleteOffer(offer.getId());
    }

  }
}