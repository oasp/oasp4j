package io.oasp.gastronomy.restaurant.general.configuration;

import org.springframework.batch.core.scope.StepScope;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Registers bean of type 'step' to be used in batch processing. E.g to be able to access jobParameter within a reader.
 * Note that the annotation @StepScope has to be used on an implementation not an interface to function properly
 *
 * @author sroeger
 *
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    beanFactory.registerScope("step", new StepScope());
  }

}
