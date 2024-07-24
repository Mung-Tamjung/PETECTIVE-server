package com.mungtamjung.petective.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private Set<WebSocketSession> sessionSet = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{

        sessionSet.add(session); //sessionSet에 세션 저장

        log.info("{} 연결됨", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{

        sessionSet.remove(session); //sessionSet에 저장된 세션 삭제

        log.info("{} 연결 끊김", session.getId());
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

        //채팅 설정

        //1. message 데이터 가공
        //chat: id, sender, receiver, message, roomId
        ObjectMapper objectMapper = new ObjectMapper();

        ChatEntity chat = objectMapper.readValue(message.getPayload(), ChatEntity.class);

        log.info("chat: {}", chat);

        //2. DB에 채팅 insert
        ChatEntity result = chatService.create(chat);

        if(result != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            chat.setCreated_at(dateFormat.format(new Date()));


            //log.info("sessions: {}",sessionSet);

            //현재 웹 소켓에 접속 중인 모든 회원의 세션 정보가 담겨져 있음: 본프로젝트는 일대일 채팅이므로 수정 필요
            for(WebSocketSession s: sessionSet){

                HttpSession temp = (HttpSession)s.getAttributes().get("session");

                String userId = ((UserEntity)temp. getAttribute("userId")).getId();

                //로그인 상태의 회원 중 사용자 정보 일치하는 회원(발신 혹은 수신에 해당)에게 메세지 전달
                if(userId == chat.getReceiver() || userId == chat.getSender()){
                    String jsonData = objectMapper.writeValueAsString(chat);

                    s.sendMessage(new TextMessage(jsonData));
                }
            }
        }

        TextMessage textMessage = new TextMessage("Hello chatting server!");
        session.sendMessage(textMessage);
    }


}
