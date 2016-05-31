package io.oasp.gastronomy.restaurant.offermanagement.logic.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferCto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.test.common.base.ModuleTest;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */

@RunWith(MockitoJUnitRunner.class)
public class OffermanagementImplTest extends ModuleTest {

  /**
   * The System Under Test (SUT)
   */

  private OffermanagementImpl offerManagementImpl;

  @Mock
  private OffermanagementImpl offerManagementImpl2;

  @Mock
  private OfferDao offerDao;

  @Mock
  private BeanMapper beanMapper;

  @Before
  public void setup() {

    assertThat(this.offerDao).isNotNull();
    assertThat(this.beanMapper).isNotNull();
    this.offerManagementImpl = new OffermanagementImpl();
    this.offerManagementImpl.setOfferDao(this.offerDao);
    this.offerManagementImpl.setBeanMapper(this.beanMapper);
  }

  @After
  public void tearDown() {

    this.beanMapper = null;
    this.offerDao = null;
    this.offerManagementImpl = null;
  }

  @Test
  public void findOffer() {

    // setup
    // given
    long id = 1L;

    OfferEntity entity = Mockito.mock(OfferEntity.class);
    OfferEto eto = new OfferEto();

    Mockito.when(this.offerDao.findOne(id)).thenReturn(entity);
    Mockito.when(this.beanMapper.map(entity, OfferEto.class)).thenReturn(eto);

    // exercise
    // when
    OfferEto resultEto = this.offerManagementImpl.findOffer(id);

    // verify
    // then
    Mockito.verify(this.offerDao).findOne(id);
    Mockito.verify(this.beanMapper).map(entity, OfferEto.class);

    assertThat(resultEto).isNotNull();
    assertThat(resultEto).isEqualTo(eto);
  }

  @Test
  public void findOfferCto() {

    // setup
    // given
    long id = 1L;

    OfferCto offerCto = new OfferCto();
    OfferEto offerEto = new OfferEto();
    // OfferEto offerEto = Mockito.mock(OfferEto.class);
    offerCto.setOffer(offerEto);
    OfferEntity entity = Mockito.mock(OfferEntity.class);

    Mockito.when(this.offerDao.findOne(id)).thenReturn(entity);
    Mockito.when(this.beanMapper.map(entity, OfferEto.class)).thenReturn(offerEto);

    // exercise
    // when
    OfferCto resultCto = this.offerManagementImpl.findOfferCto(id);

    // verify
    // then
    Mockito.verify(this.offerDao).findOne(id);
    Mockito.verify(this.beanMapper).map(entity, OfferEto.class);

    assertThat(resultCto).isNotNull();
    assertThat(resultCto).isEqualTo(offerCto);

  }

}
