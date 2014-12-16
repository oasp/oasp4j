package io.oasp.gastronomy.restaurant.salesmanagement.websocket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;

public class PositionStatusChangeTo {
    private Long id;
    private OrderPositionState status;

    @JsonCreator
    public PositionStatusChangeTo(@JsonProperty("id") Long id, @JsonProperty("status") OrderPositionState status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public OrderPositionState getStatus() {
        return status;
    }
}
