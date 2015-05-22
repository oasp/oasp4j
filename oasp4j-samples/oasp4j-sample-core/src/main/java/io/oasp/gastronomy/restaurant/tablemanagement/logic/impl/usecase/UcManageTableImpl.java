package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.Staffmanagement;
import io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to.StaffMemberEto;
import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;
import io.oasp.gastronomy.restaurant.tablemanagement.dataaccess.api.TableEntity;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcManageTable;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.base.usecase.AbstractTableUc;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

/**
 * Implementation of {@link UcManageTable}.
 *
 * @author jozitz
 */
@Named
@UseCase
@Validated
public class UcManageTableImpl extends AbstractTableUc implements UcManageTable {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageTableImpl.class);

  private Salesmanagement salesManagement;

  private Staffmanagement staffmanagement;

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.DELETE_TABLE)
  public void deleteTable(Long tableId) {

    TableEntity table = getTableDao().find(tableId);

    if (!table.getState().isFree()) {
      throw new IllegalEntityStateException(table, table.getState());
    }

    getTableDao().delete(table);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.SAVE_TABLE)
  public TableEto saveTable(@Valid TableEto table) {

    Objects.requireNonNull(table, "table");

    Long tableId = table.getId();

    TableEntity tableEntity = getBeanMapper().map(table, TableEntity.class);
    // initialize
    if (tableEntity.getState() == null) {
      tableEntity.setState(TableState.FREE);
    }
    Long waiterId = tableEntity.getWaiterId();
    if (waiterId != null) {
      StaffMemberEto staffMember = this.staffmanagement.findStaffMember(waiterId);
      if (Role.WAITER != staffMember.getRole()) {
        throw new IllegalArgumentException("Staffmember with id " + waiterId + " has role " + staffMember.getRole()
            + " and can not be associated as waiter for table with ID " + tableId + "!");
      }
    }

    getTableDao().save(tableEntity);
    LOG.debug("Table with id '{}' has been created.", tableEntity.getId());
    return getBeanMapper().map(tableEntity, TableEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @RolesAllowed(PermissionConstants.FIND_TABLE)
  public boolean isTableReleasable(TableEto table) {

    if (table.getState() != TableState.OCCUPIED) {
      return true;
    }
    OrderEto order = this.salesManagement.findOpenOrderForTable(table.getId());
    // no open order so the table is actually free...
    return order == null;
  }

  /**
   * Sets the field 'salesManagement'.
   *
   * @param salesManagement new value for salesManagement
   */
  @Inject
  public void setSalesManagement(Salesmanagement salesManagement) {

    this.salesManagement = salesManagement;
  }

  /**
   * @param staffmanagement the staffmanagement to set
   */
  @Inject
  public void setStaffmanagement(Staffmanagement staffmanagement) {

    this.staffmanagement = staffmanagement;
  }

}
