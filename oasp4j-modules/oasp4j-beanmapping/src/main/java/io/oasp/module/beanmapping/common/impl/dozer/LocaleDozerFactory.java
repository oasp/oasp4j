package io.oasp.module.beanmapping.common.impl.dozer;

import java.util.Locale;

import org.dozer.BeanFactory;

/**
 * Dozer factory for java.util.Locale because Locale class does not have default constructor
 *
 * @author dsanche2
 * @since 1.3.0
 */
public class LocaleDozerFactory implements BeanFactory {

  @Override
  public Object createBean(Object source, Class<?> sourceClass, String targetBeanId) {

    final Locale localeSource = (Locale) source;

    return new Locale(localeSource.getLanguage(), localeSource.getCountry());
  }
}