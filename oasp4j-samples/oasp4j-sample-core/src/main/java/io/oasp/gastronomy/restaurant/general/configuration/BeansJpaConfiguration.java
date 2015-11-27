package io.oasp.gastronomy.restaurant.general.configuration;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.DatabaseMigrator;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Java configuration for JPA
 *
 * @author tkuzynow
 */
@Configuration
@EnableTransactionManagement
public class BeansJpaConfiguration {

  private @Autowired EntityManagerFactory entityManagerFactory;

  private @Autowired DataSource appDataSource;

  @Value("${database.migration.auto}")
  private Boolean enabled;

  @Value("${database.migration.testdata}")
  private Boolean testdata;

  @Value("${database.migration.clean}")
  private Boolean clean;

  @Bean
  public DatabaseMigrator getFlyway() {

    DatabaseMigrator migrator = new DatabaseMigrator();
    migrator.setClean(this.clean);
    migrator.setDataSource(this.appDataSource);
    migrator.setEnabled(this.enabled);
    migrator.setTestdata(this.testdata);
    return migrator;

  }

  @PostConstruct
  public void migrate() {

    getFlyway().migrate();
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor getPersistenceExceptionTranslationPostProcessor() {

    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Bean
  public SharedEntityManagerBean getEntityManagerFactoryBean() {

    SharedEntityManagerBean bean = new SharedEntityManagerBean();
    bean.setEntityManagerFactory(this.entityManagerFactory);
    return bean;
  }

}
