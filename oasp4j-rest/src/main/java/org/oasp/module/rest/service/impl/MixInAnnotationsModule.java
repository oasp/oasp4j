package org.oasp.module.rest.service.impl;

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
   *
   */
  public MixInAnnotationsModule(Class<?>... baseClasses) {

    super("PolymorphieModule", new Version(0, 0, 1, null));
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

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "@type")
  public static class JacksonPolymorphicAnnotation {

  }

}
