package org.oasp.module.rest.service.impl.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 * A {@link SimpleModule} to extend Jackson to mixin annotations for polymorphic types.
 *
 * @author hohwille
 * @author agreul
 */
public class MixInAnnotationsModule extends SimpleModule {

  private final Class<?>[] polymorphicClasses;

  /**
   * @param polymorphicClasses the classes reflecting JSON transfer-objects that are polymorphic.
   */
  public MixInAnnotationsModule(Class<?>... polymorphicClasses) {

    super("PolymorphyModule", new Version(0, 0, 1, null));
    this.polymorphicClasses = polymorphicClasses;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupModule(SetupContext context) {

    for (Class<?> type : this.polymorphicClasses) {
      context.setMixInAnnotations(type, JacksonPolymorphicAnnotation.class);
    }
  }

  /**
   * The blueprint class for the following JSON-annotation allowing to convert from JSON to POJO and vice versa
   *
   * @author agreul
   */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "@type")
  public static class JacksonPolymorphicAnnotation {

  }

}
