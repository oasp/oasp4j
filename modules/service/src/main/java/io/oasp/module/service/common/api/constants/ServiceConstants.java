package io.oasp.module.service.common.api.constants;

import net.sf.mmm.util.exception.api.NlsRuntimeException;

/**
 * Constants for {@link io.oasp.module.service.common.api.Service}s.
 */
public class ServiceConstants {

  /** Key for {@link Throwable#getMessage() error message}. */
  public static final String KEY_MESSAGE = "message";

  /** Key for {@link NlsRuntimeException#getUuid() error ID}. */
  public static final String KEY_UUID = "uuid";

  /** Key for {@link NlsRuntimeException#getCode() error code}. */
  public static final String KEY_CODE = "code";

  /** Key for (validation) error details. */
  public static final String KEY_ERRORS = "errors";

  /** The services URL folder. */
  public static final String URL_FOLDER_SERVICES = "services";

  /** The services URL path. */
  public static final String URL_PATH_SERVICES = "/" + URL_FOLDER_SERVICES;

  /** The rest URL folder. */
  public static final String URL_FOLDER_REST = "rest";

  /** The web-service URL folder. */
  public static final String URL_FOLDER_WEB_SERVICE = "ws";

  /** The rest services URL path. */
  public static final String URL_PATH_REST_SERVICES = URL_PATH_SERVICES + "/" + URL_FOLDER_REST;

  /** The web-service URL path. */
  public static final String URL_PATH_WEB_SERVICES = URL_PATH_SERVICES + "/" + URL_FOLDER_WEB_SERVICE;

}
