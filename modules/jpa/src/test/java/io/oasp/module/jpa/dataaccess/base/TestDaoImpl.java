package io.oasp.module.jpa.dataaccess.base;

import javax.inject.Named;

/**
 * Implementation of {@link TestDao}.
 *
 */
@Named
public class TestDaoImpl extends AbstractDao<TestEntity> implements TestDao {

  @Override
  protected Class<TestEntity> getEntityClass() {

    return TestEntity.class;
  }

}
