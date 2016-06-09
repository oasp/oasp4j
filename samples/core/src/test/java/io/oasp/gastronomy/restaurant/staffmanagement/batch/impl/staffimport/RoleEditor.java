package io.oasp.gastronomy.restaurant.staffmanagement.batch.impl.staffimport;

import java.beans.PropertyEditorSupport;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;

/**
 * This class extends {@link PropertyEditorSupport} in order to provide conversion functionality from String to enum
 * type {@link Role}.
 *
 * @author sroeger
 */

public class RoleEditor extends PropertyEditorSupport {

  @Override
  public void setAsText(String text) {

    int textInt = Integer.parseInt(text);
    Role c = null;
    switch (textInt) {
    case 0:
      c = Role.COOK;
      break;
    case 1:
      c = Role.WAITER;
      break;
    case 2:
      c = Role.BARKEEPER;
      break;
    case 3:
      c = Role.CHIEF;
      break;
    }
    setValue(c);
  }

  @Override
  public String getAsText() {

    Role c = (Role) getValue();
    return c.getName();
  }

}
