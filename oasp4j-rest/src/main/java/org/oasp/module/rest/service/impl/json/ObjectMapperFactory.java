package org.oasp.module.rest.service.impl.json;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.jsontype.NamedType;
import org.codehaus.jackson.map.jsontype.SubtypeResolver;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 * A generic factory to {@link #createInstance() create} instances of a Jackson {@link ObjectMapper}. It allows to
 * configure the {@link ObjectMapper} for polymorphic transfer-objects.
 *
 * @see #setBaseClasses(Class...)
 * @see #setSubtypes(NamedType...)
 *
 * @author hohwille
 * @author agreul
 */
public class ObjectMapperFactory {

  private Class<?>[] baseClasses;

  private List<NamedType> subtypeList;

  /**
   * The constructor.
   */
  public ObjectMapperFactory() {

    super();
  }

  /**
   * @param baseClasses are the base classes that are polymorphic (e.g. abstract transfer-object classes that have
   *        sub-types). You also need to register all sub-types of these polymorphic classes via
   *        {@link #setSubtypes(NamedType...)}.
   */
  public void setBaseClasses(Class<?>... baseClasses) {

    this.baseClasses = baseClasses;
  }

  /**
   * @see #setSubtypes(NamedType...)
   *
   * @param subtypeList the {@link List} of {@link NamedType}s to register the subtypes.
   */
  public void setSubtypeList(List<NamedType> subtypeList) {

    this.subtypeList = subtypeList;
  }

  /**
   * @param subtypeList the {@link NamedType}s as pair of {@link Class} reflecting a polymorphic sub-type together with
   *        its unique name in JSON format.
   */
  public void setSubtypes(NamedType... subtypeList) {

    setSubtypeList(Arrays.asList(subtypeList));
  }

  /**
   * @return an instance of {@link ObjectMapper} configured for polymorphic resolution.
   */
  public ObjectMapper createInstance() {

    ObjectMapper mapper = new ObjectMapper();

    if ((this.baseClasses != null) && (this.baseClasses.length > 0)) {
      SimpleModule polymorphyModule = new MixInAnnotationsModule(this.baseClasses);
      mapper.registerModule(polymorphyModule);
    }

    if (this.subtypeList != null) {
      SubtypeResolver subtypeResolver = mapper.getSubtypeResolver();
      for (NamedType subtype : this.subtypeList) {
        subtypeResolver.registerSubtypes(subtype);
      }
      mapper.setSubtypeResolver(subtypeResolver);
    }

    return mapper;
  }
}
