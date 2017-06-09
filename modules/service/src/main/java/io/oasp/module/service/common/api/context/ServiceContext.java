package io.oasp.module.service.common.api.context;

import java.util.Collection;

/**
 * This interface gives read access to contextual information of a {@link io.oasp.module.service.common.api.Service}.
 *
 * @param <S> the generic type of the {@link #getApi() service API}.
 *
 * @since 3.0.0
 */
public interface ServiceContext<S> {

  /**
   * @return the {@link Class} reflecting the API of the {@link io.oasp.module.service.common.api.Service}.
   */
  Class<S> getApi();

  /**
   * @return the URL (or URI) of the remote service.
   */
  String getUrl();

  /**
   * @return a {@link Collection} with the available {@link #getHeader(String) header} names (keys).
   */
  Collection<String> getHeaderNames();

  /**
   * @param name the name (key) of the header to get.
   * @return the value of the requested header or {@code null} if undefined.
   */
  String getHeader(String name);

  /**
   * @return a {@link Collection} with the available {@link #getValue(String, Class) value} names (keys).
   */
  Collection<String> getValueNames();

  /**
   * @param name the name (key) of the value to get.
   * @return the requested value as {@link String}. Will be {@code null} if undefined.
   */
  String getValue(String name);

  /**
   * @param <T> the generic {@code type}.
   * @param type the {@link Class} reflecting the expected value type to convert the value to. Should typically be API
   *        type such as an interface. The {@link Class#getName() qualified name} of this {@link Class} is used as
   *        {@link #getValueNames() name} (key).
   * @return the requested value converted to the given {@code type}. Will be {@code null} if undefined.
   */
  <T> T getValue(Class<T> type);

  /**
   * @param <T> the generic {@code type}.
   * @param name the name (key) of the value to get.
   * @param type the {@link Class} reflecting the expected value type to convert the value to.
   * @return the requested value converted to the given {@code type}. Will be {@code null} if undefined.
   */
  <T> T getValue(String name, Class<T> type);

  /**
   * @param <T> the generic {@code type}.
   * @param name the name (key) of the value to get.
   * @param type the {@link Class} reflecting the expected value type to convert the value to.
   * @param defaultValue the value returned as default if the actual value is undefined.
   * @return the requested value converted to the given {@code type}. Will be {@code null} if undefined.
   */
  <T> T getValue(String name, Class<T> type, T defaultValue);

}
