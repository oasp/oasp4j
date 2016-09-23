package io.oasp.module.basic.common.api.reflect;

import org.junit.Test;

import io.oasp.module.test.common.base.ModuleTest;

/**
 * Test-case for {@link OaspPackage}.
 *
 * @author hohwille
 */
public class OaspPackageTest extends ModuleTest {

  /** Test of {@link OaspPackage#of(Class)} with {@link OaspPackage}. */
  @Test
  public void testOfClass() {

    Class<OaspPackage> type = OaspPackage.class;
    OaspPackage pkg = OaspPackage.of(type);
    assertThat(pkg.getRoot()).isEqualTo("io.oasp");
    assertThat(pkg.getApplication()).isEqualTo("module");
    assertThat(pkg.getComponent()).isEqualTo("basic");
    assertThat(pkg.getLayer()).isEqualTo("common");
    assertThat(pkg.isLayerBatch()).isFalse();
    assertThat(pkg.isLayerClient()).isFalse();
    assertThat(pkg.isLayerCommon()).isTrue();
    assertThat(pkg.isLayerDataAccess()).isFalse();
    assertThat(pkg.isLayerLogic()).isFalse();
    assertThat(pkg.isLayerService()).isFalse();
    assertThat(pkg.getScope()).isEqualTo("api");
    assertThat(pkg.isScopeApi()).isTrue();
    assertThat(pkg.isScopeBase()).isFalse();
    assertThat(pkg.isScopeImpl()).isFalse();
    assertThat(pkg.getDetail()).isEqualTo("reflect");
    assertThat(pkg.toString()).isEqualTo(type.getPackage().getName());
    assertThat(pkg.isValid()).isTrue();
  }

  /** Test of {@link OaspPackage#of(String)} with {@code io.oasp.module.rest.service.impl.json}. */
  @Test
  public void testOfStringOaspModule() {

    String packageName = "io.oasp.module.rest.service.impl.json";
    OaspPackage pkg = OaspPackage.of(packageName);
    assertThat(pkg.getRoot()).isEqualTo("io.oasp");
    assertThat(pkg.getApplication()).isEqualTo("module");
    assertThat(pkg.getComponent()).isEqualTo("rest");
    assertThat(pkg.getLayer()).isEqualTo("service");
    assertThat(pkg.isLayerBatch()).isFalse();
    assertThat(pkg.isLayerClient()).isFalse();
    assertThat(pkg.isLayerCommon()).isFalse();
    assertThat(pkg.isLayerDataAccess()).isFalse();
    assertThat(pkg.isLayerLogic()).isFalse();
    assertThat(pkg.isLayerService()).isTrue();
    assertThat(pkg.getScope()).isEqualTo("impl");
    assertThat(pkg.isScopeApi()).isFalse();
    assertThat(pkg.isScopeBase()).isFalse();
    assertThat(pkg.isScopeImpl()).isTrue();
    assertThat(pkg.getDetail()).isEqualTo("json");
    assertThat(pkg.toString()).isEqualTo(packageName);
    assertThat(pkg.isValid()).isTrue();
  }

  /**
   * Test of {@link OaspPackage#of(String)} with
   * {@code io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao}.
   */
  @Test
  public void testOfStringSampleApp() {

    String packageName = "io.oasp.gastronomy.restaurant.offermanagement.dataaccess.base";
    OaspPackage pkg = OaspPackage.of(packageName);
    assertThat(pkg.getRoot()).isEqualTo("io.oasp.gastronomy");
    assertThat(pkg.getApplication()).isEqualTo("restaurant");
    assertThat(pkg.getComponent()).isEqualTo("offermanagement");
    assertThat(pkg.getLayer()).isEqualTo("dataaccess");
    assertThat(pkg.isLayerBatch()).isFalse();
    assertThat(pkg.isLayerClient()).isFalse();
    assertThat(pkg.isLayerCommon()).isFalse();
    assertThat(pkg.isLayerDataAccess()).isTrue();
    assertThat(pkg.isLayerLogic()).isFalse();
    assertThat(pkg.isLayerService()).isFalse();
    assertThat(pkg.getScope()).isEqualTo("base");
    assertThat(pkg.isScopeApi()).isFalse();
    assertThat(pkg.isScopeBase()).isTrue();
    assertThat(pkg.isScopeImpl()).isFalse();
    assertThat(pkg.isScopeBase()).isTrue();
    assertThat(pkg.getDetail()).isNull();
    assertThat(pkg.toString()).isEqualTo(packageName);
    assertThat(pkg.isValid()).isTrue();
  }

  /**
   * Test of {@link OaspPackage#of(String, String, String, String, String, String)} with
   * {@code io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao}.
   */
  @Test
  public void testOfSegmentsSampleApp() {

    String root = "io.oasp.gastronomy";
    String app = "restaurant";
    String component = "offermanagement";
    String layer = "dataaccess";
    String scope = "base";
    OaspPackage pkg = OaspPackage.of(root, app, component, layer, scope, null);
    assertThat(pkg.getRoot()).isEqualTo(root);
    assertThat(pkg.getApplication()).isEqualTo(app);
    assertThat(pkg.getComponent()).isEqualTo(component);
    assertThat(pkg.getLayer()).isEqualTo(layer);
    assertThat(pkg.isLayerBatch()).isFalse();
    assertThat(pkg.isLayerClient()).isFalse();
    assertThat(pkg.isLayerCommon()).isFalse();
    assertThat(pkg.isLayerDataAccess()).isTrue();
    assertThat(pkg.isLayerLogic()).isFalse();
    assertThat(pkg.isLayerService()).isFalse();
    assertThat(pkg.getScope()).isEqualTo(scope);
    assertThat(pkg.isScopeApi()).isFalse();
    assertThat(pkg.isScopeBase()).isTrue();
    assertThat(pkg.isScopeImpl()).isFalse();
    assertThat(pkg.getDetail()).isNull();
    assertThat(pkg.toString()).isEqualTo("io.oasp.gastronomy.restaurant.offermanagement.dataaccess.base");
    assertThat(pkg.isValid()).isTrue();
  }

  /**
   * Test of {@link OaspPackage#of(String)} with a package-name not strictly following the conventions.
   */
  @Test
  public void testOfStringFallback() {

    String packageName = "com.company.sales.shop.offermanagement.data.api.dataaccess";
    OaspPackage pkg = OaspPackage.of(packageName);
    assertThat(pkg.getRoot()).isEqualTo("com.company.sales");
    assertThat(pkg.getApplication()).isEqualTo("shop");
    assertThat(pkg.getComponent()).isEqualTo("offermanagement");
    assertThat(pkg.getLayer()).isEqualTo("data");
    assertThat(pkg.isLayerDataAccess()).isFalse();
    assertThat(pkg.getScope()).isEqualTo("api");
    assertThat(pkg.isScopeApi()).isTrue();
    assertThat(pkg.getDetail()).isEqualTo("dataaccess");
    assertThat(pkg.toString()).isEqualTo(packageName);
    assertThat(pkg.isValid()).isFalse();
  }

  /**
   * Test of {@link OaspPackage#of(String)} with an invalid package.
   */
  @Test
  public void testOfStringInvalid() {

    String packageName = "java.nio.channels.spi";
    OaspPackage pkg = OaspPackage.of(packageName);
    assertThat(pkg.getRoot()).isEqualTo(packageName);
    assertThat(pkg.getApplication()).isNull();
    assertThat(pkg.getComponent()).isNull();
    assertThat(pkg.getLayer()).isNull();
    assertThat(pkg.getScope()).isNull();
    assertThat(pkg.isScopeApi()).isFalse();
    assertThat(pkg.getDetail()).isNull();
    assertThat(pkg.toString()).isEqualTo(packageName);
    assertThat(pkg.isValid()).isFalse();
  }

  /**
   * Test of {@link OaspPackage#of(String)} with an illegal package.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOfStringIllegal() {

    String packageName = "...batch.api.impl";
    OaspPackage.of(packageName);
  }

  /**
   * Test of {@link OaspPackage#of(Package)} with the {@link Package} of {@link OaspPackage} itself.
   */
  @Test
  public void testOfPackage() {

    Package javaPackage = OaspPackage.class.getPackage();
    OaspPackage pkg = OaspPackage.of(javaPackage);
    assertThat(pkg.getRoot()).isEqualTo("io.oasp");
    assertThat(pkg.getApplication()).isEqualTo("module");
    assertThat(pkg.getComponent()).isEqualTo("basic");
    assertThat(pkg.getLayer()).isEqualTo("common");
    assertThat(pkg.isLayerCommon()).isTrue();
    assertThat(pkg.getScope()).isEqualTo("api");
    assertThat(pkg.isScopeApi()).isTrue();
    assertThat(pkg.getDetail()).isEqualTo("reflect");
    assertThat(pkg.toString()).isEqualTo(javaPackage.getName());
    assertThat(pkg.isValid()).isTrue();
  }

}
