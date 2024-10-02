package com.mungtamjung.petective.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    private final HandshakeInterceptor handshakeInterceptor;

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        try{
            registry.addHandler(webSocketHandler, "/ws/chat")
                    .addInterceptors(handshakeInterceptor)
                    .setAllowedOrigins("*");
                    //.withSockJS();
        } catch(Exception e){
            logger.error("Error registering WebSocket handlers: {}", e.getMessage(), e);
        }

    }
}
