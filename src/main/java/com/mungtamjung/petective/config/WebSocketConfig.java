package com.mungtamjung.petective.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private final HandshakeInterceptor handshakeInterceptor;

    @Autowired
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        try{
            registry.addHandler(webSocketHandler, "/ws/chat")
                    .addInterceptors(handshakeInterceptor)
                    .setAllowedOrigins("*");
                    //.withSockJS();
        } catch(Exception e){
            System.out.println("Error registering Stop Endpoints: "+ e.getMessage());
        }

    }
}
