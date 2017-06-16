package io.oasp.module.logging.common.impl;

import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.ReplacingCompositeConverter;

/**
 * Based on org.owasp.security.logging.mask.MaskingConverter. Fixed to mask also events with a MultiMarker, by using
 * Marker.contains(). Issue opened in OWASP: https://github.com/javabeanz/owasp-security-logging/issues/19
 * <P>
 * This converter is used to output a masked version of the formatted message in contexts where the logging of
 * confidential information is undesirable.
 *
 * It is not possible to replace the actual formatted message, instead this converter returns a masked version of the
 * message that can be accessed using the conversionWord specified in the conversionRule definition in logback.xml.
 *
 * @author August Detlefsen [augustd@codemagi.com]
 * @author Sytze van Koningsveld
 */
public class MaskingConverter extends ReplacingCompositeConverter<ILoggingEvent> {

  public static final String MASKED_PASSWORD = "******";

  @Override
  public String convert(ILoggingEvent event) {

    Marker eventMarker = event.getMarker();

    Object[] args = event.getArgumentArray();
    if (eventMarker != null && eventMarker.contains(SecureLogging.CONFIDENTIAL)) {
      for (int i = 0; i < args.length; i++) {
        args[i] = MASKED_PASSWORD;
      }
    }

    return MessageFormatter.arrayFormat(event.getMessage(), args).getMessage();
  }

  /**
   * Override start method because the superclass ReplacingCompositeConverter requires at least two options and this
   * class has none.
   */
  @Override
  public void start() {

    this.started = true;
  }

}
