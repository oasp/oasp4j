package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.ws.v1_0;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;

import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.ws.v1_0.TablemanagementWebService;

/**
 * Implementation of {@link TablemanagementWebService}.
 *
 */
@Named("TablemanagementWebService")
@WebService(endpointInterface = "io.oasp.gastronomy.restaurant.tablemanagement.service.api.ws.v1_0.TablemanagementWebService", portName = "TablemanagementWebService", serviceName = "TablemanagementWebService")
public class TablemanagementWebServiceImpl implements TablemanagementWebService {

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
  public TableEto getTable(long id) {

    return this.tableManagement.findTable(id);
  }

}
