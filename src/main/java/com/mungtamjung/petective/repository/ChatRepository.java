package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, String> {
    // 채팅방 내 메시지 시간 순으로 가져옴
    List<ChatEntity> findByChatRoomOrderByCreatedAt(ChatRoomEntity chatRoom);
}
