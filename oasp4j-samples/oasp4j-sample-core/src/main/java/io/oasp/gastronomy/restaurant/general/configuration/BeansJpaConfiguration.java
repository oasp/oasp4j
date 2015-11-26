package io.oasp.gastronomy.restaurant.general.configuration;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class BeansJpaConfiguration {

  private @Autowired EntityManagerFactory entityManagerFactory;

  @Bean
  public PersistenceExceptionTranslationPostProcessor getPersistenceExceptionTranslationPostProcessor() {

    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Bean
  public PlatformTransactionManager getTransactionManager() {

    JpaTransactionManager manager = new JpaTransactionManager();
    manager.setEntityManagerFactory(this.entityManagerFactory);
    return manager;
  }

  @Bean
  public SharedEntityManagerBean getEntityManagerFactoryBean() {

    SharedEntityManagerBean bean = new SharedEntityManagerBean();
    bean.setEntityManagerFactory(this.entityManagerFactory);
    return bean;
  }

}
