package org.oasp.module.rest.service.impl;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.jsontype.NamedType;
import org.codehaus.jackson.map.jsontype.SubtypeResolver;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 *
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
   * @param baseClasses the baseClasses to set
   */
  public void setBaseClasses(Class<?>... baseClasses) {

    this.baseClasses = baseClasses;
  }

  /**
   * @param subtypeList the subtypeList to set
   */
  public void setSubtypeList(List<NamedType> subtypeList) {

    this.subtypeList = subtypeList;
  }

  /**
   * @param subtypeList the subtypeList to set
   */
  public void setSubtypes(NamedType... subtypeList) {

    setSubtypeList(Arrays.asList(subtypeList));
  }

  // TODO refactor to generic method, into config of spring
  public ObjectMapper createInstance() throws ClassNotFoundException {

    ObjectMapper mapper = new ObjectMapper();
    mapper.enableDefaultTyping();

    // AbstractBo.class
    SimpleModule polymorphieModule = new MixInAnnotationsModule(this.baseClasses);
    mapper.registerModule(polymorphieModule);

    SubtypeResolver subtypeResolver = mapper.getSubtypeResolver();
    for (NamedType subtype : this.subtypeList) {
      subtypeResolver.registerSubtypes(subtype);
    }
    // subtypeResolver.registerSubtypes(new NamedType(MealBo.class, "Meal"));
    // subtypeResolver.registerSubtypes(new NamedType(DrinkBo.class, "Drink"));
    // subtypeResolver.registerSubtypes(new NamedType(SideDishBo.class,
    // "SideDish"));
    mapper.setSubtypeResolver(subtypeResolver);

    return mapper;
  }
}
