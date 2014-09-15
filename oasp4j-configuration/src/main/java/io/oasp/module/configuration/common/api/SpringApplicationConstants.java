package io.oasp.module.configuration.common.api;

/**
 * Central constants for spring configuration files. Mainly for testing but may
 * also be used for productive usage.
 *
 * @author hohwille
 */
public final class SpringApplicationConstants {

  /** Path to the spring XML configuration file for the entire application. */
  public static final String BEANS_APPLICATION = "classpath:/config/app/beans-application.xml";

  /** Path to the spring XML configuration file for the service layer. */
  public static final String BEANS_SERVICE = "classpath:/config/app/service/beans-service.xml";

  /**
   * Path to the spring XML configuration file for the service layer.
   *
   * @deprecated should IMHO be removed. Otherwise renamed to
   *             BEANS_TEST_SERIVCE_REST but appears too specific. JavaDoc is
   *             incorrect.
   */
  @Deprecated
  public static final String CONFIG_SERVICE_TEST = "classpath:/config/app/service/beans-test-service-rest.xml";

  /** Path to the spring XML configuration file for the logic layer. */
  public static final String BEANS_LOGIC = "classpath:/config/app/logic/beans-logic.xml";

  /** Path to the spring XML configuration file for the persistence layer. */
  public static final String BEANS_PERSISTENCE = "classpath:/config/app/persistence/beans-persistence.xml";

  /**
   * Path to the spring XML configuration file for the common and cross-cutting
   * code that is not assigned to any layer.
   */
  public static final String BEANS_COMMON = "classpath:/config/app/common/beans-common.xml";

  /**
   * Construction prohibited.
   */
  private SpringApplicationConstants() {

    super();
  }

}