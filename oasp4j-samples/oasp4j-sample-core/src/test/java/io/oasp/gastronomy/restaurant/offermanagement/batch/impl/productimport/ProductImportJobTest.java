package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import static org.junit.Assert.assertEquals;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.test.context.ContextConfiguration;

/**
 * End-To-End test job "import offer management from csv"
 *
 * @author jczas
 */
@ContextConfiguration(locations = { ApplicationConfigurationConstants.BEANS_BATCH })
public class ProductImportJobTest extends AbstractSpringBatchIntegrationTest {

  @Inject
  private Job productImportJob;

  @Inject
  private Offermanagement offermanagement;

  @SuppressWarnings("javadoc")
  @Test
  public void testJob() throws Exception {

    // configure job
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    jobParameterBuilder.addString("drinks.file", "classpath:ProductImportJobTest/data/drinks.csv");
    jobParameterBuilder.addString("meals.file", "classpath:ProductImportJobTest/data/meals.csv");
    JobParameters jobParameters = jobParameterBuilder.toJobParameters();

    // run job
    JobExecution jobExecution = getJobLauncherTestUtils(this.productImportJob).launchJob(jobParameters);

    // check results
    // - job status
    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

    // - imported data
    List<ProductEto> allProducts = this.offermanagement.findAllProducts();
    assertEquals(7, allProducts.size());

    // - exemplary drink
    DrinkEto drink = (DrinkEto) allProducts.get(0);
    assertEquals("Heineken", drink.getName());
    assertEquals("Pretty good beer", drink.getDescription());
    assertEquals(1, drink.getPictureId().longValue());
    assertEquals(true, drink.isAlcoholic());

    // - exemplary meal
    MealEto meal = (MealEto) allProducts.get(3);
    assertEquals("Bratwurst", meal.getName());
    assertEquals("Tasty sausage", meal.getDescription());
    assertEquals(1, meal.getPictureId().longValue());
  }
}
