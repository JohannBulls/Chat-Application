package edu.escuelaing.Chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class that sets up WebSocket messaging for the chat application.
 * It enables the use of STOMP (Simple Text Oriented Messaging Protocol) and configures message brokers.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers STOMP endpoints to allow clients to connect to the WebSocket server.
     * The endpoint "/chat-websocket" is used to establish WebSocket connections, with SockJS fallback options.
     *
     * @param registry The StompEndpointRegistry used to register WebSocket endpoints.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket").withSockJS();
    }

    /**
     * Configures the message broker for handling messaging within the application.
     * It enables a simple in-memory message broker with the prefix "/topic" for broadcasting messages 
     * and sets "/app" as the prefix for client message destinations.
     *
     * @param config The MessageBrokerRegistry used to configure the message broker.
     */
    @Override
    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
}