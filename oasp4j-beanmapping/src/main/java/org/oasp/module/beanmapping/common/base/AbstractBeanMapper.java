package org.oasp.module.beanmapping.common.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.oasp.module.beanmapping.common.api.BeanMapper;

/**
 * The abstract base implementation of {@link BeanMapper}.
 * 
 * @author hohwille
 */
public abstract class AbstractBeanMapper implements BeanMapper {

  /**
   * The constructor.
   */
  public AbstractBeanMapper() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> List<T> mapList(List<?> source, Class<T> targetClass) {

    if ((source == null) || (source.isEmpty())) {
      return new ArrayList<>();
    }
    List<T> result = new ArrayList<>(source.size());
    for (Object sourceObject : source) {
      result.add(map(sourceObject, targetClass));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Set<T> mapSet(Set<?> source, Class<T> targetClass) {

    if ((source == null) || (source.isEmpty())) {
      return new HashSet<>();
    }
    Set<T> result = new HashSet<>(source.size());
    for (Object sourceObject : source) {
      result.add(map(sourceObject, targetClass));
    }
    return result;
  }
}
