package io.oasp.module.security.common.base.accesscontrol;

import io.oasp.module.security.common.api.accesscontrol.AccessControlSchema;

/**
 * This is the interface to {@link #loadSchema() load} the {@link AccessControlSchema} from an arbitrary source. The
 * default implementation will load it from an XML file. You could create your own implementation to read from database
 * or wherever if default is not suitable.
 *
 * @author hohwille
 */
public interface AccessControlSchemaProvider {

  /**
   * @return the loaded {@link AccessControlSchema}. May not be {@code null}.
   */
  AccessControlSchema loadSchema();

}
