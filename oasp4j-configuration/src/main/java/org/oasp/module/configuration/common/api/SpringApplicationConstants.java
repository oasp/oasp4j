package org.oasp.module.configuration.common.api;

/**
 * Central constants for spring configuration files. Mainly for testing but also for productive usage.
 * 
 * @author hohwille
 */
public final class SpringApplicationConstants {

  /** Path to the spring XML configuration file for the entire application. */
  public static final String CONFIG_APPLICATION = "classpath:/resources/beans-application.xml";

  /** Path to the spring XML configuration file for the service layer. */
  public static final String CONFIG_SERVICE = "classpath:/resources/service/beans-service.xml";

  /** Path to the spring XML configuration file for the logic layer. */
  public static final String CONFIG_LOGIC = "classpath:/resources/logic/beans-logic.xml";

  /** Path to the spring XML configuration file for the persistence layer. */
  public static final String CONFIG_PERSISTENCE = "classpath:/resources/persistence/beans-persistence.xml";

  /** Path to the spring XML configuration file for the common and cross-cutting code that is not assigned to any layer. */
  public static final String CONFIG_COMMON = "classpath:/resources/common/beans-common.xml";

  /**
   * Construction prohibited.
   */
  private SpringApplicationConstants() {

    super();
  }

}
