package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.repository.ChatRoomRepository;
import com.mungtamjung.petective.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public List<ChatRoomEntity> retrieveChatRoomList() {
        return chatRoomRepository.findAll();
    }

    public ChatRoomEntity createChatRoom(ChatRoomEntity chatRoomEntity) {
        // 이미 동일한 sender와 receiver로 생성된 채팅방이 있는지 확인
        Optional<ChatRoomEntity> existingRoom = chatRoomRepository.findBySenderAndReceiver(
                chatRoomEntity.getSender(),
                chatRoomEntity.getReceiver()
        );

        if (existingRoom.isPresent()) {
            // 채팅방이 이미 존재하면 그 채팅방을 반환
            return existingRoom.get();
        } else {
            // 새 채팅방 생성
            return chatRoomRepository.save(chatRoomEntity);
        }
    }
}
