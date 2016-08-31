package io.oasp.gastronomy.restaurant.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * Test data builder for TableEto generated with cobigen.
 */
public class TableEtoBuilder {

  private List<P<TableEto>> parameterToBeApplied;

  /**
   * The constructor.
   */
  public TableEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * @param number the number to add.
   * @return the builder for fluent population of fields.
   */
  public TableEtoBuilder number(final Long number) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setNumber(number);
      }
    });
    return this;
  }

  /**
   * @param waiterId the waiterId to add.
   * @return the builder for fluent population of fields.
   */
  public TableEtoBuilder waiterId(final Long waiterId) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setWaiterId(waiterId);
      }
    });
    return this;
  }

  /**
   * @param state the state to add.
   * @return the builder for fluent population of fields.
   */
  public TableEtoBuilder state(final TableState state) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setState(state);
      }
    });
    return this;
  }

  /**
   * @param revision the revision to add.
   * @return the builder for fluent population of fields.
   */
  public TableEtoBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  /**
   * @return the populated TableEto.
   */
  public TableEto createNew() {

    TableEto tableeto = new TableEto();
    for (P<TableEto> parameter : parameterToBeApplied) {
      parameter.apply(tableeto);
    }
    return tableeto;
  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

  /**
   * Might be enriched to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

}
