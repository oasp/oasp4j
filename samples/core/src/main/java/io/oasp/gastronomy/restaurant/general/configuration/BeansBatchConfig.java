package io.oasp.gastronomy.restaurant.general.configuration;

/**
 * This class contains the configuration like jobLauncher,Jobrepository etc.
 * @author ssarmoka
 */

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This class contains configuration of batch beans.
 */
@Configuration
public class BeansBatchConfig {

  /**
   * JobRepository configuration
   */
  private JobRepositoryFactoryBean jobRepository;

  /**
   * JobRegistry configuration
   */
  private MapJobRegistry jobRegistry;

  /**
   * JobLauncher configuration
   */
  private SimpleJobLauncher jobLauncher;

  /**
   * JobExplorer configuration
   */
  private JobExplorerFactoryBean jobExplorer;

  /**
   * Datasource configuartion
   */
  private DataSource dataSource;

  /**
   * Transaction manager configuration
   */
  private PlatformTransactionManager transactionManager;

  /**
   * Isolation level configuration
   */
  @Value("ISOLATION_DEFAULT")
  private String isolationLevelForCreate;

  /**
   * This method is creating joboperator bean
   *
   * @return SimpleJobOperator
   */
  @Bean
  @DependsOn({ "jobRepository", "jobExplorer", "jobRegistry", "jobLauncher" })
  public SimpleJobOperator jobOperator() {

    SimpleJobOperator jobOperator = new SimpleJobOperator();
    try {
      jobOperator.setJobExplorer(this.jobExplorer.getObject());
    } catch (Exception e) {
      throw new BeanCreationException("Could not create BatchJobOperator", e);
    }

    jobOperator.setJobLauncher(this.jobLauncher);
    jobOperator.setJobRegistry(this.jobRegistry);

    try {
      jobOperator.setJobRepository(this.jobRepository.getObject());
    } catch (Exception e) {
      throw new BeanCreationException("Could not create BatchJobOperator", e);
    }

    return jobOperator;
  }

  /**
   * This method is creating jobrepository
   *
   * @return JobRepositoryFactoryBean
   */
  @Bean(name = "jobRepository")
  public JobRepositoryFactoryBean jobRepository() {

    this.jobRepository = new JobRepositoryFactoryBean();
    this.jobRepository.setDataSource(this.dataSource);
    this.jobRepository.setTransactionManager(this.transactionManager);
    this.jobRepository.setIsolationLevelForCreate(this.isolationLevelForCreate);
    return this.jobRepository;
  }

  /**
   * This method is creating jobLauncher bean
   *
   * @return SimpleJobLauncher
   */
  @Bean
  @DependsOn("jobRepository")
  public SimpleJobLauncher jobLauncher() {

    this.jobLauncher = new SimpleJobLauncher();

    try {
      this.jobLauncher.setJobRepository(this.jobRepository.getObject());
    } catch (Exception e) {
      throw new BeanCreationException("Could not create BatchJobOperator", e);
    }

    return this.jobLauncher;
  }

  /**
   * This method is creating jobExplorer bean
   *
   * @return JobExplorerFactoryBean
   */
  @Bean
  @DependsOn("dataSource")
  public JobExplorerFactoryBean jobExplorer() {

    this.jobExplorer = new JobExplorerFactoryBean();
    this.jobExplorer.setDataSource(this.dataSource);
    return this.jobExplorer;
  }

  /**
   * This method is creating jobRegistry bean
   *
   * @return MapJobRegistry
   */
  @Bean
  public MapJobRegistry jobRegistry() {

    this.jobRegistry = new MapJobRegistry();
    return this.jobRegistry;
  }

  /**
   * This method is creating JobRegistryBeanPostProcessor
   *
   * @return JobRegistryBeanPostProcessor
   */
  @Bean
  @DependsOn("jobRegistry")
  public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {

    JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
    postProcessor.setJobRegistry(this.jobRegistry);
    return postProcessor;
  }

  /**
   * This method is creating incrementer
   *
   * @return RunIdIncrementer
   */
  @Bean
  public RunIdIncrementer incrementer() {

    return new RunIdIncrementer();
  }

  /**
   * @return datasource
   */
  public DataSource getDataSource() {

    return this.dataSource;
  }

  /**
   * @param datasource the datasource to set
   */
  @Inject
  public void setDataSource(DataSource datasource) {

    this.dataSource = datasource;
  }

  /**
   *
   * @return transactionManager
   */
  public PlatformTransactionManager getTransactionManager() {

    return this.transactionManager;
  }

  /**
   *
   * @param transactionManager the transactionManager to set
   */
  @Inject
  public void setTransactionManager(PlatformTransactionManager transactionManager) {

    this.transactionManager = transactionManager;
  }

}
