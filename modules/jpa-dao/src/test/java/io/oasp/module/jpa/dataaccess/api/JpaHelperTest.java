package io.oasp.module.jpa.dataaccess.api;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.oasp.example.TestApplication;
import io.oasp.example.component.common.api.to.FooEto;
import io.oasp.example.component.dataaccess.api.BarEntity;
import io.oasp.example.component.dataaccess.api.FooEntity;
import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * Test of {@link JpaHelper}.
 */
@Transactional
@SpringBootTest(classes = { TestApplication.class }, webEnvironment = WebEnvironment.NONE)
public class JpaHelperTest extends ComponentTest {

  @PersistenceContext
  private EntityManager entityManager;

  @Inject
  private BeanMapper beanMapper;

  /**
   * Test of {@link JpaHelper#asEntity(io.oasp.module.basic.common.api.reference.Ref, Class)} via real production-like
   * scenario.
   */
  @Test
  public void testIdRefAsEntity() {

    // given
    BarEntity barEntity = new BarEntity();
    barEntity.setMessage("Test message");
    FooEntity fooEntity = new FooEntity();
    fooEntity.setName("Test name");
    fooEntity.setBar(barEntity);

    // when
    this.entityManager.persist(fooEntity);
    FooEto fooEto = new FooEto();
    fooEto.setId(fooEntity.getId());
    fooEto.setModificationCounter(fooEntity.getModificationCounter());
    fooEto.setName(fooEntity.getName());
    fooEto.setBarId(fooEntity.getBarId());

    FooEntity fooEntity2 = this.beanMapper.map(fooEto, FooEntity.class);

    // then
    assertThat(fooEntity2.getBar()).isSameAs(barEntity);
  }

}
