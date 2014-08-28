package org.oasp.module.rest.service.impl.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 *
 * @author agreul
 */
public class MixInAnnotationsModule extends SimpleModule {

  private final Class<?>[] baseClasses;

  /**
   * @param baseClasses the baseClasses to be resolved into correspondent
   *        subclasses
   */
  public MixInAnnotationsModule(Class<?>... baseClasses) {

    super("PolymorphyModule", new Version(0, 0, 1, null));
    this.baseClasses = baseClasses;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupModule(SetupContext context) {

    for (Class<?> baseClass : this.baseClasses) {
      context.setMixInAnnotations(baseClass, JacksonPolymorphicAnnotation.class);
    }
  }

  /**
   * The blueprint class for the following JSON-annotation allowing to convert
   * from JSON to POJO and vice versa
   * 
   * @author agreul
   */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "@type")
  public static class JacksonPolymorphicAnnotation {

  }

}
