package io.oasp.gastronomy.restaurant.general.common;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

import org.junit.Test;

/**
 * Tests the permission check in logic layer.
 *
 * @author jmetzler
 */
public class PermissionCheckTest {

  /**
   * Check if all relevant methods in use case implementations have permission checks i.e. {@link RolesAllowed},
   * {@link DenyAll} or {@link PermitAll} annotation is applied. This is only checked for methods that are declared in
   * the corresponding interface and thus have the {@link Override} annotations applied.
   */
  @Test
  public void permissionCheckAnnotationPresent() {

    String packageName = "io.oasp.gastronomy.restaurant";
    Filter<String> filter = new Filter<String>() {

      @Override
      public boolean accept(String value) {

        return value.contains(".logic.impl.usecase.Uc") && value.endsWith("Impl");
      }

    };
    ReflectionUtil ru = ReflectionUtilImpl.getInstance();
    Set<String> classNames = ru.findClassNames(packageName, true, filter);
    Set<Class<?>> classes = ru.loadClasses(classNames);
    for (Class<?> clazz : classes) {
      Method[] methods = clazz.getDeclaredMethods();
      for (Method method : methods) {
        if (method.getAnnotation(Override.class) != null) {
          assertTrue(
              "Method " + method.getName() + " in Class " + clazz.getSimpleName() + " is missing access control",
              method.getAnnotation(RolesAllowed.class) != null || method.getAnnotation(DenyAll.class) != null
                  || method.getAnnotation(PermitAll.class) != null);
        }
      }
    }
  }

}
