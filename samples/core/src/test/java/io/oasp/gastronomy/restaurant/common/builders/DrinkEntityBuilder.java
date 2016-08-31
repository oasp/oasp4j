package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;

/**
 * Test data builder for DrinkEntity generated with cobigen.
 */
public class DrinkEntityBuilder {

  private List<P<DrinkEntity>> parameterToBeApplied;

  /**
   * The constructor.
   */
  public DrinkEntityBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

  /**
   * @param alcoholic the alcoholic to add.
   * @return the builder for fluent population of fields.
   */
  public DrinkEntityBuilder alcoholic(final boolean alcoholic) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setAlcoholic(alcoholic);
      }
    });
    return this;
  }

  /**
   * @param pictureId the pictureId to add.
   * @return the builder for fluent population of fields.
   */
  public DrinkEntityBuilder pictureId(final Long pictureId) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setPictureId(pictureId);
      }
    });
    return this;
  }

  /**
   * @param name the name to add.
   * @return the builder for fluent population of fields.
   */
  public DrinkEntityBuilder name(final String name) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setName(name);
      }
    });
    return this;
  }

  /**
   * @param description the description to add.
   * @return the builder for fluent population of fields.
   */
  public DrinkEntityBuilder description(final String description) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setDescription(description);
      }
    });
    return this;
  }

  /**
   * @param revision the revision to add.
   * @return the builder for fluent population of fields.
   */
  public DrinkEntityBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<DrinkEntity>() {
      @Override
      public void apply(DrinkEntity target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  /**
   * @return the populated DrinkEntity.
   */
  public DrinkEntity createNew() {

    DrinkEntity drinkentity = new DrinkEntity();
    for (P<DrinkEntity> parameter : parameterToBeApplied) {
      parameter.apply(drinkentity);
    }
    return drinkentity;
  }

  /**
   * Might be enriched to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

}
