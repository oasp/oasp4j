package io.oasp.gastronomy.restaurant.general.common;

/**
 * Functional interface for testdata builder pattern
 * 
 * @author mbrunnli
 */
public interface P<T> {

  public void apply(T target);
}