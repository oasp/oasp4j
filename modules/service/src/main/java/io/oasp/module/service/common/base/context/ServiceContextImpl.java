package io.oasp.module.service.common.base.context;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import io.oasp.module.service.common.api.context.ServiceContext;
import io.oasp.module.service.common.api.discovery.ServiceDiscoveryContext;
import io.oasp.module.service.common.api.header.ServiceHeaderContext;

/**
 * Implementation of {@link ServiceContext}.
 *
 * @param <S> the generic type of the {@link #getApi() service API}.
 *
 * @since 3.0.0
 */
public class ServiceContextImpl<S> extends AbstractServiceContext<S>
    implements ServiceHeaderContext<S>, ServiceDiscoveryContext<S> {

  private final Map<String, String> headers;

  private final Collection<String> headerNames;

  private final Map<String, Object> values;

  private final Collection<String> valueNames;

  private String url;

  /**
   * The constructor.
   *
   * @param api the {@link #getApi() API}.
   */
  public ServiceContextImpl(Class<S> api) {
    super(api);
    this.headers = new HashMap<>();
    this.headerNames = Collections.unmodifiableSet(this.headers.keySet());
    this.values = new HashMap<>();
    this.valueNames = Collections.unmodifiableSet(this.values.keySet());
  }

  @Override
  public String getUrl() {

    return this.url;
  }

  @Override
  public void setUrl(String url) {

    if (this.url == null) {
      this.url = url;
    } else if (!this.url.equals(url)) {
      throw new IllegalStateException(
          "Discovery to " + url + " for " + getApi() + " is invalid as " + this.url + " has already been discovered");
    }
  }

  @Override
  public Collection<String> getHeaderNames() {

    return this.headerNames;
  }

  @Override
  public String getHeader(String name) {

    return this.headers.get(name);
  }

  @Override
  public void setHeader(String key, String value) {

    this.headers.put(key, value);
  }

  @Override
  public Collection<String> getValueNames() {

    return this.valueNames;
  }

  @Override
  public <T> T getValue(String name, Class<T> type, T defaultValue) {

    Object object = this.values.get(name);
    if (object == null) {
      return defaultValue;
    } else {
      return convertValue(object, type);
    }
  }

  /**
   * @param <T> the generic {@code type}.
   * @param object the {@link Object} to convert. Will not be {@code null}.
   * @param type the {@link Class} reflecting the requested type.
   * @return the given {@link Object} converted to the given {@code type}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <T> T convertValue(Object object, Class<T> type) {

    try {
      Object result;
      if (type.isInstance(object)) {
        result = object;
      } else if (type.isEnum()) {
        result = Enum.valueOf((Class<? extends Enum>) type, object.toString());
      } else if (type.isAssignableFrom(String.class)) {
        result = object.toString();
      } else if ((type == boolean.class) || (type == Boolean.class)) {
        result = Boolean.valueOf(object.toString());
      } else if ((type == int.class) || (type == Integer.class)) {
        result = Integer.valueOf(object.toString());
      } else if ((type == long.class) || (type == Long.class)) {
        result = Long.valueOf(object.toString());
      } else if ((type == double.class) || (type == Double.class)) {
        result = Double.valueOf(object.toString());
      } else if (type == Class.class) {
        result = Class.forName(object.toString());
      } else if ((type == float.class) || (type == Float.class)) {
        result = Float.valueOf(object.toString());
      } else if ((type == short.class) || (type == Short.class)) {
        result = Short.valueOf(object.toString());
      } else if ((type == byte.class) || (type == Byte.class)) {
        result = Byte.valueOf(object.toString());
      } else if (type == BigDecimal.class) {
        result = new BigDecimal(object.toString());
      } else if (type == BigInteger.class) {
        result = new BigInteger(object.toString());
      } else if (type == Number.class) {
        result = Double.parseDouble(object.toString());
      } else if ((type == Character.class) || ((type == char.class))) {
        String value = object.toString();
        if (value.length() == 1) {
          result = Character.valueOf(value.charAt(0));
        } else {
          throw new IllegalArgumentException(value);
        }
      } else if (type == Currency.class) {
        result = Currency.getInstance(object.toString());
      } else {
        throw new IllegalArgumentException(object.toString());
      }
      return (T) result;
    } catch (NumberFormatException | ClassNotFoundException e) {
      throw new IllegalArgumentException(object.toString(), e);
    }
  }

  @Override
  public void setValue(String name, Object value) {

    this.values.put(name, value);
  }

}
