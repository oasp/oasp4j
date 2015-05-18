package io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.salesexport;

import io.oasp.gastronomy.restaurant.salesmanagement.batch.impl.salesexport.processor.SalesProcessor;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.SingleColumnRowMapper;

/**
 * TODO ABIELEWI This type ...
 *
 * @author ABIELEWI
 */
@Configuration
@EnableBatchProcessing
public class SalesExportJobConfig {

  @Bean
  public ItemReader<Long> salesReader() {

    JdbcCursorItemReader<Long> reader = new JdbcCursorItemReader<Long>();
    reader.setSql("SELECT FROM ");
    reader.setRowMapper(new SingleColumnRowMapper(Long.class));
    return reader;
  }

  @Bean
  public ItemProcessor<Long, SalesItem> salesProcessor() {

    return new SalesProcessor();
  }

  @Bean
  public ItemWriter<SalesItem> salesWriter() {

    FlatFileItemWriter<SalesItem> writer = new FlatFileItemWriter<SalesItem>();
    return writer;
  }
}
