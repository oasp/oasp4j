package io.oasp.gastronomy.restaurant.tablemanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

import javax.validation.Valid;

/**
 * Interface of UcManageTable to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageTable {

  /** @see net.sf.mmm.util.component.api.Cdi#CDI_NAME */
  String CDI_NAME = "UcManageTable";

  /**
   * Deletes a restaurant table from the database by its id 'id'.
   *
   * @param tableId Id of the restaurant table to delete
   */
  void deleteTable(Long tableId);

  /**
   * Creates a new restaurant table and store it in the database.
   *
   * @param table the {@link TableEto} to create.
   * @return the new {@link TableEto} that has been saved with ID and version.
   */
  TableEto saveTable(@Valid TableEto table);

  /**
   * Evaluate if this table could marked as free.
   *
   * @param table {@link TableEto} to be evaluate
   * @return <code>true</code> if the table could be released<br>
   *         <code>false</code> , otherwise
   */
  boolean isTableReleasable(@Valid TableEto table);

}
