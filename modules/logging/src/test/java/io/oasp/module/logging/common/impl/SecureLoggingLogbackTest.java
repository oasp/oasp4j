package io.oasp.module.logging.common.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.owasp.security.logging.filter.ExcludeClassifiedMarkerFilter;
import org.owasp.security.logging.mask.MaskingConverter;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.spi.FilterReply;
import io.oasp.module.test.common.base.ModuleTest;

/**
 * Test class for {@link SecureLogging}, when used with logback. <br>
 * Tests Marker initialization, logging of events with and without Markers, masking and filtering.
 * <P>
 * Main functionality is adapted from test classes of OWASP: <br>
 * owasp-security-logging-logback/src/test/java/org/owasp/security/logging/mask/MaskingConverterTest and
 * ../filter/ExcludeClassifiedMarkerFilterTest
 */
@RunWith(MockitoJUnitRunner.class)
public class SecureLoggingLogbackTest extends ModuleTest {

  LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

  Logger loggerLogback = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

  PatternLayoutEncoder encoder;

  ExcludeClassifiedMarkerFilter filterExclClassif;

  @Mock
  private RollingFileAppender<ILoggingEvent> mockAppender = new RollingFileAppender<>();

  // Captor is genericised with ch.qos.logback.classic.spi.LoggingEvent
  @Captor
  private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

  /**
   *
   */
  @Before
  public void setUp() {

    // This converter masks all arguments of a confidential message with ***.
    // It overwrites the message field %m, so the log pattern can stay unchanged.
    PatternLayout.defaultConverterMap.put("m", MaskingConverter.class.getName());

    this.encoder = new PatternLayoutEncoder();
    this.encoder.setContext(this.loggerContext);
    this.encoder.setPattern("[%marker] %-4relative [%thread] %-5level %logger{35} - %m%n");
    this.encoder.start();

    this.filterExclClassif = new ExcludeClassifiedMarkerFilter();
    this.filterExclClassif.setContext(this.loggerContext);
    this.filterExclClassif.start();
    assertTrue("filter must be started.", this.filterExclClassif.isStarted());

    this.mockAppender.setContext(this.loggerContext);
    this.mockAppender.setEncoder(this.encoder);
    // this.mockAppender.addFilter(this.filterExclClassif); // for some reason this does not work.
    this.mockAppender.start();
    // assertTrue("appender must be running.", this.mockAppender.isStarted()); // this fails, but the appender works.

    this.loggerLogback.addAppender(this.mockAppender);
  }

  /**
   *
   */
  @After
  public void teardown() {

    this.loggerLogback.detachAppender(this.mockAppender);
  }

  /**
   * Test if logging works at all, without using any Markers.
   */
  @Test
  public void testDefaultLogEvent() {

    String logmsg = "simple log message";
    this.loggerLogback.info(logmsg);

    // Now verify our logging interactions
    verify(this.mockAppender).doAppend(this.captorLoggingEvent.capture());

    // Get the logging event from the captor
    final LoggingEvent loggingEvent = this.captorLoggingEvent.getValue();

    // Check log level is correct
    assertThat(loggingEvent.getLevel()).isEqualTo(Level.INFO);

    // Check the message being logged is reasonable
    String layoutMessage = this.encoder.getLayout().doLayout(loggingEvent);
    assertFalse("formatted log message must not be empty.", layoutMessage.isEmpty());
    assertTrue("formatted log message must contain the original message.", layoutMessage.contains(logmsg));
  }

  /**
   * Test the output of a log event with a marker.
   */
  @Test
  public void testLogEventWithMarker() {

    Marker marker = SecureLogging.SECURITY_SUCCESS;
    String logmsg = "security log message";
    this.loggerLogback.info(marker, logmsg);

    // Now verify our logging interactions
    verify(this.mockAppender).doAppend(this.captorLoggingEvent.capture());

    // Get the logging event from the captor
    final LoggingEvent loggingEvent = this.captorLoggingEvent.getValue();

    // Check log level is correct
    assertThat(loggingEvent.getLevel()).isEqualTo(Level.INFO);

    // Check the message being logged is reasonable
    String layoutMessage = this.encoder.getLayout().doLayout(loggingEvent);
    System.out.println("test(): layoutMessage = " + layoutMessage);
    assertTrue("formatted log message must contain the original message.", layoutMessage.contains(logmsg));
    assertTrue("formatted log message must contain the name of its marker.", layoutMessage.contains(marker.getName()));
    // assertTrue("this pops up at the end of this test (with marker).", false);
  }

  /**
   * Test the output of a log event with a classification Marker and an argument that shall be masked. Note: the console
   * will show the 'password' content when running this test.
   */
  @Test
  public void testLogEventWithMasking() {

    System.out.println("Note: the console will show the 'password' content when running this test.");

    Marker marker = SecureLogging.CONFIDENTIAL;
    String password = "classified!";
    this.loggerLogback.info(marker, "confidential message with password = '{}'", password);

    // Now verify our logging interactions
    verify(this.mockAppender).doAppend(this.captorLoggingEvent.capture());

    // Get the logging event from the captor
    final LoggingEvent loggingEvent = this.captorLoggingEvent.getValue();

    // Check log level is correct
    assertThat(loggingEvent.getLevel()).isEqualTo(Level.INFO);

    // Check the message being logged is reasonable
    String layoutMessage = this.encoder.getLayout().doLayout(loggingEvent);
    System.out.println("test(): layoutMessage = " + layoutMessage);
    assertFalse("formatted log message must not contain any classified information.", layoutMessage.contains(password));
    assertTrue("formatted log message must contain the name of its marker.", layoutMessage.contains(marker.getName()));
    // assertTrue("this pops up at the end of this test (with masking).", false);
  }

  /**
   * Test the ExcludeClassifiedMarkerFilter on an event with MultiMarker (other tests are done within OWASP).
   */
  @Test
  public void testExclClassifMarkerFilter() {

    Marker marker = SecureLogging.SECURITY_SUCCESS_CONFIDENTIAL;

    this.loggerLogback.info(marker, "confidential security message with MultiMarker.");

    // Now verify our logging interactions
    verify(this.mockAppender).doAppend(this.captorLoggingEvent.capture());

    // Get the logging event from the captor
    final LoggingEvent loggingEvent = this.captorLoggingEvent.getValue();

    // Check log level is correct
    assertThat(loggingEvent.getLevel()).isEqualTo(Level.INFO);

    // Check the stand-alone filter decision for this event
    assertEquals(FilterReply.DENY, this.filterExclClassif.decide(loggingEvent));

    // Check the filter chain decision for this event (does not work)
    // assertEquals(FilterReply.DENY, this.mockAppender.getFilterChainDecision(loggingEvent));
  }

  /**
   * Test if a combined Marker contains the names of its constituent Markers. This test is useful in particular if the
   * dependency org.owasp is not available, but also works when it is present. To test the fall back solution in
   * {@link SecureLogging}, one has to create a separate logging module strictly without the OWASP dependency.
   */
  @Test
  public void testInitMarkersByName() {

    // setup: SecureLogging.initMarkers() is called by the loc below.
    Marker multiMarker = SecureLogging.SECURITY_SUCCESS_CONFIDENTIAL;
    Marker securMarker = SecureLogging.SECURITY_SUCCESS;
    Marker confidMarker = SecureLogging.CONFIDENTIAL;

    // verify that the combined Marker or MultiMarker contains the names of its constituent Markers.
    assertFalse("name of combined marker must not be empty.", multiMarker.getName().isEmpty());
    assertTrue("name of combined marker must contain the names of its constituent Markers.",
        multiMarker.getName().contains(securMarker.getName()));
    assertTrue("name of combined marker must contain the names of its constituent Markers.",
        multiMarker.getName().contains(confidMarker.getName()));
  }

  /**
   * Test Marker creation if the dependency org.owasp is available, which provides the class
   * {@link org.owasp.security.logging.MultiMarker}.
   */
  @Test
  public void testInitWithMultiMarkerClass() {

    // skip test if the dependency is not available.
    if (!SecureLogging.hasExtClass())
      return;
    assertTrue("this test fails if the dependency org.owasp is not available.", SecureLogging.hasExtClass());

    // setup
    // SecureLogging.initMarkers() is called by the loc below:
    Marker multiMarker = SecureLogging.SECURITY_SUCCESS_CONFIDENTIAL;
    Marker securMarker = SecureLogging.SECURITY_SUCCESS;
    Marker confidMarker = SecureLogging.CONFIDENTIAL;

    // verify that the MultiMarker contains both simple Markers.
    assertTrue("MultiMarker must have references.", multiMarker.hasReferences());
    assertTrue("MultiMarker must contain both of its constituent Markers.", multiMarker.contains(securMarker));
    assertTrue("MultiMarker must contain both of its constituent Markers.", multiMarker.contains(confidMarker));
  }

}
