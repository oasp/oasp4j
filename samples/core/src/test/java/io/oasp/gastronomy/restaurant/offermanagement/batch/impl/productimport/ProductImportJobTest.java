package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringBatchIntegrationTest;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.ProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

/**
 * End-To-End test job "import offer management from csv"
 *
 * @author jczas
 */
@Ignore
@ContextConfiguration(locations = { ApplicationConfigurationConstants.BEANS_BATCH })
public class ProductImportJobTest extends AbstractSpringBatchIntegrationTest {

  @PersistenceContext
  private EntityManager entityManager;

  @Inject
  private PlatformTransactionManager txManager;

  @Inject
  private Job productImportJob;

  @Inject
  private Offermanagement offermanagement;

  /**
   * @throws Exception thrown by JobLauncherTestUtils
   */
  @Test
  public void testJob() throws Exception {

    // TODO hohwille temporary hack for travis build failure
    TransactionDefinition def = new DefaultTransactionDefinition();
    TransactionStatus status = this.txManager.getTransaction(def);
    try {
      this.entityManager.createQuery("Delete from " + OfferEntity.class.getSimpleName()).executeUpdate();
      this.entityManager.createQuery("Delete from " + ProductEntity.class.getSimpleName()).executeUpdate();
      this.txManager.commit(status);
    } catch (Exception e) {
      this.txManager.rollback(status);
      throw e;
    }

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
}
