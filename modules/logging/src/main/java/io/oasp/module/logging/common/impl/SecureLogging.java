package io.oasp.module.logging.common.impl;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Class which provides {@link Marker}s and "MultiMarker"s (if dependency org.owasp available) for logging.
 *
 * example usage:
 * <code> LOG.info(SecureLogging.SECURITY_FAILURE_CONFIDENTIAL, "Confidential Security Failure message."); </code>
 *
 * example filters for appenders in logback.xml:
 * <code> class="org.owasp.security.logging.filter.SecurityMarkerFilter"</code>
 * <code> class="org.owasp.security.logging.filter.ExcludeClassifiedMarkerFilter" </code>
 *
 * @author preichel-cg
 */
public class SecureLogging {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(SecureLogging.class);

  private static String extClass = "org.owasp.security.logging.SecurityMarkers";

  private static String methodName = "getMarker";

  private static boolean initialized = false;

  private static Marker markerSecurSuccConfid = null;

  private static Marker markerSecurFailConfid = null;

  private static Marker markerSecurAuditConfid = null;

  private static final String CONFIDENTIAL_MARKER_NAME = "CONFIDENTIAL";

  private static final String SECURITY_SUCCESS_MARKER_NAME = "SECURITY SUCCESS";

  private static final String SECURITY_FAILURE_MARKER_NAME = "SECURITY FAILURE";

  private static final String SECURITY_AUDIT_MARKER_NAME = "SECURITY AUDIT";

  private static final String SECURITY_SUCCESS_CONFIDENTIAL_MARKER_NAME = "SECURITY SUCCESS CONFIDENTIAL";

  private static final String SECURITY_FAILURE_CONFIDENTIAL_MARKER_NAME = "SECURITY FAILURE CONFIDENTIAL";

  private static final String SECURITY_AUDIT_CONFIDENTIAL_MARKER_NAME = "SECURITY AUDIT CONFIDENTIAL";

  /**
   * Marker for Confidential log events.
   */
  public static final Marker CONFIDENTIAL = MarkerFactory.getDetachedMarker(CONFIDENTIAL_MARKER_NAME);

  /**
   * Marker for Security Success log events.
   */
  public static final Marker SECURITY_SUCCESS = MarkerFactory.getDetachedMarker(SECURITY_SUCCESS_MARKER_NAME);

  /**
   * Marker for Security Failure log events.
   */
  public static final Marker SECURITY_FAILURE = MarkerFactory.getDetachedMarker(SECURITY_FAILURE_MARKER_NAME);

  /**
   * Marker or MultiMarker for Confidential Security Success log events.
   */
  public static final Marker SECURITY_SUCCESS_CONFIDENTIAL = getMarkerSecurSuccConfid();

  /**
   * Marker or MultiMarker for Confidential Security Failure log events.
   */
  public static final Marker SECURITY_FAILURE_CONFIDENTIAL = getMarkerSecurFailConfid();

  /**
   * Marker or MultiMarker for Confidential Security Audit log events.
   */
  public static final Marker SECURITY_AUDIT_CONFIDENTIAL = getMarkerSecurAuditConfid();

  private SecureLogging() {
  }

  private static Marker getMarkerSecurSuccConfid() {

    initMarkers();
    return markerSecurSuccConfid;
  }

  private static Marker getMarkerSecurFailConfid() {

    initMarkers();
    return markerSecurFailConfid;
  }

  private static Marker getMarkerSecurAuditConfid() {

    initMarkers();
    return markerSecurAuditConfid;
  }

  /**
   * Main method to initialize the {@link Marker}s provided by this class.
   */
  private static void initMarkers() {

    if (initialized)
      return;

    Class<?> cExtClass = hasExtClass(extClass);

    if (cExtClass.isAssignableFrom(String.class)) {
      createDefaultMarkers();
    } else {
      createMultiMarkers(cExtClass);
    }

    if (!initialized)
      LOG.warn("SecureLogging Markers could not be initialized!");
    else
      LOG.debug("SecureLogging Markers created: '{}', ...", markerSecurSuccConfid.getName());
    return;
  }

  private static void createDefaultMarkers() {

    LOG.debug("Creating default markers.");
    markerSecurSuccConfid = MarkerFactory.getDetachedMarker(SECURITY_SUCCESS_CONFIDENTIAL_MARKER_NAME);
    markerSecurFailConfid = MarkerFactory.getDetachedMarker(SECURITY_FAILURE_CONFIDENTIAL_MARKER_NAME);
    markerSecurAuditConfid = MarkerFactory.getDetachedMarker(SECURITY_AUDIT_CONFIDENTIAL_MARKER_NAME);
    initialized = true;
  }

  private static void createMultiMarkers(Class<?> cExtClass) {

    LOG.debug("Creating MultiMarkers.");

    Object objExtClass = null;
    try {
      objExtClass = cExtClass.newInstance();
      Class<?>[] paramTypes = { Marker[].class }; // the method to invoke is "getMarker(Marker... markers)".
      Method method = cExtClass.getMethod(methodName, paramTypes);

      Marker[] markerArray = { MarkerFactory.getDetachedMarker(SECURITY_SUCCESS_MARKER_NAME),
      MarkerFactory.getDetachedMarker(CONFIDENTIAL_MARKER_NAME) };
      markerSecurSuccConfid = (Marker) method.invoke(objExtClass, (Object) markerArray);
      markerArray[0] = MarkerFactory.getDetachedMarker(SECURITY_FAILURE_MARKER_NAME);
      markerSecurFailConfid = (Marker) method.invoke(objExtClass, (Object) markerArray);
      markerArray[0] = MarkerFactory.getDetachedMarker(SECURITY_AUDIT_MARKER_NAME);
      markerSecurAuditConfid = (Marker) method.invoke(objExtClass, (Object) markerArray);
      initialized = true;

    } catch (Exception e) {
      LOG.warn("Error getting Method '{}' of Class '{}'. Falling back to default.", methodName, cExtClass.getName());
      LOG.warn("Exception occurred.", e);
      e.printStackTrace();
      createDefaultMarkers();
    }
  }

  /**
   * @return The given {@link Class} if parameter 'className' can be resolved, otherwise {@link String}.class.
   */
  private static Class<?> hasExtClass(String className) {

    Class<?> cExtClass;
    try {
      cExtClass = Class.forName(className);
      return cExtClass;
    } catch (Exception e) {
      LOG.debug("Class '{}' or one of its dependencies is not present.", className);
      // LOG.debug("Exception occured.", e);
      cExtClass = String.class;
      return cExtClass;
    }
  }

  /**
   * @return extClass
   */
  public static String getExtClass() {

    return extClass;
  }

  /**
   * @param extClass new value of {@link #getExtClass}.
   */
  public static void setExtClass(String extClass) {

    SecureLogging.extClass = extClass;
  }

  /**
   * @return methodName
   */
  public static String getMethodName() {

    return methodName;
  }

  /**
   * @param methodName new value of {@link #getMethodName}.
   */
  public static void setMethodName(String methodName) {

    SecureLogging.methodName = methodName;
  }

}
