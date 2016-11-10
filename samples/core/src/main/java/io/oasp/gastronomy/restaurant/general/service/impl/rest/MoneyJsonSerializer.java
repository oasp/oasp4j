package io.oasp.gastronomy.restaurant.general.service.impl.rest;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * The {@link JsonSerializer} for {@link Money}.
 *
 */
public final class MoneyJsonSerializer extends JsonSerializer<Money> {

  @Override
  public void serialize(Money value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

    if (value != null) {
      jgen.writeString(value.getValue().toString());
    }
  }
}
