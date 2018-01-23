#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.dataaccess.impl.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ${package}.general.dataaccess.base.DatabaseMigrator;

/**
 * Java configuration for JPA
 */
@Configuration
public class BeansJpaConfig {

  @Inject
  private EntityManagerFactory entityManagerFactory;

  @Inject
  private DataSource appDataSource;

  @Value("${symbol_dollar}{database.migration.auto}")
  private Boolean enabled;

  @Value("${symbol_dollar}{database.migration.testdata}")
  private Boolean testdata;

  @Value("${symbol_dollar}{database.migration.clean}")
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