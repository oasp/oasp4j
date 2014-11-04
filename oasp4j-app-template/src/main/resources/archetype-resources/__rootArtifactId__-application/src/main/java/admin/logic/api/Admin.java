#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.admin.logic.api;

/**
 * 
 * Interface which provides methods to check the system.
 * 
 * @author mvielsac
 */
public interface Admin {
  /**
   * Returns the given string to check the availability of the system.
   * 
   * @param message String to return
   * @return the given message
   */
  String ping(String message);

  /**
   * Runs a test if the system is working correctly.
   * 
   * @return test result of the check
   */
  boolean checkSystem();
}
