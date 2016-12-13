package io.oasp.gastronomy.restaurant.general.common.api.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;

public class DrinkEntityBuilder {

  private List<P<DrinkEntity>> parameterToBeApplied;

  public DrinkEntityBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
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
    for (P<DrinkEntity> parameter : this.parameterToBeApplied) {
      parameter.apply(drinkentity);
    }
    return drinkentity;
  }

}
