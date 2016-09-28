package io.oasp.gastronomy.restaurant.general.common.impl.config;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Java bean configuration for Dozer
 *
 */
@Configuration
@ComponentScan(basePackages = { "io.oasp.module.beanmapping" })
public class BeansDozerConfig {

  private static final String DOZER_MAPPING_XML = "config/app/common/dozer-mapping.xml";

  /**
   * @return the {@link DozerBeanMapper}.
   */
  @Bean
  public Mapper getDozer() {

    List<String> beanMappings = new ArrayList<>();
    beanMappings.add(DOZER_MAPPING_XML);
    return new DozerBeanMapper(beanMappings);

  }
}
