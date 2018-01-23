#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the {@link NlsBundle} for this application.
 *
 */
public interface NlsBundleApplicationRoot extends NlsBundle {

  /**
   * @see ${package}.general.common.api.exception.IllegalEntityStateException
   *
   * @param entity is the entity relevant for the error.
   * @param state is the state of the entity that caused the operation to fail.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The entity {entity} is in state {state}!")
  NlsMessage errorIllegalEntityState(@Named("entity") Object entity, @Named("state") Object state);

  /**
   * @see ${package}.general.common.api.exception.IllegalEntityStateException
   *
   * @param entity is the entity relevant for the error.
   * @param currentState is the current state of the entity.
   * @param newState is the new state for the entity that is illegal.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The entity {entity} in state {currentState} can not be changed to state {newState}!")
  NlsMessage errorIllegalEntityStateChange(@Named("entity") Object entity, @Named("currentState") Object currentState,
      @Named("newState") Object newState);

  /**
   * @see ${package}.general.common.api.exception.IllegalEntityStateException
   *
   * @param object is the entity relevant for the error.
   * @param property is the property of the entity that can not be changed.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The property {property} of object {object} can not be changed!")
  NlsMessage errorIllegalPropertyChange(@Named("object") Object object, @Named("property") Object property);

  /**
   * @see ${package}.general.common.api.exception.NoActiveUserException
   *
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("There is currently no user logged in")
  NlsMessage errorNoActiveUser();

  

}
