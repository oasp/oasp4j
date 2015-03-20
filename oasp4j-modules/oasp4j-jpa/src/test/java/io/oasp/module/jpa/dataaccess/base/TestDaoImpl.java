package io.oasp.module.jpa.dataaccess.base;

import javax.inject.Named;

/**
 * Implementation of {@link TestDao}
 *
 * @author fawinter
 */
@Named
public class TestDaoImpl extends AbstractDao<TestEntity> implements TestDao {

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<TestEntity> getEntityClass() {

    return TestEntity.class;
  }

}
