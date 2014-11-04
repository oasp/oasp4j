#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.service.impl.rest;

import ${package}.general.common.api.datatype.Money;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * The {@link JsonSerializer} for {@link Money}.
 *
 * @author agreul
 */
public final class MoneyJsonSerializer extends JsonSerializer<Money> {

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(Money value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

    if (value != null) {
      jgen.writeString(value.getValue().toString());
    }
  }
}
