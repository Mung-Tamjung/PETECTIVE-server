package com.mungtamjung.petective.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;

@Component
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpServletRequest req = servletRequest.getServletRequest();
        HttpSession httpSession = req.getSession();
        String sessionID = httpSession.getId();
        attributes.put("sessionID", sessionID);

        String userId = req.getParameter("userId");

        attributes.put("userId", userId);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        if(request.getHeaders() == null)
            return;
        if(request.getHeaders().get("Sec-WebSocket-Protocol") == null)
            return;
        String protocol = (String) request.getHeaders().get("Sec-WebSocket-Protocol").get(0);
        if(protocol == null)
            return;
        response.getHeaders().add("Sec-WebSocket-Protocol", protocol);
    }
}
