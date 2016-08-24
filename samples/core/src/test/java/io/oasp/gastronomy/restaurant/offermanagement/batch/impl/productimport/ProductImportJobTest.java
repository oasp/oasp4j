package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import io.oasp.gastronomy.restaurant.SpringBootBatchApp;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * End-To-End test job "import offer management from csv"
 *
 */
@SpringApplicationConfiguration(classes= { SpringBootBatchApp.class }, locations = { "classpath:config/app/batch/beans-productimport.xml" })
@WebAppConfiguration
public class ProductImportJobTest extends AbstractSpringBatchIntegrationTest {

  @Inject
  private Job productImportJob;

  @Inject
  private Offermanagement offermanagement;

  /**
   * @throws Exception thrown by JobLauncherTestUtils
   */
  @Test
  public void testJob() throws Exception {

    cleanDatabase();

    // configure job
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    jobParameterBuilder.addString("drinks.file", "classpath:ProductImportJobTest/data/drinks.csv");
    jobParameterBuilder.addString("meals.file", "classpath:ProductImportJobTest/data/meals.csv");
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    // run job
    JobExecution jobExecution = getJobLauncherTestUtils(this.productImportJob).launchJob(jobParameters);

    // check results
    // - job status
    assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    // - imported data (there is 7 products in setup data)
    List<ProductEto> allProducts = this.offermanagement.findAllProducts();
    assertThat(allProducts).hasSize(7);

    // - exemplary drink
    DrinkEto drink = (DrinkEto) allProducts.get(0);
    assertThat(drink.getName()).isEqualTo("Heineken");
    assertThat(drink.getDescription()).isEqualTo("Pretty good beer");
    assertThat(drink.getPictureId()).isEqualTo(1);
    assertThat(drink.isAlcoholic()).isTrue();

    // - exemplary meal
    MealEto meal = (MealEto) allProducts.get(3);
    assertThat(meal.getName()).isEqualTo("Bratwurst");
    assertThat(meal.getDescription()).isEqualTo("Tasty sausage");
    assertThat(meal.getPictureId()).isEqualTo(1);
  }

  private void cleanDatabase() {

    for (OfferEto offer : this.offermanagement.findAllOffers()) {
      this.offermanagement.deleteOffer(offer.getId());
    }

    for (ProductEto product : this.offermanagement.findAllProducts()) {
      this.offermanagement.deleteProduct(product.getId());
    }
  }
}
