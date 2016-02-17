package io.oasp.gastronomy.restaurant.general.configuration;

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
 * @author tkuzynow
 */
@Configuration
@ComponentScan(basePackages = { "io.oasp.module.beanmapping" })
public class BeansDozerConfiguration {

  private static final String DOZER_MAPPING_XML = "config/app/common/dozer-mapping.xml";

  @Bean
  public Mapper getDozer() {

    List<String> beanMappings = new ArrayList<>();
    beanMappings.add(DOZER_MAPPING_XML);
    return new DozerBeanMapper(beanMappings);

  }
}
