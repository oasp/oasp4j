package io.oasp.gastronomy.restaurant.staffmanagement.batch.impl.staffimport;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.common.collect.ImmutableMap;

import io.oasp.gastronomy.restaurant.batch.common.CustomSkipListener;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.staffmanagement.batch.impl.staffimport.writer.StaffMemberWriter;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;

/**
 * This class serves as configuration class for the StaffImportJobTest. The conversation of a read String to enum type
 * "Role" is done using {code setCustomEditors()} method of {@link BeanWrapperFieldSetMapper} by registering a custom
 * {@link RoleEditor}. This class makes use of the application.properties file by accessing the
 * {@code batch.staffimport.chunk.size} value.
 *
 *
 * @author sroeger
 */
public class StaffImportConfig {

  @Value("${batch.staffimport.chunk.size}")
  private int chunkSize;

  @Value("${batch.staffimport.skip.limit}")
  private int skipLimit;

  /**
   * @param jobRepository the {@link JobRepository}
   * @return {@link JobBuilderFactory}
   */
  @Bean
  public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {

    return new JobBuilderFactory(jobRepository);
  }

  /**
   * @param jobRepository the {@link JobRepository}
   * @param transactionManager the {@link PlatformTransactionManager}
   * @return {@link StepBuilderFactory}
   */
  @Bean
  public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository,
      PlatformTransactionManager transactionManager) {

    return new StepBuilderFactory(jobRepository, transactionManager);
  }

  /**
   * @param stepBuilderFactory the {@link StepBuilderFactory}
   * @param reader the {@link ItemReader}
   * @param writer the {@link ItemWriter}
   * @param skipListener the {@link SkipListener}
   * @return the created stepBuilder
   */
  @Bean
  public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader reader, ItemWriter writer,
      SkipListener skipListener) {

    return stepBuilderFactory.get("step1").chunk(this.chunkSize).reader(reader).writer(writer).faultTolerant()
        .skipLimit(this.skipLimit).skip(IncorrectTokenCountException.class).skip(FlatFileParseException.class)
        .listener(skipListener).build();

  }

  /**
   * @param jobs the {@link JobBuilderFactory}
   * @param step1 the {@link Step}
   * @return the created jobBuilder
   */
  @Bean
  public Job job1(JobBuilderFactory jobs, Step step1) {

    return jobs.get("job1").incrementer(new RunIdIncrementer()).flow(step1).end().build();
  }

  /**
   * @param pathToFile the path to the file to execute
   * @return the created reader
   * @throws MalformedURLException when the URL is not correctly formatted
   */
  @Bean
  @StepScope
  public FlatFileItemReader<StaffMemberEto> reader(@Value("#{jobParameters[pathToFile]}") String pathToFile)
      throws MalformedURLException {

    FlatFileItemReader<StaffMemberEto> reader = new FlatFileItemReader<>();
    reader.setResource(new ClassPathResource(pathToFile));

    reader.setLineMapper(new DefaultLineMapper<StaffMemberEto>() {
      {
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setNames(new String[] { "name", "role", "firstName", "lastName" });
          }
        });
        setFieldSetMapper(new BeanWrapperFieldSetMapper<StaffMemberEto>() {
          {

            setCustomEditors(ImmutableMap.of(Role.class, new RoleEditor()));
            setTargetType(StaffMemberEto.class);
          }
        });
      }
    });
    return reader;
  }

  /**
   * @return the created writer
   */
  @Bean
  public ItemWriter<StaffMemberEto> writer() {

    return new StaffMemberWriter();
  }

  /**
   * @return the created skipListener
   */
  @Bean
  public SkipListener<?, ?> skipListener() {

    return new CustomSkipListener<>();

  }

}