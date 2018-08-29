/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.jpa.dataaccess.api;

import net.sf.mmm.util.session.api.UserSessionAccess;

import org.hibernate.envers.RevisionListener;

/**
 * This is the implementation of {@link RevisionListener} that enriches {@link AdvancedRevisionEntity} with additional
 * information.
 */
public class AdvancedRevisionListener implements RevisionListener {

  /**
   * The constructor.
   */
  public AdvancedRevisionListener() {

    super();
  }

  @Override
  public void newRevision(Object revisionEntity) {

    AdvancedRevisionEntity revision = (AdvancedRevisionEntity) revisionEntity;
    revision.setUserLogin(UserSessionAccess.getUserLogin());
  }

}