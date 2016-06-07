package io.oasp.gastronomy.restaurant.offermanagement.batch.configuration;

import java.beans.PropertyEditor;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

import io.oasp.gastronomy.restaurant.general.batch.api.MoneyEditor;
import io.oasp.gastronomy.restaurant.general.batch.api.OfferStateEditor;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.batch.impl.offerimport.writer.OfferWriter;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

/**
 *
 * TODO sroeger need to insert custom editors OfferStateEditor and MoneyEditor to get functionality
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
  public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader reader, ItemWriter writer) {

    /* it handles bunches of 10 units and skips 1 error of types given below */
    return stepBuilderFactory.get("step1").chunk(10).reader(reader).writer(writer).faultTolerant().skipLimit(1)
        .skip(BindException.class).skip(FlatFileParseException.class).build();

  }

  @Bean
  public Job job1(JobBuilderFactory jobs, Step step1) {

    return jobs.get("job1").incrementer(new RunIdIncrementer()).flow(step1).end().build();
  }

  @Bean
  public ItemReader<OfferEto> reader() throws MalformedURLException {

    final Map<? extends Object, ? extends PropertyEditor> customEditors1 = new HashMap<OfferState, OfferStateEditor>();
    final Map<? extends Object, ? extends PropertyEditor> customEditors2 = new HashMap<Money, MoneyEditor>();

    FlatFileItemReader<OfferEto> reader = new FlatFileItemReader<OfferEto>();
    reader.setResource(new ClassPathResource("ProductImportJobTest/data/offers.csv"));

    reader.setLineMapper(new DefaultLineMapper<OfferEto>() {
      {
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setNames(new String[] { "name", "description", "state", "meal_id", "sidedish_id", "drink_id", "price" });
          }
        });
        setFieldSetMapper(new BeanWrapperFieldSetMapper<OfferEto>() {
          {
            setTargetType(OfferEto.class);
            setCustomEditors(customEditors1);
            setCustomEditors(customEditors2);

          }
        });
      }
    });
    return reader;
  }

  @Bean
  public ItemWriter writer() {

    return new OfferWriter();
  }

}