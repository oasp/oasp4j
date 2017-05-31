package io.oasp.module.service.common.api.discovery;

/**
 * This interface abstracts the aspect of the {@link #discover(ServiceDiscoveryContext) discovery} of a
 * {@link io.oasp.module.service.common.api.Service}. You may choose an exiting implementation or write your own to
 * customize the {@link #discover(ServiceDiscoveryContext) discovery} of your
 * {@link io.oasp.module.service.common.api.Service}s.
 */
public interface ServiceDiscoverer {

  /**
   * @param context the {@link ServiceDiscoveryContext} where to set the discovered meta-data. At least
   *        {@link ServiceDiscoveryContext#setUrl(String)} has to be called.<br>
   *        It is possible to have multiple implementations of this interface as spring beans in your context. An
   *        implementation may decide that it is not responsible for the given {@link ServiceDiscoveryContext#getApi()
   *        service API} (e.g. only responsible for REST services). In that case in can return without doing any
   *        modifications to the given {@link ServiceDiscoveryContext}. Until the
   *        {@link ServiceDiscoveryContext#getUrl() URL} has not been {@link ServiceDiscoveryContext#setUrl(String) set}
   *        further implementations of this interface will be {@link #discover(ServiceDiscoveryContext) invoked}. If all
   *        implementations have been invoked without {@link ServiceDiscoveryContext#setUrl(String) setting} the
   *        {@link ServiceDiscoveryContext#getUrl() URL}, the discovery will fail with a runtime exception causing the
   *        {@link io.oasp.module.service.common.api.ServiceClientFactory} to fail.
   */
  void discover(ServiceDiscoveryContext<?> context);

}
