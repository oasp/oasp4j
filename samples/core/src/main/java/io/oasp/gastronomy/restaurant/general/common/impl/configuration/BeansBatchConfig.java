package io.oasp.gastronomy.restaurant.general.common.impl.configuration;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;

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
			jobOperator.setJobExplorer(jobExplorer.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jobOperator.setJobLauncher(this.jobLauncher);
		jobOperator.setJobRegistry(this.jobRegistry);
		try {
			jobOperator.setJobRepository(this.jobRepository.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobOperator;

	}

	/**
	 * This method is creating jobrepository
	 *
	 * @return JobRepositoryFactoryBean
	 */
	@Bean(name = "jobRepository")
	// @DependsOn({"dataSource","transactionManager"})
	public JobRepositoryFactoryBean jobRepository() {
		jobRepository = new JobRepositoryFactoryBean();
		jobRepository.setDataSource(this.dataSource);
		jobRepository.setTransactionManager(this.transactionManager);
		jobRepository.setIsolationLevelForCreate(isolationLevelForCreate);
		return jobRepository;
	}

	/**
	 * This method is creating jobLauncher bean
	 *
	 * @return SimpleJobLauncher
	 */
	@Bean
	@DependsOn("jobRepository")
	public SimpleJobLauncher jobLauncher() {
		jobLauncher = new SimpleJobLauncher();
		try {
			jobLauncher.setJobRepository(this.jobRepository.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobLauncher;
	}

	/**
	 * This method is creating jobExplorer bean
	 *
	 * @return JobExplorerFactoryBean
	 */
	@Bean
	@DependsOn("dataSource")
	public JobExplorerFactoryBean jobExplorer() {

		jobExplorer = new JobExplorerFactoryBean();
		jobExplorer.setDataSource(dataSource);
		return jobExplorer;
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
	 * @param datasource
	 *            the datasource to set
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
		return transactionManager;
	}

	/**
	 *
	 * @param transactionManager
	 */
	@Inject
	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

}
