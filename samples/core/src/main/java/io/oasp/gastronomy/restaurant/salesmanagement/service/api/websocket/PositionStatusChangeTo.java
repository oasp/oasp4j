package io.oasp.gastronomy.restaurant.salesmanagement.service.api.websocket;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionStatusChangeTo {
  private Long id;

  private OrderPositionState status;

  /**
   *
   * The constructor.
   *
   * @param id OrderId
   * @param status OrderPositionState
   */
  @JsonCreator
  public PositionStatusChangeTo(@JsonProperty("id") Long id, @JsonProperty("status") OrderPositionState status) {

    this.id = id;
    this.status = status;
  }

  /**
   * @return id
   */
  public Long getId() {

    return this.id;
  }

  /**
   * @return OrderPosition status
   */
  public OrderPositionState getStatus() {

    return this.status;
  }
}
