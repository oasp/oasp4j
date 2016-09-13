package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;

public class DrinkEntityBuilder {

  private List<P<DrinkEntity>> parameterToBeApplied;

  public static final String NAME_DRINK = "Spring Paradise";

  public static final String DESCRIPTION_DRINK = "without alcohol";

  public static final boolean FLAG_DRINK_ALCOHOLIC = false;

  public DrinkEntityBuilder() {

    this.parameterToBeApplied = new LinkedList<P<DrinkEntity>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

  public DrinkEntityBuilder alcoholic(final boolean alcoholic) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setAlcoholic(alcoholic);
      }
    });
    return this;
  }

  public DrinkEntityBuilder pictureId(final Long pictureId) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setPictureId(pictureId);
      }
    });
    return this;
  }

  public DrinkEntityBuilder name(final String name) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setName(name);
      }
    });
    return this;
  }

  public DrinkEntityBuilder description(final String description) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setDescription(description);
      }
    });
    return this;
  }

  public DrinkEntityBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  public DrinkEntity createNew() {

    DrinkEntity drinkentity = new DrinkEntity();

    name(NAME_DRINK);
    description(DESCRIPTION_DRINK);
    alcoholic(FLAG_DRINK_ALCOHOLIC);

    for (P<DrinkEntity> parameter : this.parameterToBeApplied) {
      parameter.apply(drinkentity);
    }
    return drinkentity;
  }

}
