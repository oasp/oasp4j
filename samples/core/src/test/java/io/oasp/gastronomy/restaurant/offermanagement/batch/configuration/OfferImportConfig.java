package io.oasp.gastronomy.restaurant.offermanagement.batch.configuration;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.offerimport.writer.OfferItemConverter;
import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.offerimport.writer.OfferWriter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

/**
 *
 * This class defines job and steps for an offer import test
 *
 * @author sroeger
 */

public class OfferImportConfig {

  @Bean
  public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {

    return new JobBuilderFactory(jobRepository);
  }

  @Bean
  public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository,
      PlatformTransactionManager transactionManager) {

    return new StepBuilderFactory(jobRepository, transactionManager);
  }

  @Bean
  public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader reader, ItemProcessor processor,
      ItemWriter writer) {

    /* it handles bunches of 2 units and skips 1 error of types given below */
    return stepBuilderFactory.get("step1").chunk(2).reader(reader).processor(processor).writer(writer).faultTolerant()
        .skipLimit(1).skip(IncorrectTokenCountException.class).skip(FlatFileParseException.class).build();

  }

  @Bean
  public Job job1(JobBuilderFactory jobs, Step step1) {

    return jobs.get("job1").incrementer(new RunIdIncrementer()).flow(step1).end().build();
  }

  @Bean
  public ItemReader<OfferCsv> reader() throws MalformedURLException {

    FlatFileItemReader<OfferCsv> reader = new FlatFileItemReader<OfferCsv>();
    reader.setResource(new ClassPathResource("ProductImportJobTest/data/offers.csv"));

    reader.setLineMapper(new DefaultLineMapper<OfferCsv>() {
      {
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setNames(new String[] { "name", "description", "state", "meal_id", "sidedish_id", "drink_id", "price" });
          }
        });
        setFieldSetMapper(new BeanWrapperFieldSetMapper<OfferCsv>() {
          {
            setTargetType(OfferCsv.class);

          }
        });
      }
    });
    return reader;
  }

  @Bean
  public ItemWriter<OfferEto> writer() {

    return new OfferWriter();
  }

  @Bean
  public ItemProcessor<OfferCsv, OfferEto> processor() {

    return new OfferItemConverter();
  }

}