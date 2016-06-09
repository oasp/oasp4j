package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.message.Message;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */
public class MyUriInfo extends UriInfoImpl {

  /**
   * The constructor.
   *
   * @param m
   * @param templateParams
   */
  public MyUriInfo(Message m, MultivaluedMap<String, String> templateParams) {
    super(m, templateParams);
  }

  /**
   * The constructor.
   *
   * @param m
   */
  public MyUriInfo(Message m) {
    super(m);
  }

  @Override
  public MultivaluedMap<String, String> getQueryParameters() {

    MultivaluedMap<String, String> myParameterMap = new MultivaluedHashMap<>();
    myParameterMap.add("tableId", "101");
    myParameterMap.add("state", OrderState.OPEN.toString());
    myParameterMap.add("pagination[page]", "1");
    myParameterMap.add("pagination[size]", "1");
    return myParameterMap;
  }
}