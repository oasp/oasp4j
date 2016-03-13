package io.oasp.gastronomy.restaurant.tablemanagement.service.api.ws.v1_0;

import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * This is the interface for a service exposing the functionality of the
 * {@link io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement} component.
 *
 * @author jmetzler
 */
@WebService
public interface TablemanagmentWebService {

  /**
   * @see io.oasp.gastronomy.restaurant.tablemanagement.logic.api.Tablemanagement#findTable(Long)
   *
   * @param id is the {@link TableEto#getId() ID}.
   * @return the requested {@link TableEto table}.
   */
  @WebMethod
  @WebResult(name = "message")
  TableEto getTable(@WebParam(name = "id") String id);

}
