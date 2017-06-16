package io.oasp.gastronomy.restaurant.general.common.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

import org.assertj.core.api.SoftAssertions;
import org.junit.Ignore;
import org.junit.Test;

import io.oasp.module.basic.common.api.reflect.OaspPackage;
import io.oasp.module.test.common.base.ModuleTest;

/**
 * This test verifies that the entire code of your code-base is located in {@link OaspPackage#isValid() valid OASP
 * packages}.
 */
@Ignore("Currently fails, see #498")
public class OaspPackageCheckTest extends ModuleTest {

  /**
   * Scans all the packages of this application root pacakge namespace. Will verify that these are
   * {@link OaspPackage#isValid() valid OASP packages}.
   */
  @Test
  public void testPackages() {

    OaspPackage pkg = OaspPackage.of(OaspPackageCheckTest.class);
    assertThat(pkg.isValid()).isTrue();

    ReflectionUtil ru = ReflectionUtilImpl.getInstance();
    Set<String> classNames = ru.findClassNames(getRootPackage2Scan(pkg), true);
    String appPackage = pkg.getRoot() + "." + pkg.getApplication();
    Set<String> packages = new HashSet<>(128);
    packages.add(appPackage); // allow SpringBootApp, etc. in application package
    SoftAssertions assertion = new SoftAssertions();
    for (String className : classNames) {
      int lastDot = className.lastIndexOf('.');
      if (lastDot > 0) {
        String packageName = className.substring(0, lastDot);
        boolean added = packages.add(packageName);
        if (added) {
          pkg = OaspPackage.of(packageName);
          if (!pkg.isValid()) {
            assertion.assertThat(pkg.isValid())
                .as("package " + packageName + " is invalid (component: " + pkg.getComponent() + ", layer: "
                    + pkg.getLayer() + ", scope: " + pkg.getScope() + "). Hint contains e.g. "
                    + className.substring(lastDot + 1))
                .isTrue();
          }
        }
      }
    }
    assertion.assertAll();
  }

  /**
   * @param pkg the {@link OaspPackage} of this test.
   * @return the root package to scan for {@link Class}es to get the actual packages to check.
   */
  protected String getRootPackage2Scan(OaspPackage pkg) {

    String root = pkg.getRoot();
    if (root.startsWith("io.oasp")) {
      return "io.oasp";
    }
    return root;
  }

}
