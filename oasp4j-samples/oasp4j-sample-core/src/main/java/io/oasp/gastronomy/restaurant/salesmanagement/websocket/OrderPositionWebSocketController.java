package io.oasp.gastronomy.restaurant.salesmanagement.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class OrderPositionWebSocketController {
    @MessageMapping("/positions")
    @SendTo("/topic/positionStatusChange")
    public PositionStatusChangeTo statusChanged(PositionStatusChangeTo positionStatusChange) {
        return positionStatusChange;
    }
}
