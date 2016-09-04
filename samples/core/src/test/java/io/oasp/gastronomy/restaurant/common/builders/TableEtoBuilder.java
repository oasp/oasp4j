package io.oasp.gastronomy.restaurant.common.builders;

import static io.oasp.gastronomy.restaurant.staffmanagement.common.StaffmanagementTestDataConstants.ID_WAITER;
import static io.oasp.gastronomy.restaurant.tablemanagement.common.TablemanagementTestDataConstants.NUMBER_TABLE_NEW;

import java.util.LinkedList;
import java.util.List;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

public class TableEtoBuilder {

  private List<P<TableEto>> parameterToBeApplied;

  public static final TableState STATE_TABLE = TableState.FREE;

  public TableEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<P<TableEto>>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * Might be enrichted to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

    number(NUMBER_TABLE_NEW);

  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

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

  public TableEtoBuilder waiterId(final Long waiterId) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setWaiterId(waiterId);
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

  public TableEtoBuilder revision(final Number revision) {

    this.parameterToBeApplied.add(new P<TableEto>() {
      @Override
      public void apply(TableEto target) {

        target.setRevision(revision);
      }
    });
    return this;
  }

  public TableEto createNew() {

    TableEto tableeto = new TableEto();
    tableeto.setState(STATE_TABLE);
    tableeto.setWaiterId(ID_WAITER);
    for (P<TableEto> parameter : this.parameterToBeApplied) {
      parameter.apply(tableeto);
    }
    return tableeto;
  }

}
