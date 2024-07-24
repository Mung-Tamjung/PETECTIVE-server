package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, String> {
    ChatEntity findByRoomId(String roomId); //채팅방 메시지 조회
}
