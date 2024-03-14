package com.alexia.backend.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for WebSocket using Spring Framework.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers STOMP endpoints, allowing WebSocket clients to connect to the
     * server.
     * 
     * @param registry the STOMP endpoint registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registers the WebSocket endpoint "/ws" and allows requests from all origins
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    /**
     * Configures the message broker to relay messages between clients and the
     * server.
     * 
     * @param registry the message broker registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Sets a prefix for application destinations where clients can send messages
        registry.setApplicationDestinationPrefixes("/app");
        // Configures the broker to relay messages to clients subscribed to destinations
        // starting with "/chat"
        registry.enableSimpleBroker("/chat");
    }

}