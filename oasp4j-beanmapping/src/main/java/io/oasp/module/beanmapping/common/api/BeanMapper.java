package io.oasp.module.beanmapping.common.api;

import java.util.List;
import java.util.Set;

/**
 * This is the interface used to convert from one Java bean to another compatible bean (e.g. from a JPA entity to a
 * corresponding transfer-object).
 * 
 * @author hohwille
 */
public interface BeanMapper {

  /**
   * Recursively converts the given <code>source</code> {@link Object} to the given target {@link Class}.
   * 
   * @param <T> is the generic type to convert to.
   * @param source is the object to convert.
   * @param targetClass is the {@link Class} reflecting the type to convert to.
   * @return the converted object. Will be <code>null</code> if <code>source</code> is <code>null</code>.
   */
  <T> T map(Object source, Class<T> targetClass);

  /**
   * Creates a new {@link List} with the {@link #map(Object, Class) mapped bean} for each {@link List#get(int) entry} of
   * the given {@link List}.
   * 
   * @param <T> is the generic type to convert the {@link List} entries to.
   * @param source is the {@link List} with the source objects.
   * @param targetClass is the {@link Class} reflecting the type to convert each {@link List} entry to.
   * @return the {@link List} with the converted objects. Will be {@link List#isEmpty() empty} is <code>source</code> is
   *         empty or <code>null</code>.
   */
  <T> List<T> mapList(List<?> source, Class<T> targetClass);

  /**
   * Creates a new {@link Set} with the {@link #map(Object, Class) mapped bean} for each {@link Set#contains(Object)
   * entry} of the given {@link Set}.
   * 
   * @param <T> is the generic type to convert the {@link Set} entries to.
   * @param source is the {@link Set} with the source objects.
   * @param targetClass is the {@link Class} reflecting the type to convert each {@link Set} entry to.
   * @return the {@link Set} with the converted objects. Will be {@link Set#isEmpty() empty} is <code>source</code> is
   *         empty or <code>null</code>.
   */
  <T> Set<T> mapSet(Set<?> source, Class<T> targetClass);

}
