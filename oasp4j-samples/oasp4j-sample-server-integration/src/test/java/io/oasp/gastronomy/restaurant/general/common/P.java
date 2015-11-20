package io.oasp.gastronomy.restaurant.general.common;

/**
 * Functional interface for testdata builder pattern
 *
 * @param <T> generic interface
 *
 * @author mbrunnli
 *
 */
public interface P<T> {

  /**
   * @param target of Type T
   */
  public void apply(T target);
}