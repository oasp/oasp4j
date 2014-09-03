package org.oasp.module.rest.service.impl.json;

import java.io.IOException;
import java.math.BigDecimal;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * Helper class to simplify implementation of {@link JsonDeserializer}.
 *
 * @author agreul, hohwiller
 * @param <T> the class to be deserialized
 */
public abstract class AbstractJsonDeserializer<T> extends JsonDeserializer<T> {

  /**
   * @param <V> type of value from node to get
   * @param node parent node to deserialize
   * @param fieldName the required key whose value is looked up
   * @param type the same type as the expected value
   * @return value from node to get
   */
  @SuppressWarnings("unchecked")
  protected <V> V getRequiredValue(JsonNode node, String fieldName, Class<V> type) {

    JsonNode childNode = node.get(fieldName);
    if (childNode != null) {

      V value = null;
      if (!childNode.isNull()) {
        try {
          if (type == String.class) {
            value = type.cast(childNode.getTextValue());
          } else if (type == BigDecimal.class) {
            value = type.cast(new BigDecimal(childNode.asText()));
          } else if (type == Boolean.class) {
            value = (V) Boolean.valueOf(childNode.getBooleanValue());
          } else if (type == Integer.class) {
            value = (V) Integer.valueOf(childNode.asText());
          } else if (type == Double.class) {
            value = (V) Double.valueOf(childNode.asText());
          } else {
            throw new IllegalArgumentException("Unsupported value type " + type.getName());
          }
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Failed to convert value to type " + type.getName(), e);
        }
      }
      if (value != null) {
        return value;
      }
    }
    throw new IllegalStateException("Deserialization failed due to missing " + type.getSimpleName() + " field "
        + fieldName + "!");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    JsonNode node = jp.getCodec().readTree(jp);
    return deserializeNode(node);
  }

  /**
   * @param node is the {@link JsonNode} with the value content to be deserialized
   * @return the deserialized java object
   */
  protected abstract T deserializeNode(JsonNode node);
}
