package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.general.logic.base.AbstractComponentFacade;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcFindTable;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase.UcManageTable;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation of {@link Tablemanagement}.
 *
 * @author etomety
 */
@Named
public class TablemanagementImpl extends AbstractComponentFacade implements Tablemanagement {

  private UcFindTable ucFindTable;

  private UcManageTable ucManageTable;

  /**
   * The constructor.
   */
  public TablemanagementImpl() {

    super();
  }

  /**
   * Sets the field 'ucFindTable'.
   *
   * @param ucFindTable New value for ucFindTable
   */
  @Inject
  @UseCase
  public void setUcFindTable(UcFindTable ucFindTable) {

    this.ucFindTable = ucFindTable;
  }

  /**
   * Sets the field 'ucManageTable'.
   *
   * @param ucManageTable New value for ucManageTable
   */
  @Inject
  @UseCase
  public void setUcManageTable(UcManageTable ucManageTable) {

    this.ucManageTable = ucManageTable;
  }

  @Override
  public TableEto findTable(Long id) {

    return this.ucFindTable.findTable(id);
  }

  @Override
  public List<TableEto> findAllTables() {

    return this.ucFindTable.findAllTables();
  }

  @Override
  public TableEto saveTable(TableEto table) {

    return this.ucManageTable.saveTable(table);
  }

  @Override
  public void deleteTable(Long id) {

    this.ucManageTable.deleteTable(id);
  }

  @Override
  public List<TableEto> findFreeTables() {

    return this.ucFindTable.findFreeTables();
  }

  @Override
  public boolean isTableReleasable(TableEto table) {

    return this.ucManageTable.isTableReleasable(table);
  }

  @Override
  public PaginatedListTo<TableEto> findTableEtos(TableSearchCriteriaTo criteria) {

    return this.ucFindTable.findTableEtos(criteria);
  }

}
