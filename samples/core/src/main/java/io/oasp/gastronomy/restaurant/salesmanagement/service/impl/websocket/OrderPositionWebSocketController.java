package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import io.oasp.gastronomy.restaurant.salesmanagement.service.api.websocket.PositionStatusChangeTo;

@Controller
public class OrderPositionWebSocketController {
    @MessageMapping("/positions")
    @SendTo("/topic/positionStatusChange")
    public PositionStatusChangeTo statusChanged(PositionStatusChangeTo positionStatusChange) {
        return positionStatusChange;
    }
}
