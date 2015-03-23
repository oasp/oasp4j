package io.oasp.gastronomy.restaurant.salesmanagement.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {

    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/sample");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    registry.addEndpoint("/positions").withSockJS();
  }

}