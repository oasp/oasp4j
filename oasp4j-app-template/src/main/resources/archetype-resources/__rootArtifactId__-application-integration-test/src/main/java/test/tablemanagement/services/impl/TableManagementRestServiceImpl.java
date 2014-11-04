#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.tablemanagement.services.impl;

import ${package}.tablemanagement.common.api.datatype.TableState;
import ${package}.tablemanagement.logic.api.to.TableEto;
import ${package}.test.general.webclient.WebClientPool;
import ${package}.test.general.webclient.WebClientWrapper;
import ${package}.test.tablemanagement.logic.api.to.TableEtoWrapper;
import ${package}.test.tablemanagement.services.api.TableManagementRestService;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class TableManagementRestServiceImpl implements TableManagementRestService {

  private String username;

  public TableManagementRestServiceImpl(String username) {

    this.username = username;
  }

  /**
   * {@inheritDoc}
   */
  public TableEtoWrapper getTable(Long id) {

    WebClient webClient = WebClientPool.getWebClientWrapper(this.username).getWebClient();
    webClient.replacePath(TableManagementRestServiceUrls.getTableUrl(id));

    return new TableEtoWrapper(webClient.get(TableEto.class), webClient.get(), webClient.get(String.class));

  }

  /**
   * {@inheritDoc}
   */
  public List<TableEtoWrapper> getAllTables() {

    WebClient webClient = WebClientPool.getWebClientWrapper(this.username).getWebClient();
    webClient.replacePath(TableManagementRestServiceUrls.getAllTablesUrl);
    List<TableEtoWrapper> resultList = new ArrayList<>();

    List<TableEto> getResult = webClient.get(List.class);
    Response response = webClient.get();
    String jsonString = webClient.get(String.class);
    for (TableEto tableEto : getResult) {
      resultList.add(new TableEtoWrapper(tableEto, response, jsonString));
    }
    return resultList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TableEtoWrapper createTable(TableEtoWrapper table) {

    WebClientWrapper webClientWrapper = WebClientPool.getWebClientWrapper(this.username);
    if (!webClientWrapper.isCsrfHeaderSet()) {
      webClientWrapper.setCsrfHeader();
    }
    WebClient webClient = webClientWrapper.getWebClient();
    webClient.replacePath(TableManagementRestServiceUrls.createTableUrl);
    return new TableEtoWrapper(webClient.put(null, TableEto.class), webClient.put(null), webClient.put(null,
        String.class));

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Response deleteTable(Long id) {

    WebClient webClient = WebClientPool.getWebClientWrapper(this.username).getWebClient();
    webClient.replacePath(TableManagementRestServiceUrls.createTableUrl);
    return webClient.delete();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TableEtoWrapper> getFreeTables() {

    WebClient webClient = WebClientPool.getWebClientWrapper(this.username).getWebClient();
    webClient.replacePath(TableManagementRestServiceUrls.getFreeTablesUrl);
    List<TableEtoWrapper> resultList = new ArrayList<>();

    List<TableEto> getResult = webClient.get(List.class);
    Response response = webClient.get();
    String jsonString = webClient.get(String.class);
    for (TableEto tableEto : getResult) {
      resultList.add(new TableEtoWrapper(tableEto, response, jsonString));
    }
    return resultList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Response markTableAs(Long id, TableState newState) {

    WebClientWrapper webClientWrapper = WebClientPool.getWebClientWrapper(this.username);
    if (!webClientWrapper.isCsrfHeaderSet()) {
      webClientWrapper.setCsrfHeader();
    }
    WebClient webClient = webClientWrapper.getWebClient();
    webClient.replacePath(TableManagementRestServiceUrls.markTableAsUrl(id, newState));
    return webClient.post(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTableReleasable(Long id) {

    WebClient webClient = WebClientPool.getWebClientWrapper(this.username).getWebClient();
    webClient.replacePath(TableManagementRestServiceUrls.isTableReleasable(id));
    return webClient.post(null, Boolean.class);
  }

}
