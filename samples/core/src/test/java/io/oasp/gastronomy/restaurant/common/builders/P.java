package io.oasp.gastronomy.restaurant.common.builders;

/**
 * Helper interface for usage of test data builders.
 * 
 * @param <T> the parameter
 */
public interface P<T> {

  /**
   * @param target the target to apply
   */
  public void apply(T target);
}