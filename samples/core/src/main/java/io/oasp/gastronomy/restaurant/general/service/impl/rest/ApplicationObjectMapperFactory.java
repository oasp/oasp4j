package io.oasp.gastronomy.restaurant.general.service.impl.rest;

// BEGIN ARCHETYPE SKIP
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
// END ARCHETYPE SKIP
import io.oasp.module.rest.service.impl.json.ObjectMapperFactory;

import javax.inject.Named;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * The MappingFactory class to resolve polymorphic conflicts within the restaurant application.
 *
 */
@Named("ApplicationObjectMapperFactory")
public class ApplicationObjectMapperFactory extends ObjectMapperFactory {

  /**
   * The constructor.
   */
  public ApplicationObjectMapperFactory() {

    super();
    // register polymorphic base classes
    // BEGIN ARCHETYPE SKIP
    setBaseClasses(ProductEto.class);
    // END ARCHETYPE SKIP

    NamedType[] subtypes;
    // register mapping for polymorphic sub-classes
    // BEGIN ARCHETYPE SKIP
    subtypes =
        new NamedType[] { new NamedType(MealEto.class, "Meal"), new NamedType(DrinkEto.class, "Drink"),
        new NamedType(SideDishEto.class, "SideDish") };
    setSubtypes(subtypes);

    // register (de)serializers for custom datatypes
    SimpleModule module = getExtensionModule();
    module.addDeserializer(Money.class, new MoneyJsonDeserializer());
    module.addSerializer(Money.class, new MoneyJsonSerializer());
    // END ARCHETYPE SKIP
  }
}
