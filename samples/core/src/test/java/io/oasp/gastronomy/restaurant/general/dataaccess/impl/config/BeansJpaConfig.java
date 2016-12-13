package io.oasp.gastronomy.restaurant.general.dataaccess.impl.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.DatabaseMigrator;

/**
 * Java configuration for JPA
 */
@Configuration
public class BeansJpaConfig {

  @Inject
  private EntityManagerFactory entityManagerFactory;

  @Inject
  private DataSource appDataSource;

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

}