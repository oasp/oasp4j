/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.jpa.dataaccess.api;

import net.sf.mmm.util.session.api.UserSessionAccess;

import org.hibernate.envers.RevisionListener;

/**
 * This is the implementation of {@link RevisionListener} that enriches {@link AdvancedRevisionEntity} with additional
 * information.
 *
 * If you are starting the development of your application from scratch , please use this class or else if you have an
 * application developed and needs backward compatibility , please use the deprecated class
 * {@link io.oasp.module.jpa.dataaccess.base.AdvancedRevisionListener}
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