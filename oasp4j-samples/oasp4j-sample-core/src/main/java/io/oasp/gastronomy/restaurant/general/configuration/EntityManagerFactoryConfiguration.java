package io.oasp.gastronomy.restaurant.general.configuration;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class EntityManagerFactoryConfiguration {

  @Value("io.oasp.gastronomy.restaurant.*.dataaccess")
  private String firstPackageToScan;

  @Value("io.oasp.module.jpa.dataaccess.api")
  private String secondPackageToScan;

  @Value("${database.hibernate.show.sql}")
  private Boolean showSql;

  @Value("${database.hibernate.dialect}")
  private String databasePlatform;

  @Value("${database.hibernate.hbm2ddl.auto}")
  private String hbm2ddlAuto;

  private @Autowired JdbcDataSource appDataSource;

  @Value("config/app/dataaccess/NamedQueries.xml")
  private String mappingResources;

  @Bean
  @DependsOn("flyway")
  public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {

    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
    bean.setPackagesToScan(this.firstPackageToScan, this.secondPackageToScan);
    bean.setMappingResources(this.mappingResources);

    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setShowSql(this.showSql);
    jpaVendorAdapter.setDatabasePlatform(this.databasePlatform);
    bean.setJpaVendorAdapter(jpaVendorAdapter);

    bean.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", this.hbm2ddlAuto);
    bean.setDataSource(this.appDataSource);
    return bean;
  }
}
