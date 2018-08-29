package io.oasp.module.jpa.dataaccess.base;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.oasp.example.TestApplication;
import io.oasp.example.component.dataaccess.api.BarEntity;
import io.oasp.example.component.dataaccess.impl.BarDaoTxBean;
import io.oasp.module.jpa.dataaccess.api.dao.IGenericDao;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * Test class to test the {@link IGenericDao}.
 */
@SpringBootTest(classes = { TestApplication.class }, webEnvironment = WebEnvironment.NONE)
public class AbstractGenericDaoTest extends ComponentTest {

  @Inject
  private BarDaoTxBean testBean;

  /**
   * Test of {@link IGenericDao#forceIncrementModificationCounter(Object)}. Ensures that the modification counter is
   * updated after the call of that method when the transaction is closed.
   */
  @Test
  public void testForceIncrementModificationCounter() {

    // given
    BarEntity entity = this.testBean.create();
    assertThat(entity.getId()).isNotNull();
    assertThat(entity.getModificationCounter()).isEqualTo(0);

    // when
    BarEntity updatedEntity = this.testBean.incrementModificationCounter(entity.getId());

    // then
    assertThat(updatedEntity.getModificationCounter()).isEqualTo(1);
  }

};
