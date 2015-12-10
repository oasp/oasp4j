package io.oasp.module.configuration.common.api;

/**
 * Central constants for spring configuration files. Mainly for testing but may also be used for productive usage.
 *
 * @author hohwille
 */
public final class ApplicationConfigurationConstants {

  /** Path to the spring XML configuration file for the entire application. */
  public static final String BEANS_APPLICATION = "classpath:/config/app/beans-application.xml";

  /** Path to the spring XML configuration file for the service layer. */
  public static final String BEANS_SERVICE = "classpath:/config/app/service/beans-service.xml";

  /** Path to the spring XML configuration file for the logic layer. */
  public static final String BEANS_LOGIC = "classpath:/config/app/logic/beans-logic.xml";

  /** Path to the spring XML configuration file for the data-acccess layer. */
  public static final String BEANS_DATA_ACCESS = "classpath:/config/app/dataaccess/beans-dataaccess.xml";

  /** Path to the spring XML configuration file for the batch layer. */
  public static final String BEANS_BATCH = "classpath:/config/app/batch/beans-batch.xml";

  /**
   * Path to the spring XML configuration file for the common and cross-cutting code that is not assigned to any layer.
   */
  public static final String BEANS_COMMON = "classpath:/config/app/common/beans-common.xml";

  /** Path to the spring XML configuration file for the security. */
  public static final String BEANS_SECURITY = "classpath:/config/app/security/beans-security.xml";

  /** Path to the XML configuration of the access control schema. */
  public static final String SECURITY_ACCESS_CONTROL_SCHEMA = "config/app/security/access-control-schema.xml";

  /**
   * Construction prohibited.
   */
  private ApplicationConfigurationConstants() {

    super();
  }

}
