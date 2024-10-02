package com.mungtamjung.petective.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungtamjung.petective.dto.ChatDTO;
import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private Set<WebSocketSession> sessionSet = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSet.add(session); //sessionSet에 세션 저장

        log.info("{} 연결됨", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        sessionSet.remove(session); //sessionSet에 저장된 세션 삭제

        log.info("{} 연결 끊김", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            //1. message 데이터 가공
            //chat: id, sender, receiver, message, chatRoom
            ObjectMapper objectMapper = new ObjectMapper();

            // 클라이언트에서 보내는 JSON 데이터를 ChatDTO로 매핑
            ChatDTO chatDTO = objectMapper.readValue(message.getPayload(), ChatDTO.class);

            log.info("chat: {}", chatDTO);

            //2. DB에 채팅 insert
            ChatEntity result = chatService.create(chatDTO);

            if (result != null) {
                result.setCreatedAt(LocalDateTime.now()); // 시간 처리

                String senderId = (String) session.getAttributes().get("userId");

                if (senderId == null) {
                    session.sendMessage(new TextMessage("Error: User is not authenticated"));
                    return;
                }

                log.info("Authenticated user ID: {}", senderId);

                // 해당 채팅방에 있는 사용자에게만 메시지 전송
                for (WebSocketSession s : sessionSet) {
                    String currentUserId = (String) s.getAttributes().get("userId");

                    // 발신자는 제외하고 수신자에게만 메시지 전송
                    if (currentUserId != null && currentUserId.equals(chatDTO.getReceiver())) {
                        String jsonData = objectMapper.writeValueAsString(chatDTO);
                        s.sendMessage(new TextMessage(jsonData));
                    }

                }
            }

        } catch (Exception e) {
            log.error("Error while handling message: {}", e.getMessage());
            session.sendMessage(new TextMessage("Error: " + e.getMessage())); // 클라이언트에 오류 메시지 전송
        }
    }
}

