package org.oasp.module.security.common.exception;

/**
 * Signals, that the requested permission has been denied
 * 
 * @author mbrunnli
 * @version $Id:$
 */
public class PermissionDeniedException extends Exception {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new {@link PermissionDeniedException} with the given error message
   * 
   * @param message error message
   */
  public PermissionDeniedException(String message) {

    super(message);
  }

}
