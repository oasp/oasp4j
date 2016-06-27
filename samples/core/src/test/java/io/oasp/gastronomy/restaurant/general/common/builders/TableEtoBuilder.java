package io.oasp.gastronomy.restaurant.general.common.builders;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

public class TableEtoBuilder {

  private List<P<TableEto>> parameterToBeApplied;

  public TableEtoBuilder() {

    parameterToBeApplied = new LinkedList<P<TableEto>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  public TableEtoBuilder number(final Long number) {

    parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setNumber(number);
      }
    });
    return this;
  }

  public TableEtoBuilder waiterId(final Long waiterId) {

    parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setWaiterId(waiterId);
      }
    });
    return this;
  }

  public TableEtoBuilder state(final TableState state) {

    parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setState(state);
      }
    });
    return this;
  }

  public TableEtoBuilder revision(final Number revision) {

    parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

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
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

}
