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
 * This class serves as configuration class for the StaffImportJobTest. There is no xml-configuration needed
 * additionally. The conversation of a read String to enum type "Role" is done using {@link setCustomEditors} method of
 * {@link BeanWrapperFieldSetMapper} by registering a custom {@link RoleEditor}.
 *
 *
 * @author sroeger
 */
public class StaffImportConfig {

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
  public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader reader, ItemWriter writer,
      SkipListener skipListener) {

    /* it handles bunches of 2 units and skips 1 error of types given below */
    return stepBuilderFactory.get("step1").chunk(200).reader(reader).writer(writer).faultTolerant().skipLimit(1)
        .skip(IncorrectTokenCountException.class).skip(FlatFileParseException.class).listener(skipListener).build();

  }

  @Bean
  public Job job1(JobBuilderFactory jobs, Step step1) {

    return jobs.get("job1").incrementer(new RunIdIncrementer()).flow(step1).end().build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<StaffMemberEto> reader(@Value("#{jobParameters[pathToFile]}") String pathToFile)
      throws MalformedURLException {

    FlatFileItemReader<StaffMemberEto> reader = new FlatFileItemReader<StaffMemberEto>();
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

  @Bean
  public ItemWriter<StaffMemberEto> writer() {

    return new StaffMemberWriter();
  }

  @Bean
  public SkipListener skipListener() {

    return new CustomSkipListener();

  }

  // @Bean
  // public ConversionService getConversionService() {
  //
  // ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
  // bean.setConverters(getConverters());
  // bean.afterPropertiesSet();
  // ConversionService object = bean.getObject();
  // return object;
  // }
  //
  // private Set<Converter<?, ?>> getConverters() {
  //
  // Set<Converter<?, ?>> converters = new HashSet<Converter<?, ?>>();
  //
  // converters.add(this.roleToStringConverter);
  // converters.add(this.stringToRoleConverter);
  // // add here more custom converters, either as spring bean references or directly instantiated
  //
  // return converters;
  // }

  // @Bean
  // public DefaultPropertyEditorRegistrar propertyEditorRegistrar() {
  //
  // PropertyEditorRegistrySupport registry = new PropertyEditorRegistrySupport();
  // registry.registerCustomEditor(Role.class, new RoleEditor());
  // DefaultPropertyEditorRegistrar registrar = new DefaultPropertyEditorRegistrar();
  // registrar.registerCustomEditors(registry);
  // return registrar;
  // }

}