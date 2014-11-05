#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api;


/**
 * Interface to get a user from its login.
 *
 * @author jmetzler
 */
public interface Usermanagement {

  /**
   * @param login The login of the requested user.
   * @return The {@link UserProfile} with the given <code>login</code> or <code>null</code> if no such object exists.
   */
  UserProfile findStaffMemberByLogin(String login);

}
