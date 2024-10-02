package com.mungtamjung.petective.handler;

import com.mungtamjung.petective.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;

@Component
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//        HttpServletRequest req = servletRequest.getServletRequest();
//        HttpSession httpSession = req.getSession(false);

        // JWT 토큰을 HTTP 요청에서 추출
        String token = parseBearerToken(request);

        if(token != null){
            // TokenProvider를 사용하여 사용자 ID를 추출
            String userId = tokenProvider.validateTokenAndGetUser(token);

            if (userId != null) {
                attributes.put("userId", userId);
                System.out.println("User ID set in WebSocket attributes: " + userId);
                return true;
            } else {
                System.out.println("No userId found in HttpSession");
            }
        }else {
            System.out.println("No token found");
        }
        // 인증 실패 시
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false; // 핸드쉐이크 거부
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

    // JWT 토큰을 추출하는 메소드
    private String parseBearerToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer "를 제거한 토큰 반환
        }
        return null; // 토큰이 없으면 null 반환
    }
}
