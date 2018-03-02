package ${package}.general.service.impl.rest;

import javax.inject.Named;

import io.oasp.module.json.common.base.ObjectMapperFactory;

/**
 * The MappingFactory class to resolve polymorphic conflicts within the ${rootArtifactId} application.
 */
@Named("ApplicationObjectMapperFactory")
public class ApplicationObjectMapperFactory extends ObjectMapperFactory {

  /**
   * The constructor.
   */
  public ApplicationObjectMapperFactory() {

    super();
    // register polymorphic mapping here - see https://github.com/oasp-forge/oasp4j-wiki/wiki/guide-json#json-and-inheritance

  }
}
