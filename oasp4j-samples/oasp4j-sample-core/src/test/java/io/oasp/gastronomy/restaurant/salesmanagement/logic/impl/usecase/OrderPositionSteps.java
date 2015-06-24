package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.exception.IllegalEntityStateException;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.module.beanmapping.common.api.BeanMapper;

import net.thucydides.core.annotations.Step;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;

/**
 * Steps for testing the order position lifecycle
 *
 * @author mgoebel
 */
public class OrderPositionSteps extends Assertions {

  private final static String OK = "OK";

  private final static String NOK = "NOK";

  UcManageOrderPositionImpl usecase = new UcManageOrderPositionImpl();

  String oldStatus;

  String actualResult;

  @Step("Given an order position that is in state {0}")
  public void anOrderPositionThatIsInState(String status) {

    this.oldStatus = status;
  }

  @Step("When the user tries to change the state to {0}")
  public void theUserTriesToChangeStateTo(String newStatus) {

    // create mocks
    OrderPositionDao daoMock = Mockito.mock(OrderPositionDao.class);
    BeanMapper mapperMock = Mockito.mock(BeanMapper.class);

    // prepare ETO
    OrderPositionEto newEto = new OrderPositionEto();
    newEto.setState(OrderPositionState.valueOf(newStatus));
    newEto.setId(new Long(1));

    // prepare dummy entities
    OrderPositionEntity oldEntity = new OrderPositionEntity();
    oldEntity.setState(OrderPositionState.valueOf(this.oldStatus));
    OrderPositionEntity newEntity = new OrderPositionEntity();

    // prepare mocks
    Mockito.when(daoMock.find(Mockito.anyLong())).thenReturn(oldEntity);
    Mockito.when(mapperMock.map(newEto, OrderPositionEntity.class)).thenReturn(newEntity);
    Mockito.when(mapperMock.map(newEntity, OrderPositionEto.class)).thenReturn(newEto);
    Mockito.when(daoMock.save(newEntity)).thenReturn(newEntity);

    // prepare usecase
    this.usecase.setOrderPositionDao(daoMock);
    this.usecase.setBeanMapper(mapperMock);

    // save order position and store result
    try {
      this.usecase.saveOrderPosition(newEto);
      this.actualResult = OK;
    } catch (IllegalEntityStateException e) {
      this.actualResult = NOK;
    }

  }

  @Step("Then the validation result should be {0}")
  public void theValidationResultShouldBe(String expResult) {

    assertThat(this.actualResult).isEqualTo(expResult);
  }

}
