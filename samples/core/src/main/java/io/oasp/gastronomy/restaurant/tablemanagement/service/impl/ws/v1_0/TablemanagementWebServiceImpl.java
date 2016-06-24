package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.ws.v1_0;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.ws.v1_0.TablemanagmentWebService;

/**
 * Implementation of {@link TablemanagmentWebService}.
 *
 * @author jmetzler
 */
@Named("TablemanagementWebService")
@WebService(endpointInterface = "io.oasp.gastronomy.restaurant.tablemanagement.service.api.ws.v1_0.TablemanagmentWebService")
public class TablemanagementWebServiceImpl implements TablemanagmentWebService {

  private Tablemanagement tableManagement;

  /**
   * This method sets the field <tt>tableManagement</tt>.
   *
   * @param tableManagement the new value of the field ${bare_field_name}
   */
  @Inject
  public void setTableManagement(Tablemanagement tableManagement) {

    this.tableManagement = tableManagement;
  }

  @Override
  public TableEto getTable(String id) {

    long idAsLong;
    if (id == null) {
      throw new BadRequestException("missing id");
    }

    TableEto tableEto = null;

    try {
      idAsLong = Long.parseLong(id);
      tableEto = this.tableManagement.findTable(idAsLong);

      if (tableEto == null)
        throw new NotFoundException("table not found");

    } catch (NumberFormatException e) {
      throw new BadRequestException("id is not a number");
    } catch (NotFoundException e) {
      throw new BadRequestException(e.getMessage());
    }
    return this.tableManagement.findTable(idAsLong);
  }

}
