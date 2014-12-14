package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.ws.v1_0;

import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.ws.v1_0.TablemanagmentWebService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

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

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("javadoc")
  @Override
  public TableEto getTable(String id) {

    Long idAsLong;
    if (id == null) {
      throw new BadRequestException("missing id");
    }
    try {
      idAsLong = Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new BadRequestException("id is not a number");
    } catch (NotFoundException e) {
      throw new BadRequestException("table not found");
    }
    return this.tableManagement.findTable(idAsLong);
  }

}
