#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.admin.logic.impl;

import ${package}.admin.logic.api.Admin;

import javax.inject.Named;

/**
 * 
 * Implementation of the Admin interface.
 * 
 * @author mvielsac
 */
@Named
public class AdminImpl implements Admin {

  /**
   * {@inheritDoc}
   */
  @Override
  public String ping(String value) {

    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean checkSystem() {

    // TODO do a check is system is working correctly
    return true;
  }

}
