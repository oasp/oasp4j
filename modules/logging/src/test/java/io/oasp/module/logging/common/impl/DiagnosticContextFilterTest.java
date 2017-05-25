package io.oasp.module.logging.common.impl;

import static org.mockito.Mockito.when;

import javax.servlet.FilterConfig;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.util.ReflectionTestUtils;

import io.oasp.module.test.common.base.AbstractModuleTest;

/**
 *
 * @author jmolinar
 */
public class DiagnosticContextFilterTest extends AbstractModuleTest {

  /**
   *
   */
  private static final String CORRELATION_ID_HEADER_NAME_PARAM = "correlationIdHttpHeaderName";

  /**
   *
   */
  private static final String CORRELATION_ID_HEADER_NAME_PARAM_FIELD_NAME = "CORRELATION_ID_HEADER_NAME_PARAM";

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  private FilterConfig config;

  @Test
  public void testCorrelationIdHttpHeaderNameAfterConstructor() {

    // setup
    DiagnosticContextFilter filter = new DiagnosticContextFilter();

    // exercise
    String correlationIdHttpHeaderName =
        (String) ReflectionTestUtils.getField(filter, CORRELATION_ID_HEADER_NAME_PARAM);

    // verify
    assertThat(correlationIdHttpHeaderName).isNotNull();
  }

  @Test
  public void testInitWithNullInitParameter() throws Exception {

    // setup
    DiagnosticContextFilter filter = new DiagnosticContextFilter();
    String field = (String) ReflectionTestUtils.getField(DiagnosticContextFilter.class,
        CORRELATION_ID_HEADER_NAME_PARAM_FIELD_NAME);
    assertThat(field).isNotNull();
    when(this.config.getInitParameter(field)).thenReturn(null);

    // exercise
    filter.init(this.config);

    // verify
    String correlationIdHttpHeaderName =
        (String) ReflectionTestUtils.getField(filter, CORRELATION_ID_HEADER_NAME_PARAM);
    assertThat(correlationIdHttpHeaderName).isNotNull()
        .isEqualTo(DiagnosticContextFilter.CORRELATION_ID_HEADER_NAME_DEFAULT);
  }

  @Test
  public void testInitWithNonDefaultParameter() throws Exception {

    // setup
    DiagnosticContextFilter filter = new DiagnosticContextFilter();
    String field = (String) ReflectionTestUtils.getField(DiagnosticContextFilter.class,
        CORRELATION_ID_HEADER_NAME_PARAM_FIELD_NAME);
    assertThat(field).isNotNull();
    String nonDefaultParameter = "test";
    when(this.config.getInitParameter(field)).thenReturn(nonDefaultParameter);

    // exercise
    filter.init(this.config);
    // verify
    String correlationIdHttpHeaderName =
        (String) ReflectionTestUtils.getField(filter, CORRELATION_ID_HEADER_NAME_PARAM);
    assertThat(correlationIdHttpHeaderName).isEqualTo(nonDefaultParameter);
  }
}
