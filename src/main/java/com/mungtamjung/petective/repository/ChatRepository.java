package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, String> {
    List<ChatEntity> findByChatRoom(ChatRoomEntity chatRoom); // ChatRoomEntity로 검색
}
