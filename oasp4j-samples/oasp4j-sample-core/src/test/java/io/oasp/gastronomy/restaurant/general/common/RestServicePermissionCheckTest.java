package io.oasp.gastronomy.restaurant.general.common;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

import org.junit.Test;

/**
 * Tests the permission check for the REST services.
 *
 * @author jmetzler
 */
public class RestServicePermissionCheckTest {

  /**
   * Check if all REST service methods have permission checks i.e. {@link RolesAllowed}, {@link DenyAll} or
   * {@link PermitAll} annotation is applied. This is only checked for methods that also have one of the following
   * annotations applied: {@link GET}, {@link PUT}, {@link POST}, {@link DELETE}.
   */
  @Test
  public void rolesAllowedAnnotationPresent() {

    String packageName = "io.oasp.gastronomy.restaurant";
    Filter<String> filter = new Filter<String>() {

      @Override
      public boolean accept(String value) {

        return value.endsWith("RestServiceImpl");
      }

    };
    ReflectionUtil ru = ReflectionUtilImpl.getInstance();
    Set<String> classNames = ru.findClassNames(packageName, true, filter);
    Set<Class<?>> classes = ru.loadClasses(classNames);
    for (Class<?> clazz : classes) {
      Method[] methods = clazz.getDeclaredMethods();
      for (Method method : methods) {
        if (method.getAnnotation(GET.class) != null || method.getAnnotation(POST.class) != null
            || method.getAnnotation(PUT.class) != null || method.getAnnotation(DELETE.class) != null) {
          assertTrue(
              "Method " + method.getName() + " in Class " + clazz.getSimpleName() + " is missing access control",
              method.getAnnotation(RolesAllowed.class) != null || method.getAnnotation(DenyAll.class) != null
                  || method.getAnnotation(PermitAll.class) != null);
        }
      }
    }
  }

}
