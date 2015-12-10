package io.oasp.gastronomy.restaurant.tablemanagement.common.builders;

import io.oasp.gastronomy.restaurant.general.common.P;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

import java.util.LinkedList;
import java.util.List;

public class TableEtoBuilder {

  private List<P<TableEto>> parameterToBeApplied;

  public TableEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<P<TableEto>>();
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

  public TableEtoBuilder waiterId(final Long waiterId) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setWaiterId(waiterId);
      }
    });
    return this;
  }

  public TableEtoBuilder number(final Long number) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setNumber(number);
      }
    });
    return this;
  }

  public TableEtoBuilder state(final TableState state) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setState(state);
      }
    });
    return this;
  }

  public TableEto createNew() {

    TableEto tableeto = new TableEto();
    for (P<TableEto> parameter : this.parameterToBeApplied) {
      parameter.apply(tableeto);
    }
    return tableeto;
  }

}
