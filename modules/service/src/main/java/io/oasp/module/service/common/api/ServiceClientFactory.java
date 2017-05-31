package io.oasp.module.service.common.api;

/**
 * This is the interface for a factory used to {@link #create(Class) create} client stubs for a {@link Service}. The
 * following example shows the typical usage in your code:
 *
 * <pre>
 * &#64;{@link javax.inject.Named}
 * public class UcMyUseCaseImpl extends MyUseCaseBase implements UcMyUseCase {
 *   &#64;{@link javax.inject.Inject} private {@link ServiceClientFactory} clientFactory;
 *
 *   &#64;{@link Override} &#64;{@link javax.annotation.security.RolesAllowed}(...)
 *   public Foo doSomething(Bar bar) {
 *     MyExternalServiceApi externalService = this.clientFactory.{@link ServiceClientFactory#create(Class) create}(MyExternalServiceApi.class);
 *     Some result = externalService.doSomething(convert(bar));
 *     return convert(result);
 *   }
 * }
 * </pre>
 *
 * As you can see creating a service client stub is easy and requires only a single line of code. However, internally a
 * lot of things happen such as the following aspects:
 * <ul>
 * <li>{@link io.oasp.module.service.common.api.discovery.ServiceDiscoverer#discover(io.oasp.module.service.common.api.discovery.ServiceDiscoveryContext)
 * service discovery}.</li>
 * <li>{@link io.oasp.module.service.common.api.header.ServiceHeaderCustomizer#addHeaders(io.oasp.module.service.common.api.header.ServiceHeaderContext)
 * header customization} (for security, correlation-ID, etc.).</li>
 * <li>performance logging</li>
 * <li>exception mapping (exception facade)</li>
 * </ul>
 * All these aspects can be configured via spring and customized with own implementations.
 */
public interface ServiceClientFactory {

  /**
   * @param <S> the generic type of the {@code serviceInterface}. For flexibility and being not invasive this generic is
   *        not bound to {@link Service} ({@code S extends Service}).
   * @param serviceInterface the {@link Class} reflecting the interface that defines the API of your {@link Service}.
   * @return a new instance of the given {@code serviceInterface} that is a client stub. Invocations to any of the
   *         service methods will trigger a remote call and synchronously return the result.
   */
  <S> S create(Class<S> serviceInterface);

}
