package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.configuration;

import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.processor.DrinkPassThroughProcessor;
import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.processor.OfferPassThroughProcessor;
import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.reader.DrinkFromCsvReader;
import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.reader.OfferFromCsvReader;
import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.writer.DrinkToDbWriter;
import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.writer.OfferToDbWriter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of job "import offer management from csv"
 *
 * @author jczas
 */
@Configuration
@EnableBatchProcessing
public class ImportOfferManagementFromCsvJobConfiguration {
  // TODO make *Reader, *Writer, *Processor @Named instead of @Bean here?

  /**
   * @return instance of DrinkFromCsvReader
   */
  @Bean
  public ItemReader<DrinkEto> drinkFromCsvReader() {

    return new DrinkFromCsvReader();
  }

  /**
   * @return instance of OfferFromCsvReader
   */
  @Bean
  public ItemReader<OfferEto> offerFromCsvReader() {

    return new OfferFromCsvReader();
  }

  /**
   * @return instance of DrinkToDbWriter
   */
  @Bean
  public ItemWriter<DrinkEto> drinkToDbWriter() {

    return new DrinkToDbWriter();
  }

  /**
   * @return instance of OfferToDbWriter
   */
  @Bean
  public ItemWriter<OfferEto> offerToDbWriter() {

    return new OfferToDbWriter();
  }

  /**
   * @return instance of DrinkPassThroughProcessor
   */
  @Bean
  public ItemProcessor<DrinkEto, DrinkEto> drinkPassThroughProcessor() {

    return new DrinkPassThroughProcessor();
  }

  /**
   * @return instance of OfferPassThroughProcessor
   */
  @Bean
  public ItemProcessor<OfferEto, OfferEto> offerPassThroughProcessor() {

    return new OfferPassThroughProcessor();
  }

  /**
   * @param stepBuilderFactory
   * @param drinkFromCsvReader
   * @param drinkToDbWriter
   * @param drinkPassThroughProcessor
   * @return step "import drink from csv" from job "import offer management from csv"
   */
  @Bean
  public Step drinkImportFromCsvStep(StepBuilderFactory stepBuilderFactory, ItemReader<DrinkEto> drinkFromCsvReader,
      ItemWriter<DrinkEto> drinkToDbWriter, ItemProcessor<DrinkEto, DrinkEto> drinkPassThroughProcessor) {

    // TODO set chunk from parameters (???)
    return stepBuilderFactory.get("drinkImportFromCsvStep").<DrinkEto, DrinkEto> chunk(10).reader(drinkFromCsvReader)
        .processor(drinkPassThroughProcessor).writer(drinkToDbWriter).build();
  }

  /**
   * @param stepBuilderFactory
   * @param offerFromCsvReader
   * @param offerToDbWriter
   * @param offerPassThroughProcessor
   * @return step "import offer from csv" from job "import offer management from csv"
   */
  @Bean
  public Step offerImportFromCsvStep(StepBuilderFactory stepBuilderFactory, ItemReader<OfferEto> offerFromCsvReader,
      ItemWriter<OfferEto> offerToDbWriter, ItemProcessor<OfferEto, OfferEto> offerPassThroughProcessor) {

    // TODO set chunk from parameters (???)
    return stepBuilderFactory.get("offerImportFromCsvStep").<OfferEto, OfferEto> chunk(10).reader(offerFromCsvReader)
        .processor(offerPassThroughProcessor).writer(offerToDbWriter).build();
  }

  /**
   * @param jobs
   * @param
   * @return job "import offer management from csv"
   */
  @Bean
  public Job importOfferManagementFromCsvJob(JobBuilderFactory jobs, Step drinkImportFromCsvStep,
      Step offerImportFromCsvStep) {

    return jobs.get("importOfferManagementFromCsvJob").incrementer(new RunIdIncrementer())
        .start(drinkImportFromCsvStep).next(offerImportFromCsvStep).build();
  }

  @Bean
  public JobLauncherTestUtils jobLauncherTestUtils() {

    return new JobLauncherTestUtils();
  }

}
