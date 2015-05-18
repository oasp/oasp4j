package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport;

import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport.reader.DrinkReader;
import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.productimport.writer.ProductWriter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Configuration for ProductImportJob. The job reads received CSV files and adds records to DB.
 *
 * @author jczas, abielewi
 */
@Configuration
@EnableBatchProcessing
public class ProductImportJobConfig {

  /**
   * @return instance of DrinkFromCsvReader
   */
  @Bean
  public ItemReader<DrinkEto> drinkReader() {

    return new DrinkReader();
  }

  /**
   * @return
   */
  @Bean
  public ItemReader<MealEto> mealReader() {

    FlatFileItemReader<MealEto> reader = new FlatFileItemReader<MealEto>();
    reader.setResource(new ClassPathResource("meals.csv"));
    reader.setLineMapper(new DefaultLineMapper<MealEto>() {
      {
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setNames(new String[] { "name", "description", "pictureId" });
          }
        });
        setFieldSetMapper(new BeanWrapperFieldSetMapper<MealEto>() {
          {
            setTargetType(MealEto.class);
          }
        });
      }
    });
    return reader;
  }

  /**
   * @return instance of DrinkToDbWriter
   */
  @Bean
  public ItemWriter<ProductEto> productWriter() {

    return new ProductWriter();
  }

  /**
   * @param stepBuilderFactory
   * @param drinkReader
   * @param productWriter
   * @return
   */
  @Bean
  public Step drinkImportStep(StepBuilderFactory stepBuilderFactory, ItemReader<DrinkEto> drinkReader,
      ItemWriter<ProductEto> productWriter) {

    return stepBuilderFactory.get("drinkImportStep").<DrinkEto, DrinkEto> chunk(50).reader(drinkReader)
        .writer(productWriter).build();
  }

  /**
   * @param stepBuilderFactory
   * @param mealReader
   * @param productWriter
   * @return
   */
  @Bean
  public Step mealImportStep(StepBuilderFactory stepBuilderFactory, ItemReader<MealEto> mealReader,
      ItemWriter<ProductEto> productWriter) {

    return stepBuilderFactory.get("mealImportStep").<MealEto, MealEto> chunk(50).reader(mealReader)
        .writer(productWriter).build();
  }

  /**
   * @param jobBuilderFactory
   * @param drinkImportStep
   * @return
   */
  @Bean
  public Job productImportJob(JobBuilderFactory jobBuilderFactory, Step drinkImportStep) {

    return jobBuilderFactory.get("productImportJob").incrementer(new RunIdIncrementer()).start(drinkImportStep).build();
  }
}
