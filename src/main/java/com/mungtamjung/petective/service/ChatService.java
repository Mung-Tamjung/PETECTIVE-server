package com.mungtamjung.petective.service;

import com.mungtamjung.petective.dto.ChatDTO;
import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.repository.ChatRepository;
import com.mungtamjung.petective.repository.ChatRoomRepository;
import com.mungtamjung.petective.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatEntity create(ChatDTO chatDTO) {
        // chatRoom은 ID로 전달받았다고 가정
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatDTO.getChatRoom())
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        // UserEntity를 sender와 receiver의 ID로 조회
        UserEntity sender = userRepository.findById(chatDTO.getSender())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        UserEntity receiver = userRepository.findById(chatDTO.getReceiver())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setSender(sender);
        chatEntity.setReceiver(receiver);
        chatEntity.setMessage(chatDTO.getMessage());
        chatEntity.setChatRoom(chatRoom); // chatRoomEntity로 설정

        return chatRepository.save(chatEntity);
    }

    public List<ChatEntity> getChatHistory(String chatRoomId){
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        // 채팅방에 속한 모든 메시지를 가져옴
        return chatRepository.findByChatRoomOrderByCreatedAt(chatRoom);
    }

}
