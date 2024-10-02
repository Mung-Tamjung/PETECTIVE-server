package com.mungtamjung.petective.service;

import com.mungtamjung.petective.dto.ChatDTO;
import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.repository.ChatRepository;
import com.mungtamjung.petective.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public ChatEntity create(ChatDTO chatDTO) {
        // chatRoom은 ID로 전달받았다고 가정
        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatDTO.getChatRoom())
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setSender(chatDTO.getSender());
        chatEntity.setReceiver(chatDTO.getReceiver());
        chatEntity.setMessage(chatDTO.getMessage());
        chatEntity.setChatRoom(chatRoom); // chatRoomEntity로 설정

        return chatRepository.save(chatEntity);
    }

}
