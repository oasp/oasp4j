package io.oasp.example.component.dataaccess.impl.dao;

import javax.inject.Named;

import io.oasp.example.component.dataaccess.api.BarEntity;
import io.oasp.example.component.dataaccess.api.dao.BarDao;
import io.oasp.module.jpa.dataaccess.base.AbstractDaoImpl;

/**
 * Implementation of {@link BarDao}.
 */
@Named
public class BarDaoImpl extends AbstractDaoImpl<BarEntity> implements BarDao {

  @Override
  protected Class<BarEntity> getEntityClass() {

    return BarEntity.class;
  }

}
