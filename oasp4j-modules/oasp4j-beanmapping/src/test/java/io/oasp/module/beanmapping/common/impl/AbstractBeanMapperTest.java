package io.oasp.module.beanmapping.common.impl;

import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.test.common.base.ModuleTest;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.entity.base.AbstractRevisionedEntity;
import net.sf.mmm.util.transferobject.api.EntityTo;

import org.junit.Test;

/**
 * The abstract test-case for testing {@link BeanMapper} via its interface.
 *
 * @author hohwille
 */
public abstract class AbstractBeanMapperTest extends ModuleTest {

  /**
   * @return the {@link BeanMapper} instance to test.
   */
  protected abstract BeanMapper getBeanMapper();

  /**
   * Tests {@link BeanMapper#map(Object, Class)} for an {@link PersistenceEntity entity} to an {@link EntityTo ETO} and
   * ensures that if the {@link PersistenceEntity#getModificationCounter() modification counter} gets updated after
   * conversion that the {@link EntityTo ETO} reflects this change.
   */
  @Test
  public void testMapEntity2Eto() {

    // given
    BeanMapper mapper = getBeanMapper();
    MyBeanEntity entity = new MyBeanEntity();
    Long id = 1L;
    entity.setId(id);
    int version = 1;
    entity.setModificationCounter(version);
    Number revision = 10L;
    entity.setRevision(revision);
    String property = "its magic";
    entity.setProperty(property);

    // when
    MyBeanEto eto = mapper.map(entity, MyBeanEto.class);

    // then
    assertThat(eto).isNotNull();
    assertThat(eto.getId()).isEqualTo(id);
    assertThat(eto.getModificationCounter()).isEqualTo(version);
    assertThat(eto.getRevision()).isEqualTo(revision);
    assertThat(eto.getProperty()).isEqualTo(property);
    // sepcial feature: update of modificationCounter is performed when TX is closed what is typically after conversion
    int newVersion = version + 1;
    entity.setModificationCounter(newVersion);
    assertThat(eto.getModificationCounter()).isEqualTo(newVersion);
  }

  /**
   * {@link PersistenceEntity} for testing.
   */
  public static class MyBeanEntity extends AbstractRevisionedEntity<Long> implements PersistenceEntity<Long> {

    private static final long serialVersionUID = 1L;

    private String property;

    /**
     * @return property
     */
    public String getProperty() {

      return this.property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {

      this.property = property;
    }

  }

  /**
   * {@link EntityTo ETO} for testing.
   */
  public static class MyBeanEto extends EntityTo<Long> {

    private static final long serialVersionUID = 1L;

    private String property;

    /**
     * @return property
     */
    public String getProperty() {

      return this.property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {

      this.property = property;
    }
  }

}
